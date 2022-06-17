package top.kkoishi.cv4j;

import top.kkoishi.cv4j.attr.InnerClassAttribute;
import top.kkoishi.cv4j.attr.SourceFileAttribute;
import top.kkoishi.cv4j.cp.ConstClassInfo;
import top.kkoishi.cv4j.cp.ConstUtf8Info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClassDecoder {
    static final ClassReader.ClassFileAccessFlag[] DEFAULT_FLAGS = {
            ClassReader.ClassFileAccessFlag.PUBLIC,
            ClassReader.ClassFileAccessFlag.FINAL,
            ClassReader.ClassFileAccessFlag.SUPER,
            ClassReader.ClassFileAccessFlag.INTERFACE,
            ClassReader.ClassFileAccessFlag.ABSTRACT,
            ClassReader.ClassFileAccessFlag.SYNTHETIC,
            ClassReader.ClassFileAccessFlag.ANNOTATION,
            ClassReader.ClassFileAccessFlag.ENUM,
            ClassReader.ClassFileAccessFlag.MODULE
    };

    static final ClassReader.FieldAccessFlag[] DEFAULT_IDENTIFIES = {
            ClassReader.FieldAccessFlag.PUBLIC,
            ClassReader.FieldAccessFlag.PRIVATE,
            ClassReader.FieldAccessFlag.PROTECTED,
            ClassReader.FieldAccessFlag.STATIC,
            ClassReader.FieldAccessFlag.FINAL,
            ClassReader.FieldAccessFlag.VOLATILE,
            ClassReader.FieldAccessFlag.TRANSIENT,
            ClassReader.FieldAccessFlag.TRANSIENT,
            ClassReader.FieldAccessFlag.ENUM
    };

    protected int majorBytecodeVersion;
    protected int minorBytecodeVersion;
    protected ArrayList<ConstPoolInfo> cpInfo;
    protected int accessFlags;
    protected int thisClassIndex;
    protected int superClassIndex;
    protected ArrayList<Integer> interfaceIndexes;
    protected ArrayList<FieldInfo> fieldTable;
    protected ArrayList<MethodInfo> methodTable;
    protected ArrayList<Attribute_info> classFileAttributeTable;
    protected final ArrayList<ClassReader.Annotation> annotations = new ArrayList<>(4);

    protected ClassDecoder (int majorBytecodeVersion,
                            int minorBytecodeVersion, ArrayList<ConstPoolInfo> cpInfo,
                            int accessFlags,
                            int thisClassIndex,
                            int superClassIndex,
                            ArrayList<Integer> interfaceIndexes,
                            ArrayList<FieldInfo> fieldTable,
                            ArrayList<MethodInfo> methodTable,
                            ArrayList<Attribute_info> classFileAttributeTable) {
        this.majorBytecodeVersion = majorBytecodeVersion;
        this.minorBytecodeVersion = minorBytecodeVersion;
        this.cpInfo = cpInfo;
        this.accessFlags = accessFlags;
        this.thisClassIndex = thisClassIndex;
        this.superClassIndex = superClassIndex;
        this.interfaceIndexes = interfaceIndexes;
        this.fieldTable = fieldTable;
        this.methodTable = methodTable;
        this.classFileAttributeTable = classFileAttributeTable;
    }

    public static ClassDecoder getDefault (ClassReader cr) {
        return new ClassDecoder(cr.majorBytecodeVersion,
                cr.minorBytecodeVersion,
                cr.cpInfo,
                cr.accessFlags,
                ClassReader.toInt(cr.thisClassIndex),
                ClassReader.toInt(cr.superClassIndex),
                cr.interfaceIndexes,
                cr.fieldTable,
                cr.methodTable,
                cr.classFileAttributeTable);
    }

    public static ClassDecoder getDefaultAndClose (ClassReader cr) {
        final ClassDecoder cdm = getDefault(cr);
        cr.close();
        return cdm;
    }

    public static ClassDecoder getAutoReadDefault (ClassReader cr) throws DecompilerException {
        cr.read();
        return getDefault(cr);
    }

    public static ClassDecoder getAutoReadAndClose (ClassReader cr) throws DecompilerException {
        final ClassDecoder cdm = getAutoReadDefault(cr);
        cr.close();
        return cdm;
    }

    public ClassResult decode () throws IllegalFileAttributeException {
        final ClassResult classResult = new ClassResult();
        classResult.fileAccessFlags = decodeClassAccessFlags(accessFlags);

        // TODO: 2022/5/24 decode data.
        return null;
    }

    protected Map<String, Object> decodeFileAttributeTable () throws IllegalFileAttributeException {
        final Map<String, ArrayList<Attribute_info>> attributeMap = new HashMap<>(4 * classFileAttributeTable.size());
        for (final Attribute_info attributeInfo : classFileAttributeTable) {
            final String name = ((ConstUtf8Info) cpInfo.get(attributeInfo.attributeNameIndex - 1)).getUtf8();
            if (attributeMap.containsKey(name)) {
                attributeMap.get(name).add(attributeInfo);
            } else {
                attributeMap.put(((ConstUtf8Info) cpInfo.get(attributeInfo.attributeNameIndex - 1)).getUtf8(),
                        new ArrayList<>(2) {{
                            add(attributeInfo);
                        }});
            }
        }
        final Map<String, Object> realAttributeMap = new HashMap<>(attributeMap.size() * 4);
        realAttributeMap.put("SourceFile",
                ((ConstUtf8Info) cpInfo.get(((SourceFileAttribute)
                        attributeMap.get("SourceFile").get(0)).getSourceFileIndex() - 1)).getUtf8());
        ArrayList<Attribute_info> cursor;
        if ((cursor = attributeMap.get("InnerClasses")) != null) {
            final ArrayList<InnerClass> innerClasses = new ArrayList<>(cursor.size());
            for (final Attribute_info attribute : cursor) {
                for (final InnerClassAttribute.InnerClassesInfo info : ((InnerClassAttribute) attribute).getInnerClassesInfo()) {
                    // The first two fields are pointer pointed to CONSTANT_class_info in const_pool.
                    // And the CONSTANT_class_info contains a pointer pointed to CONSTANT_Utf8_info
                    // to store its full qualified name.
                    // They are used to store the full qualified names of this class and super class.
                    // The next field is a pointer pointed to CONSTANT_Utf8_info to store the inner
                    // class's simple name.
                    // At last, the remained field stores the access flags of the inner class,
                    // and it can be decoded using decodeAccessFlags(I);Ljava/util/ArrayList method.
                    final String thisFullName = ((ConstUtf8Info) cpInfo.get(((ConstClassInfo) cpInfo.get(info.getInnerClassInfoIndex()
                            - 1)).getIndex() - 1)).getUtf8();
                    final String superFullName = ((ConstUtf8Info) cpInfo.get(((ConstClassInfo) cpInfo.get(info.getOuterClassInfoIndex() - 1)).getIndex() - 1)).getUtf8();
                    final String name = ((ConstUtf8Info) cpInfo.get(info.getInnerNameIndex() - 1)).getUtf8();
                    final ArrayList<ClassReader.ClassFileAccessFlag> accessFlags = decodeClassAccessFlags(info.getInnerClassAccessFlags());
                    innerClasses.add(new InnerClass(name, thisFullName, superFullName, accessFlags));
                }
            }
            realAttributeMap.put("InnerClasses", innerClasses);
        }
        if ((cursor = attributeMap.get("Deprecated")) != null) {
            realAttributeMap.put("Deprecated", true);
        }
        // TODO: 2022/5/24 finish other class attribute_info
        return realAttributeMap;
    }

    protected ArrayList<ClassReader.FieldStore> decodeFields () {
        final ArrayList<ClassReader.FieldStore> fields = new ArrayList<>(fieldTable.size());
        for (final FieldInfo fieldInfo : fieldTable) {
            // The way to decode field:
            // The first field of field_info is its access_flags, they are defined as
            // FIELD_ACCESS_FLAG_ACC_xxx in ClassReader.class.
            // What important is, the field access_flags is same as method access_flags,
            // so we need to build it as an ArrayList of MemberIdentify.
            // The second one is the name index, which pointed to CONSTANT_Utf8_info in const_pool.
            // Then is the descriptor of field_info, it can be used to get the type of the field.
            // We use L+full_qualified_name to represent a class, like Ljava/lang/String.
            // Basic types has been defined in ClassReader.class, and we use '[' to identify array types.
            // the fourth field is the count of attribute, it is not important.
            // At last, it comes to attribute_table, the field_info structure supports next attribute types:
            // Deprecated, ConstantValue, Synthetic, Signature, RuntimeVisibleAnnotations,
            // RuntimeVisibleTypeAnnotations, RuntimeInvisibleAnnotations, RuntimeInvisibleTypeAnnotations.

        }
        return fields;
    }

    protected static ArrayList<ClassReader.FieldAccessFlag> decodeMemberAccessFlags (int accessFlags) throws IllegalFileAttributeException {
        if (accessFlags < 0) {
            throw new IllegalFileAttributeException("The access_flags in method and field must be greater than 0.");
        }
        final ArrayList<ClassReader.FieldAccessFlag> memberIdentifies = new ArrayList<>(2);

        return memberIdentifies;
    }

    protected static ArrayList<ClassReader.ClassFileAccessFlag> decodeClassAccessFlags (int accessFlags)
            throws IllegalFileAttributeException {
        if (accessFlags <= 0) {
            throw new IllegalFileAttributeException("The access_flags must be greater than 0!");
        }
        int cpy = accessFlags;
        final ArrayList<ClassReader.ClassFileAccessFlag> result = new ArrayList<>(2);
        for (int i = DEFAULT_FLAGS.length - 1; i >= 0; --i) {
            final var flag = DEFAULT_FLAGS[i];
            if (flag.accessFlag <= cpy) {
                cpy -= flag.accessFlag;
                result.add(flag);
            }
            if (cpy <= 0) {
                break;
            }
        }
        if (cpy != 0) {
            throw new IllegalFileAttributeException("The file access_flag is illegal!");
        }
        return result;
    }

    public record InnerClass(String name, String typeFullName, String superFullName,
                             ArrayList<ClassReader.ClassFileAccessFlag> accessFlags) {
    }

    public static class ClassResult {
        protected ArrayList<ClassReader.ClassFileAccessFlag> fileAccessFlags;
        protected final int[] bytecodeVersion = new int[]{0x0000, 0x0052};
        protected String resourceFileName;
        protected ArrayList<InnerClass> innerClasses;
        protected ClassReader.ClassStore clz;

        private ClassResult () {
        }

        public void changeAccessFlags (ClassReader.ClassFileAccessFlag... accessFlags) {

        }

        public void changeAccessFlags (int accessFlag) throws IllegalFileAttributeException {
            changeAccessFlags(decodeClassAccessFlags(accessFlag).toArray(ClassReader.ClassFileAccessFlag[]::new));
        }

        public void setMajorBytecodeVersion (int major) throws IllegalFileAttributeException {
            if (major < 0) {
                throw new IllegalFileAttributeException("The bytecode major version must be greater than 0!");
            }
            bytecodeVersion[1] = major;
        }

        public void setMinorBytecodeVersion (int minor) throws IllegalFileAttributeException {
            if (minor < 0) {
                throw new IllegalFileAttributeException("The bytecode minor version must be greater than 0!");
            }
            bytecodeVersion[0] = minor;
        }
    }

    public static final class IllegalFileAttributeException extends DecompilerException {
        public IllegalFileAttributeException (String message) {
            super(message);
        }
    }

    public static byte[] to2bytes (int v) {
        final byte[] code = new byte[2];
        code[1] = (byte) (v & 0XFF);
        code[0] = (byte) (v >> 8 & 0XFF);
        return code;
    }

    public static byte[] to4bytes (int v) {
        final byte[] code = new byte[4];
        code[3] = (byte) (v & 0xFF);
        code[2] = (byte) (v >> 8 & 0XFF);
        code[1] = (byte) (v >> 16 & 0XFF);
        code[0] = (byte) (v >> 24 & 0XFF);
        return code;
    }

    public static void main (String[] args) {
        System.out.println(Arrays.toString(to2bytes(-333)));
        System.out.println(ClassReader.toInt(to2bytes(-333)));
    }
}
