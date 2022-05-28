package top.kkoishi.d4j;

import top.kkoishi.d4j.attr.AnnotationDefaultAttribute;
import top.kkoishi.d4j.attr.BootstrapMethodsAttribute;
import top.kkoishi.d4j.attr.CodeAttribute;
import top.kkoishi.d4j.attr.ConstantValueAttribute;
import top.kkoishi.d4j.attr.DeprecatedAttribute;
import top.kkoishi.d4j.attr.EnclosingMethodAttribute;
import top.kkoishi.d4j.attr.ExceptionsAttribute;
import top.kkoishi.d4j.attr.InnerClassAttribute;
import top.kkoishi.d4j.attr.LineNumberTableAttribute;
import top.kkoishi.d4j.attr.LocalVariableTableAttribute;
import top.kkoishi.d4j.attr.LocalVariableTypeAttribute;
import top.kkoishi.d4j.attr.MethodParametersAttribute;
import top.kkoishi.d4j.attr.ModuleAttribute;
import top.kkoishi.d4j.attr.ModuleMainClassAttribute;
import top.kkoishi.d4j.attr.ModulePackagesAttribute;
import top.kkoishi.d4j.attr.NestHostAttribute;
import top.kkoishi.d4j.attr.NestMembersAttribute;
import top.kkoishi.d4j.attr.PermittedSubclassesAttribute;
import top.kkoishi.d4j.attr.RecordAttribute;
import top.kkoishi.d4j.attr.RuntimeAnnotationAttribute;
import top.kkoishi.d4j.attr.RuntimeInvisibleParameterAnnotationsAttribute;
import top.kkoishi.d4j.attr.RuntimeTypeAnnotationAttribute;
import top.kkoishi.d4j.attr.RuntimeVisibleParameterAnnotationsAttribute;
import top.kkoishi.d4j.attr.SignatureAttribute;
import top.kkoishi.d4j.attr.SourceDebugExtensionAttribute;
import top.kkoishi.d4j.attr.SourceFileAttribute;
import top.kkoishi.d4j.attr.StackMapTableAttribute;
import top.kkoishi.d4j.attr.SyntheticAttribute;
import top.kkoishi.d4j.attr.frames.AppendFrames;
import top.kkoishi.d4j.attr.frames.ChopFrame;
import top.kkoishi.d4j.attr.frames.FullFrame;
import top.kkoishi.d4j.attr.frames.SameFrame;
import top.kkoishi.d4j.attr.frames.SameFrameExtended;
import top.kkoishi.d4j.attr.frames.SameLocals1StackItemFrame;
import top.kkoishi.d4j.attr.frames.SameLocals1StackItemFrameExtended;
import top.kkoishi.d4j.attr.frames.verifi.DoubleVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.FloatVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.IntegerVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.LongVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.NullVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.ObjectVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.TopVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.UninitializedVariableInfo;
import top.kkoishi.d4j.attr.frames.verifi.UninitialzedThis;
import top.kkoishi.d4j.attr.frames.verifi.VerificationTypeInfo;
import top.kkoishi.d4j.cp.ConstClassInfo;
import top.kkoishi.d4j.cp.ConstDoubleInfo;
import top.kkoishi.d4j.cp.ConstDynamicInfo;
import top.kkoishi.d4j.cp.ConstFieldrefInfo;
import top.kkoishi.d4j.cp.ConstFloatInfo;
import top.kkoishi.d4j.cp.ConstIntegerInfo;
import top.kkoishi.d4j.cp.ConstInterfaceMethodrefInfo;
import top.kkoishi.d4j.cp.ConstInvokeDynamicInfo;
import top.kkoishi.d4j.cp.ConstLongInfo;
import top.kkoishi.d4j.cp.ConstMethodHandleInfo;
import top.kkoishi.d4j.cp.ConstMethodTypeInfo;
import top.kkoishi.d4j.cp.ConstMethodrefInfo;
import top.kkoishi.d4j.cp.ConstModuleInfo;
import top.kkoishi.d4j.cp.ConstNameAndTypeInfo;
import top.kkoishi.d4j.cp.ConstPackageInfo;
import top.kkoishi.d4j.cp.ConstStringInfo;
import top.kkoishi.d4j.cp.ConstUtf8Info;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class reader class, used to read data from jvm bytecode.
 * <pre>
 *     The class file structure:
 *     File magic number(0XCAFEBABE)
 *     minor bytecode version and major bytecode version(u2)
 *     const_pool counter(u2) the size of const_pool + 1
 *     const_pool
 *     access_flags
 *     this_class_info and super_class_info(u2, pointer pointed to CONSTANT_class_info in const_pool)
 *     interfaces_count(u2, the amount of interfaces)
 *     interfaces(u2 array, store the pointer pointed to CONSTANT_class_info in const_pool)
 *     fields_count(u2, the size of fields table)
 *     fields
 *     methods_count(u2, the size of methods table)
 *     methods
 *     attributes_count(u2)
 *     attributes
 * </pre>
 *
 * @author KKoishi_
 */
@SuppressWarnings("unused")
public class ClassReader implements Closeable {
    /**
     * The file magic number of a jvm class file.
     */
    public static final byte[] FILE_HEAD = new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    /**
     * The public access_flag of class file.
     */
    public static final int ACC_PUBLIC = 0X0001;

    /**
     * The final access_flag of class file.
     */
    public static final int ACC_FINAL = 0X0010;

    /**
     * An access_flag of class file.
     * Before jdk 1.2, this is always false, and latest version is always true.
     */
    public static final int ACC_SUPER = 0X0020;

    /**
     * The interface access_flag of class file.
     * This is only used by interface class.
     */
    public static final int ACC_INTERFACE = 0X0200;

    /**
     * The abstract access_flag of class file.
     * The abstract class must take this.
     */
    public static final int ACC_ABSTRACT = 0X0400;

    /**
     * The synthetic access_flag of class file, and this is only used by jvm.
     */
    public static final int ACC_SYNTHETIC = 0X1000;

    /**
     * The annotation access_flag of class file.
     * It is only used by annotation class.
     */
    public static final int ACC_ANNOTATION = 0X2000;

    /**
     * The enum access_flag of class file, used to mark if the class is enum.
     */
    public static final int ACC_ENUM = 0X4000;

    /**
     * The module access_flag of module class file.
     */
    public static final int ACC_MODULE = 0X8000;

    /**
     * The byte type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Integer.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_BYTE = 'B';

    /**
     * The char type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Integer.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_CHAR = 'C';

    /**
     * The double type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Double.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_DOUBLE = 'D';

    /**
     * The float type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Float.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_FLOAT = 'F';

    /**
     * The int type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Integer.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_INT = 'I';

    /**
     * The long type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Long.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_LONG = 'J';

    /**
     * The short type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Short.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_SHORT = 'S';

    /**
     * The boolean type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_Boolean.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_BOOLEAN = 'Z';

    /**
     * The String type element_value::value.
     * The corresponding value structure is const_value,
     * and its constant type is CONSTANT_String.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_STRING = 's';

    /**
     * The enum type element_value::value.
     * The corresponding value structure is enum_const_value,
     * and its constant type is not applicable.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_ENUM_CLASS = 'e';

    /**
     * The class type element_value::value.
     * The corresponding value structure is class_info_value,
     * and its constant type is not applicable.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_CLASS = 'c';
    /**
     * The annotation type element_value::value.
     * The corresponding value structure is annotation_value,
     * and its constant type is not applicable.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_ANNOTATION_INTERFACE = '@';

    /**
     * The array type element_value::value.
     * The corresponding value structure is array_value,
     * and its constant type is not applicable.
     *
     * @see top.kkoishi.d4j.attr.RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value
     */
    public static final byte ELEMENT_VALUE_TYPE_ARRAY_TYPE = '[';

    /**
     * The public access_flag of field_info used to illustrate
     * the field has public access.
     */
    public static final int FIELD_ACCESS_FLAG_ACC_PUBLIC = 0X0001;

    /**
     * The private access_flag of field_info used to illustrate
     * the field has private access.
     */
    public static final int FIELD_ACCESS_FLAG_ACC_PRIVATE = 0X0002;

    /**
     * The protected access_flag of field_info used to illustrate
     * the field has protected access.
     */
    public static final int FIELD_ACCESS_FLAG_ACC_PROTECTED = 0X0004;

    /**
     * The static access_flag of field_info used to illustrate
     * the field is static.(Can only accessed by static method,
     * block, etc.)
     */
    public static final int FIELD_ACCESS_FLAG_ACC_STATIC = 0X0008;

    /**
     * The final access_flag of the field_info used to illustrate
     * the field is final.(Can not be replaced by new instance in
     * common method, except using
     * {@link sun.misc.Unsafe#compareAndSwapObject(Object, long, Object, Object)}
     * to force replace.)
     */

    public static final int FIELD_ACCESS_FLAG_ACC_FINAL = 0X0010;

    /**
     * The volatile access_flag of the field_info to illustrate
     * the field is volatile.
     */
    public static final int FIELD_ACCESS_FLAG_ACC_VOLATILE = 0X0040;

    /**
     * The transient access_flag of the field_info to illustrate
     * the field is transient.(Ignored while serialization processing)
     */
    public static final int FIELD_ACCESS_FLAG_ACC_TRANSIENT = 0X0080;

    /**
     * This access_flag is used by jvm to make sure the field accessibility
     * is correctly implemented.
     */
    public static final int FIELD_ACCESS_FLAG_ACC_SYNTHETIC = 0X1000;

    /**
     * The enum access_flag is used to illustrate the field is enum type.
     */
    public static final int FIELD_ACCESS_FLAG_ACC_ENUM = 0X4000;

    /**
     * Illustrate the method is public access.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_PUBLIC = 0X0001;

    /**
     * Illustrate the method is private access.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_PRIVATE = 0X0002;

    /**
     * Illustrate the method is protected.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_PROTECTED = 0X0004;

    /**
     * Illustrate the method is a static method.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_STATIC = 0X0008;

    /**
     * Illustrate the method is final.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_FINAL = 0X0010;

    /**
     * Illustrate the method is decorated by synchronized keyword.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_SYNCHRONIZED = 0X0020;

    /**
     * JVM use, generated by compiler.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_BRIDGE = 0X0040;

    /**
     * Declared with variable number of arguments.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_VARARGS = 0X0080;

    /**
     * Illustrate the method is a native method.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_NATIVE = 0X0100;

    /**
     * Illustrate the method is abstract.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_ABSTRACT = 0X0400;

    /**
     * Illustrate the method is a strictfp method, one of java keywords.
     * This is used to tell jvm to use strict float-point while invoking
     * the method.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_STRICT = 0X0800;

    /**
     * Generated by compiler and used by JVM.
     */
    public static final int METHOD_ACCESS_FLAG_ACC_SYNTHETIC = 0X1000;

    /**
     * Illustrate the method parameter is final.
     */
    public static final int METHOD_PARAMETERS_ACC_FINAL = 0X0010;

    /**
     * Illustrate the method parameter is synthetic.This is used by JVM and
     * generated by compiler.
     */
    public static final int METHOD_PARAMETERS_ACC_SYNTHETIC = 0X1000;

    /**
     * Indicates that the formal parameter was implicitly declared in source code.
     */
    public static final int METHOD_PARAMETERS_ACC_MANDATED = 0X8000;

    /**
     * The ByteReader used to view the input bytes.
     */
    private final ByteReader br;

    /**
     * The size of const_pool.
     */
    protected int constPoolCount = 0;

    /**
     * The major bytecode version.
     */
    protected int majorBytecodeVersion = 0x0052;

    /**
     * The minor bytecode version.
     */
    protected int minorBytecodeVersion = 0x0000;

    /**
     * Const_pool of a compiled class file.
     */
    protected ArrayList<ConstPoolInfo> cpInfo;

    /**
     * The class file access_flags.
     */
    protected int accessFlags = ACC_PUBLIC + ACC_SUPER;

    /**
     * A byte array which its length is 2. And after converted to unsigned int, this is the index of
     * one CONSTANT_Class_info in const_pool.
     * <br>
     * This is used to index the full-qualified-name of this class.
     */
    protected byte[] thisClassIndex;

    /**
     * A byte array which its length is 2. And after converted to unsigned int, this is the index of
     * one CONSTANT_Class_info in const_pool.
     * <br>
     * This is used to index the full-qualified-name of super class.
     * <br>
     * <b>If this is zero, then the super class is java.lang.Object.</b>
     */
    protected byte[] superClassIndex;

    /**
     * The size of interfaces.
     */
    protected int interfaceCount = 0;

    /**
     * The interfaces table stored the index of CONSTANT_Class_info in
     * const_pool.
     * And this stores the implemented interfaces.
     */
    protected ArrayList<Integer> interfaceIndexes;

    /**
     * The field_info table count.
     */
    protected int fieldsCount = 0;

    /**
     * The field_info table.
     * This table stores all the fields of the class.
     */
    protected ArrayList<FieldInfo> fieldTable;

    /**
     * The method_info table count.
     */
    protected int methodsCount = 0;

    /**
     * The method_info table.
     * This table stores all the methods of the class.
     */
    protected ArrayList<MethodInfo> methodTable;

    /**
     * The additional file attributes amount.
     */
    protected int classFileAttributesCount;

    /**
     * The additional file attribute_info table, it stores some extended
     * attributes like SourceFile_attribute(used to store the source file name).
     */
    protected ArrayList<AttributeInfo> classFileAttributeTable;

    private ClassReader (ByteReader br) {
        this.br = br;
    }

    public ClassReader (byte[] bytes) {
        this(new ByteReader(bytes));
    }

    public int getAccessFlags () {
        return accessFlags;
    }

    public byte[] getThisClassIndex () {
        return thisClassIndex;
    }

    public byte[] getSuperClassIndex () {
        return superClassIndex;
    }

    public int getInterfaceCount () {
        return interfaceCount;
    }

    public ArrayList<Integer> getInterfaceIndexes () {
        return interfaceIndexes;
    }

    public int getFieldsCount () {
        return fieldsCount;
    }

    public ArrayList<FieldInfo> getFieldTable () {
        return fieldTable;
    }

    public int getMethodsCount () {
        return methodsCount;
    }

    public ArrayList<MethodInfo> getMethodTable () {
        return methodTable;
    }

    public int getClassFileAttributesCount () {
        return classFileAttributesCount;
    }

    public ArrayList<AttributeInfo> getClassFileAttributeTable () {
        return classFileAttributeTable;
    }

    public String getFullQualifiedName () {
        return ((ConstUtf8Info) cpInfo.get(((ConstClassInfo) cpInfo.get(toInt(thisClassIndex) - 1)).getIndex() - 1)).getUtf8();
    }

    /**
     * Read the data from the transported byte array.
     *
     * @throws DecompilerException while the class file is illegal.
     */
    public void read () throws DecompilerException {
        readFileInfo();
        readCpInfo();
        readAccessFlags();
        readClassIndexes();
        readInterfaces();
        readFields();
        readMethods();
        readClassFileAttributes();
    }

    public static int toInt (byte[] arr) {
        int val = 0;
        int len = arr.length;
        final int end = len;
        for (int i = 0; i < end; i++) {
            int n = (arr[i]) & 0XFF;
            n <<= (--len) * 8;
            val += n;
        }
        return val;
    }

    public static void report (ClassReader cr) {
        System.out.println("Major byte code version:" + cr.getMajorBytecodeVersion());
        System.out.println("Minor byte code version:" + cr.getMinorBytecodeVersion());
        System.out.println("CONSTANT_info amount:" + cr.getConstPoolCount());
        System.out.println("CONSTANT_POOL:");
        cr.getCpInfo().forEach(System.out::println);
        System.out.println("Class information:");
        System.out.println("\tAccess Flags:" + cr.accessFlags);
        System.out.println("\tThis class Name:" + cr.getCpInfo().get(toInt(cr.thisClassIndex) - 1));
        System.out.println("\tSuper class Name:" + cr.getCpInfo().get(toInt(cr.superClassIndex) - 1));
        System.out.println("Interface count:" + cr.getInterfaceCount());
        System.out.println("Interfaces:");
        cr.getInterfaceIndexes().forEach(System.out::println);
        System.out.println("Field count:" + cr.fieldsCount);
        System.out.println("Field table:");
        for (final FieldInfo fieldInfo : cr.getFieldTable()) {
            final StringBuilder sb = new StringBuilder("FieldInfo{");
            sb.append("Access Flag=")
                    .append(fieldInfo.getAccessFlags())
                    .append(", name=\"")
                    .append(cr.cpInfo.get(fieldInfo.getNameIndex() - 1))
                    .append("\", descriptor=")
                    .append(cr.cpInfo.get(fieldInfo.getDescriptorIndex() - 1))
                    .append(", attributeCount=")
                    .append(fieldInfo.getAttributeCount())
                    .append(", attributes=[");
            if (fieldInfo.getAttributeCount() == 0) {
                sb.append(']');
            } else {
                final ArrayList<AttributeInfo> attributes = fieldInfo.getAttributes();
                for (int i = 0; i < attributes.size() - 1; i++) {
                    final AttributeInfo attribute = attributes.get(i);
                    sb.append(cr.cpInfo.get(attribute.attributeNameIndex - 1))
                            .append("->")
                            .append(attribute)
                            .append(", ");
                }
                sb.append(cr.cpInfo.get(attributes.get(attributes.size() - 1)
                                .attributeNameIndex - 1)).append("->")
                        .append(attributes.get(attributes.size() - 1))
                        .append(", ")
                        .append(']');
            }
            System.out.println(sb);
        }
        System.out.println("Method table:");
        for (final MethodInfo methodInfo : cr.methodTable) {
            final StringBuilder sb = new StringBuilder("MethodInfo{");
            sb.append("AccessFlag=")
                    .append(methodInfo.getAccessFlags())
                    .append(", Name=\"")
                    .append(cr.cpInfo.get(methodInfo.getNameIndex() - 1))
                    .append("\", descriptor=\"")
                    .append(cr.cpInfo.get(methodInfo.getDescriptorIndex() - 1))
                    .append(", attributes=")
                    .append(Arrays.deepToString(methodInfo.getAttributes()));
            System.out.println(sb.append('}'));
        }
        System.out.println("Extended Attribute Table:" + cr.classFileAttributesCount);
        cr.classFileAttributeTable.forEach(System.out::println);
    }

    protected void readMethods () throws IllegalAttributeNameException, IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        methodsCount = toInt(br.read(2));
        methodTable = new ArrayList<>(methodsCount);
        for (int i = 0; i < methodsCount; i++) {
            final int attributesCount;
            methodTable.add(new MethodInfo(toInt(br.read(2)),
                    toInt(br.read(2)),
                    toInt(br.read(2)),
                    (attributesCount = toInt(br.read(2))),
                    readMethodAttributeTable(attributesCount)));

        }
    }

    @SuppressWarnings({"ConstantConditions"})
    protected AttributeInfo[] readMethodAttributeTable (int count) throws IllegalAttributeNameException, IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new AttributeInfo[0];
        }
        final AttributeInfo[] attributes = new AttributeInfo[count];
        for (int i = 0; i < count; i++) {
            final int attributeNameIndex = toInt(br.read(2));
            final int attributeLength = toInt(br.read(4));
            final String name = ((ConstUtf8Info) cpInfo.get(attributeNameIndex - 1)).getUtf8();
            switch (name) {
                case "Code": {
                    final int codeLength;
                    final int exceptionTableLength;
                    final int attributesCount;
                    attributes[i] = new CodeAttribute(attributeNameIndex,
                            attributeLength,
                            toInt(br.read(2)),
                            toInt(br.read(2)),
                            (codeLength = toInt(br.read(4))),
                            readNByte(codeLength),
                            (exceptionTableLength = toInt(br.read(2))),
                            readCodeExceptions(exceptionTableLength),
                            (attributesCount = toInt(br.read(2))),
                            readCodeAttributeTable(attributesCount));
                    break;
                }
                case "Exceptions": {
                    final int numberOfExceptions;
                    attributes[i] = new ExceptionsAttribute(attributeNameIndex,
                            attributeLength,
                            (numberOfExceptions = toInt(br.read(2))),
                            readExceptionIndexTables(numberOfExceptions));
                    break;
                }
                case "MethodParameters": {
                    final byte parametersCount = br.read();
                    final MethodParametersAttribute.Parameter[] parameters = new MethodParametersAttribute.Parameter[parametersCount];
                    for (int j = 0; j < parametersCount; j++) {
                        parameters[j] = new MethodParametersAttribute.Parameter(toInt(br.read(2)), toInt(br.read(2)));
                    }
                    attributes[i] = new MethodParametersAttribute(attributeNameIndex, attributeLength, parametersCount, parameters);
                    break;
                }
                case "AnnotationDefault": {
                    attributes[i] = new AnnotationDefaultAttribute(attributeNameIndex, attributeLength, readElementValue());
                    break;
                }
                case "RuntimeVisibleParameterAnnotations": {
                    final byte parametersCount = br.read();
                    final RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[] parameterAnnotations =
                            new RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[parametersCount];
                    for (int j = 0; j < parametersCount; j++) {
                        final int numAnnotations = toInt(br.read(2));
                        final RuntimeAnnotationAttribute.Annotation[] annotations = new RuntimeAnnotationAttribute.Annotation[numAnnotations];
                        for (int k = 0; k < numAnnotations; k++) {
                            final int numElementValuePairs;
                            annotations[k] = new RuntimeAnnotationAttribute.Annotation(toInt(br.read(2)),
                                    (numElementValuePairs = toInt(br.read(2))),
                                    readElementValuePairs(numElementValuePairs));
                        }
                        parameterAnnotations[j] = new RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation(numAnnotations, annotations);
                    }
                    attributes[i] = new RuntimeVisibleParameterAnnotationsAttribute(attributeNameIndex, attributeLength, parametersCount, parameterAnnotations);
                    break;
                }
                case "RuntimeInvisibleParameterAnnotations": {
                    final byte parametersCount = br.read();
                    final RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[] parameterAnnotations =
                            new RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[parametersCount];
                    for (int j = 0; j < parametersCount; j++) {
                        final int numAnnotations = toInt(br.read(2));
                        final RuntimeAnnotationAttribute.Annotation[] annotations = new RuntimeAnnotationAttribute.Annotation[numAnnotations];
                        for (int k = 0; k < numAnnotations; k++) {
                            final int numElementValuePairs;
                            annotations[k] = new RuntimeAnnotationAttribute.Annotation(toInt(br.read(2)),
                                    (numElementValuePairs = toInt(br.read(2))),
                                    readElementValuePairs(numElementValuePairs));
                        }
                        parameterAnnotations[j] = new RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation(numAnnotations, annotations);
                    }
                    attributes[i] = new RuntimeInvisibleParameterAnnotationsAttribute(attributeNameIndex, attributeLength, parametersCount, parameterAnnotations);
                    break;
                }
                case "Synthetic": {
                    attributes[i] = new SyntheticAttribute(attributeNameIndex);
                    break;
                }
                case "Deprecated": {
                    attributes[i] = new DeprecatedAttribute(attributeNameIndex);
                }
                case "Signature": {
                    attributes[i] = new SignatureAttribute(attributeNameIndex, toInt(br.read(2)));
                }
                case "RuntimeVisibleAnnotations": {
                    final int numAnnotations = toInt(br.read(2));
                    attributes[i] = new RuntimeAnnotationAttribute.RuntimeVisibleAnnotationAttribute(attributeNameIndex,
                            attributeLength, numAnnotations, readAnnotationAttribute(numAnnotations));
                    break;
                }
                case "RuntimeInvisibleAnnotations": {
                    final int numAnnotations = toInt(br.read(2));
                    attributes[i] = new RuntimeAnnotationAttribute.RuntimeInvisibleAnnotationAttribute(attributeNameIndex,
                            attributeLength, numAnnotations, readAnnotationAttribute(numAnnotations));
                    break;
                }
                case "RuntimeVisibleTypeAnnotations": {
                    final int numAnnotations = toInt(br.read(2));
                    attributes[i] = new RuntimeTypeAnnotationAttribute.RuntimeVisibleTypeAnnotationsAttribute(attributeNameIndex,
                            attributeLength, numAnnotations, readMethodTypeAnnotations(numAnnotations));
                    break;
                }
                case "RuntimeInvisibleTypeAnnotations": {
                    final int numAnnotations = toInt(br.read(2));
                    attributes[i] = new RuntimeTypeAnnotationAttribute.RuntimeInvisibleTypeAnnotationsAttribute(attributeNameIndex,
                            attributeLength, numAnnotations, readMethodTypeAnnotations(numAnnotations));
                    break;
                }
                default: {
                    throw new IllegalAttributeNameException("The name of attribute \""
                            + name + "\" is not the attribute of method_info!");
                }
            }
        }
        return attributes;
    }

    protected ArrayList<CodeAttribute.CodeException> readCodeExceptions (int exceptionTableLength) {
        if (exceptionTableLength == 0) {
            return new ArrayList<>(0);
        }
        final ArrayList<CodeAttribute.CodeException> codeExceptionTable = new ArrayList<>(exceptionTableLength);
        for (int i = 0; i < exceptionTableLength; i++) {
            codeExceptionTable.add(new CodeAttribute.CodeException(toInt(br.read(2)),
                    toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2))));
        }
        return codeExceptionTable;
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation[] readFieldInfoAndRecordTypeAnnotations (int count)
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new RuntimeTypeAnnotationAttribute.TypeAnnotation[0];
        }
        final RuntimeTypeAnnotationAttribute.TypeAnnotation[] typeAnnotations = new RuntimeTypeAnnotationAttribute.TypeAnnotation[count];
        for (int i = 0; i < count; i++) {
            typeAnnotations[i] = readFieldInfoAndRecordTypeAnnotation();
        }
        return typeAnnotations;
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation readFieldInfoAndRecordTypeAnnotation ()
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        final byte tag = br.read();
        final int pathsLength;
        final int numElementValuePairs;
        return new RuntimeTypeAnnotationAttribute.TypeAnnotation(tag, readFieldRecordTargetInfo(tag),
                new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath((pathsLength = toInt(br.read(2))), readPaths(pathsLength)),
                toInt(br.read(2)),
                (numElementValuePairs = toInt(br.read(2))),
                readElementValuePairs(numElementValuePairs));
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo readFieldRecordTargetInfo (byte tag) {
        if (tag != RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_DECLARE_FIELD_RECORD) {
            throw new IllegalArgumentException("");
        }
        return new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.EmptyTarget();
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation[] readCodeTypeAnnotations (int count) throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new RuntimeTypeAnnotationAttribute.TypeAnnotation[0];
        }
        final RuntimeTypeAnnotationAttribute.TypeAnnotation[] typeAnnotations = new RuntimeTypeAnnotationAttribute.TypeAnnotation[count];
        for (int i = 0; i < count; i++) {
            typeAnnotations[i] = readCodeTypeAnnotation();
        }
        return typeAnnotations;
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation readCodeTypeAnnotation ()
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        final byte tag = br.read();
        final int pathsLength;
        final int numElementValuePairs;
        return new RuntimeTypeAnnotationAttribute.TypeAnnotation(tag, readCodeTargetInfo(tag),
                new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath((pathsLength = toInt(br.read(2))), readPaths(pathsLength)),
                toInt(br.read(2)),
                (numElementValuePairs = toInt(br.read(2))),
                readElementValuePairs(numElementValuePairs));
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo readCodeTargetInfo (byte tag) {
        if (tag >= RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_DECLARE_LOCAL_VAR
                && tag <= RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_ARGUMENT_REFERENCE_METHOD) {
            return switch (tag) {
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_DECLARE_LOCAL_VAR:
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_DECLARE_RESOURCE_VAR: {
                    final int length = toInt(br.read(2));
                    final RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.LocalVarTarget.LocalVar[] localVars =
                            new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.LocalVarTarget.LocalVar[length];
                    for (int i = 0; i < length; i++) {
                        localVars[i] =
                                new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.LocalVarTarget.LocalVar(
                                        toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2)));
                    }
                    yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.LocalVarTarget(length, localVars);
                }
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_EXCEPTION_PARAMETER: {
                    yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.CatchTarget(toInt(br.read(2)));
                }
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_EXPRESSION_INSTANCEOF:
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_EXPRESSION_NEW:
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_METHOD_REFERENCE_CONSTRUCTOR:
                case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_METHOD_REFERENCE_METHOD: {
                    yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.OffsetTarget(toInt(br.read(2)));
                }
                default: {
                    yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.TypeArgumentTarget(
                            toInt(br.read(2)), br.read());
                }
            };
        }
        throw new IllegalArgumentException("");
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation[] readClassFileTypeAnnotations (int count)
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new RuntimeTypeAnnotationAttribute.TypeAnnotation[0];
        }
        final RuntimeTypeAnnotationAttribute.TypeAnnotation[] typeAnnotations = new RuntimeTypeAnnotationAttribute.TypeAnnotation[count];
        for (int i = 0; i < count; i++) {
            typeAnnotations[i] = readClassFileTypeAnnotation();
        }
        return typeAnnotations;
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation readClassFileTypeAnnotation ()
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        final byte tag = br.read();
        final int pathsLength;
        final int numElementValuePairs;
        return new RuntimeTypeAnnotationAttribute.TypeAnnotation(tag, readClassFileTargetInfo(tag),
                new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath((pathsLength = toInt(br.read(2))), readPaths(pathsLength)),
                toInt(br.read(2)),
                (numElementValuePairs = toInt(br.read(2))),
                readElementValuePairs(numElementValuePairs));
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo readClassFileTargetInfo (byte tag) {
        return switch (tag) {
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_PARAMETER_CLASS_INTERFACE: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.TypeParameterTarget(br.read());
            }
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_BOUND_CLASS_INTERFACE:
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_EXTEND_IMPLEMENTS: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.SuperTypeTarget(toInt(br.read(2)));
            }
            default: {
                throw new IllegalArgumentException("");
            }
        };
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation[] readMethodTypeAnnotations (int count)
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new RuntimeTypeAnnotationAttribute.TypeAnnotation[0];
        }
        final RuntimeTypeAnnotationAttribute.TypeAnnotation[] typeAnnotations = new RuntimeTypeAnnotationAttribute.TypeAnnotation[count];
        for (int i = 0; i < count; i++) {
            typeAnnotations[i] = readMethodTypeAnnotation();
        }
        return typeAnnotations;
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation readMethodTypeAnnotation () throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        final byte tag = br.read();
        final int pathsLength;
        final int numElementValuePairs;
        return new RuntimeTypeAnnotationAttribute.TypeAnnotation(tag, readMethodTargetInfo(tag),
                new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath((pathsLength = toInt(br.read(2))), readPaths(pathsLength)),
                toInt(br.read(2)),
                (numElementValuePairs = toInt(br.read(2))),
                readElementValuePairs(numElementValuePairs));
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo readMethodTargetInfo (byte tag) {
        return switch (tag) {
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_RECEIVER_METHOD_CONSTRUCTOR:
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_RETURN_METHOD_CONSTRUCTED_OBJECT: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.EmptyTarget();
            }
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_BOUND_METHOD_CONSTRUCTOR: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.TypeParameterBoundTarget(br.read(), br.read());
            }
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_PARAMETER_METHOD_CONSTRUCTOR: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.TypeParameterTarget(br.read());
            }
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_FORMAL_PARAMETER: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.FormalParameterTarget(br.read());
            }
            case RuntimeTypeAnnotationAttribute.TypeAnnotation.TYPE_THROW_CLAUSE: {
                yield new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetInfo.ThrowTarget(toInt(br.read(2)));
            }
            default: {
                throw new IllegalArgumentException("");
            }
        };
    }

    protected RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path[] readPaths (int pathsLength) {
        if (pathsLength == 0) {
            return new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path[0];
        }
        final RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path[] paths =
                new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path[pathsLength];
        for (int i = 0; i < pathsLength; i++) {
            paths[i] = new RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path(br.read(), br.read());
        }
        return paths;
    }

    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected AttributeInfo[] readCodeAttributeTable (int count) throws IllegalAttributeNameException {
        if (count == 0) {
            return new AttributeInfo[0];
        }
        final AttributeInfo[] attributes = new AttributeInfo[count];
        for (int i = 0; i < count; i++) {
            final int attributeNameIndex = toInt(br.read(2));
            final int attributeLength = toInt(br.read(4));
            final String name = ((ConstUtf8Info) cpInfo.get(attributeNameIndex - 1)).getUtf8();
            switch (name) {
                case "LineNumberTable": {
                    final int lineNumberTableLength = toInt(br.read(2));
                    final LineNumberTableAttribute.LineNumber[] lineNumberTable = new LineNumberTableAttribute.LineNumber[lineNumberTableLength];
                    for (int j = 0; j < lineNumberTableLength; j++) {
                        lineNumberTable[j] = new LineNumberTableAttribute.LineNumber(toInt(br.read(2)), toInt(br.read(2)));
                    }
                    attributes[i] = new LineNumberTableAttribute(attributeNameIndex, attributeLength, lineNumberTableLength, lineNumberTable);
                    break;
                }
                case "LocalVariableTable": {
                    final int localVariableTableLength = toInt(br.read(2));
                    final LocalVariableTableAttribute.LocalVariable[] localVariableTable = new LocalVariableTableAttribute.LocalVariable[localVariableTableLength];
                    for (int j = 0; j < localVariableTableLength; j++) {
                        localVariableTable[j] = new LocalVariableTableAttribute.LocalVariable(toInt(br.read(2)),
                                toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2)));
                    }
                    attributes[i] = new LocalVariableTableAttribute(attributeNameIndex, attributeLength, localVariableTableLength, localVariableTable);
                    break;
                }
                case "LocalVariableTypeTable": {
                    final int localVariableTypeTableLength = toInt(br.read(2));
                    final LocalVariableTypeAttribute.localVariableType[] localVariableTypeTable = new LocalVariableTypeAttribute.localVariableType[localVariableTypeTableLength];
                    for (int j = 0; j < localVariableTypeTableLength; j++) {
                        localVariableTypeTable[j] = new LocalVariableTypeAttribute.localVariableType(toInt(br.read(2)),
                                toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2)));
                    }
                    attributes[i] = new LocalVariableTypeAttribute(attributeNameIndex, attributeLength, localVariableTypeTableLength, localVariableTypeTable);
                    break;
                }
                case "StackMapTable": {
                    final int numberOfEntries;
                    attributes[i] = new StackMapTableAttribute(attributeNameIndex,
                            attributeLength,
                            (numberOfEntries = toInt(br.read(2))),
                            readStackMapFrames(numberOfEntries));
                    break;
                }
                default: {
                    throw new IllegalAttributeNameException("The name of attribute \""
                            + name + "\" is not the attribute of code_info!");
                }
            }
        }
        return attributes;
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    protected ArrayList<StackMapTableAttribute.StackMapFrame> readStackMapFrames (int count) {
        if (count == 0) {
            return new ArrayList<>(0);
        }
        final ArrayList<StackMapTableAttribute.StackMapFrame> frames = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final byte frameType = br.read();
            switch (frameType) {
                case (byte) 247: {
                    frames.add(new SameLocals1StackItemFrameExtended(frameType, toInt(br.read(2)), readVerificationTypeInfo()));
                    break;
                }
                case (byte) 248:
                case (byte) 249:
                case (byte) 250: {
                    frames.add(new ChopFrame(frameType, toInt(br.read(2))));
                    break;
                }
                case (byte) 251: {
                    frames.add(new SameFrameExtended(frameType, toInt(br.read(2))));
                    break;
                }
                case (byte) 252:
                case (byte) 253:
                case (byte) 254: {
                    frames.add(new AppendFrames(frameType, toInt(br.read(2)),
                            readVerificationTypeInfo((frameType & 0XFF) - 251)));
                    break;
                }
                case (byte) 255: {
                    final int numberOfLocals;
                    final int numberOfStackItems;
                    frames.add(new FullFrame(frameType,
                            toInt(br.read(2)),
                            (numberOfLocals = toInt(br.read(2))),
                            readVerificationTypeInfo(numberOfLocals)
                            , (numberOfStackItems = toInt(br.read(2))),
                            readVerificationTypeInfo(numberOfStackItems)));
                    break;
                }
                default: {
                    if (frameType >= 0 && frameType <= 63) {
                        frames.add(new SameFrame(frameType));
                    } else //noinspection ConstantConditions
                        if (frameType >= 64 && frameType <= 127) {
                            frames.add(new SameLocals1StackItemFrame(frameType, readVerificationTypeInfo()));
                        } else {
                            throw new IllegalArgumentException("The frame_type byte is illegal!");
                        }
                }
            }
        }
        return frames;
    }

    protected VerificationTypeInfo readVerificationTypeInfo () {
        final byte tag = br.read();
        System.out.println("Verification tag:" + tag);
        return switch (tag) {
            case VerificationTypeInfo.TOP_VARIABLE_INFO: {
                yield new TopVariableInfo();
            }
            case VerificationTypeInfo.INTEGER_VARIABLE_INFO: {
                yield new IntegerVariableInfo();
            }
            case VerificationTypeInfo.DOUBLE_VARIABLE_INFO: {
                yield new DoubleVariableInfo();
            }
            case VerificationTypeInfo.FLOAT_VARIABLE_INFO: {
                yield new FloatVariableInfo();
            }
            case VerificationTypeInfo.NULL_VARIABLE_INFO: {
                yield new NullVariableInfo();
            }
            case VerificationTypeInfo.UNINITIALIZED_VARIABLE_INFO: {
                yield new UninitializedVariableInfo(toInt(br.read(2)));
            }
            case VerificationTypeInfo.OBJECT_VARIABLE_INFO: {
                yield new ObjectVariableInfo(toInt(br.read(2)));
            }
            case VerificationTypeInfo.UNINITIALIZED_THIS: {
                yield new UninitialzedThis();
            }
            case VerificationTypeInfo.LONG_VARIABLE_INFO: {
                yield new LongVariableInfo();
            }
            default: {
                throw new IllegalArgumentException("The tag of verification_type_info is illegal, got " + tag);
            }
        };
    }

    protected ArrayList<VerificationTypeInfo> readVerificationTypeInfo (int count) {
        if (count == 0) {
            return new ArrayList<>(0);
        }
        final ArrayList<VerificationTypeInfo> verificationTypeInfo = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            verificationTypeInfo.add(readVerificationTypeInfo());
        }
        return verificationTypeInfo;
    }

    protected ArrayList<Byte> readNByte (int n) {
        final ArrayList<Byte> bits = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            bits.add(br.read());
        }
        return bits;
    }

    protected ArrayList<ExceptionsAttribute> readExceptionTable (int count) {
        if (count == 0) {
            return new ArrayList<>(0);
        }
        final ArrayList<ExceptionsAttribute> exceptions = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final int numberOfExceptions;
            exceptions.add(new ExceptionsAttribute(toInt(br.read(2)),
                    toInt(br.read(4)),
                    (numberOfExceptions = toInt(br.read(2))),
                    readExceptionIndexTables(numberOfExceptions)));
        }
        return exceptions;
    }

    protected ExceptionsAttribute.ExceptionIndexTable[] readExceptionIndexTables (int count) {
        if (count == 0) {
            return new ExceptionsAttribute.ExceptionIndexTable[0];
        }
        final ExceptionsAttribute.ExceptionIndexTable[] exceptionIndexTables = new ExceptionsAttribute.ExceptionIndexTable[count];
        for (int i = 0; i < count; i++) {
            exceptionIndexTables[i] = new ExceptionsAttribute.ExceptionIndexTable(toInt(br.read(2)));
        }
        return exceptionIndexTables;
    }

    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected void readClassFileAttributes () throws IllegalRuntimeAnnotationAttributeElementValueTagByteException, IllegalAttributeNameException {
        classFileAttributesCount = toInt(br.read(2));
        classFileAttributeTable = new ArrayList<>(classFileAttributesCount);
        for (int i = 0; i < classFileAttributesCount; i++) {
            final int attributeNameIndex = toInt(br.read(2));
            final int attributesLength = toInt(br.read(4));
            final String name = ((ConstUtf8Info) cpInfo.get(attributeNameIndex - 1)).getUtf8();
            switch (name) {
                case "SourceFile": {
                    classFileAttributeTable.add(new SourceFileAttribute(attributeNameIndex, attributesLength, toInt(br.read(2))));
                    break;
                }
                case "InnerClasses": {
                    final int numberOfClass = toInt(br.read(2));
                    final ArrayList<InnerClassAttribute.InnerClassesInfo> classes = new ArrayList<>(numberOfClass);
                    for (int j = 0; j < numberOfClass; j++) {
                        classes.add(new InnerClassAttribute.InnerClassesInfo(toInt(br.read(2)),
                                toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2))));
                    }
                    classFileAttributeTable.add(new InnerClassAttribute(attributeNameIndex, attributesLength, numberOfClass, classes));
                    break;
                }
                case "Synthetic": {
                    classFileAttributeTable.add(new SyntheticAttribute(attributeNameIndex));
                    break;
                }
                case "Deprecated": {
                    classFileAttributeTable.add(new DeprecatedAttribute(attributeNameIndex));
                    break;
                }
                case "Signature": {
                    classFileAttributeTable.add(new SignatureAttribute(attributeNameIndex, toInt(br.read(2))));
                    break;
                }
                case "RuntimeVisibleAnnotations": {
                    final int numAnnotations;
                    classFileAttributeTable.add(new RuntimeAnnotationAttribute.RuntimeVisibleAnnotationAttribute(
                            attributeNameIndex, attributesLength, (numAnnotations = toInt(br.read(2))), readAnnotationAttribute(numAnnotations)));
                    break;
                }
                case "RuntimeInvisibleAnnotations": {
                    final int numAnnotations;
                    classFileAttributeTable.add(new RuntimeAnnotationAttribute.RuntimeInvisibleAnnotationAttribute(
                            attributeNameIndex, attributesLength, (numAnnotations = toInt(br.read(2))), readAnnotationAttribute(numAnnotations)));
                    break;
                }
                case "RuntimeVisibleTypeAnnotations": {
                    final int numTypeAnnotations;
                    classFileAttributeTable.add(new RuntimeTypeAnnotationAttribute.RuntimeVisibleTypeAnnotationsAttribute(
                            attributeNameIndex, attributesLength, (numTypeAnnotations = toInt(br.read(2))), readClassFileTypeAnnotations(numTypeAnnotations)));
                    break;
                }
                case "RuntimeInvisibleTypeAnnotations": {
                    final int numTypeAnnotations;
                    classFileAttributeTable.add(new RuntimeTypeAnnotationAttribute.RuntimeInvisibleTypeAnnotationsAttribute(
                            attributeNameIndex, attributesLength, (numTypeAnnotations = toInt(br.read(2))), readClassFileTypeAnnotations(numTypeAnnotations)));
                    break;
                }
                case "SourceDebugExtension": {
                    final byte[] debugExtensionTable = br.read(attributesLength);
                    classFileAttributeTable.add(new SourceDebugExtensionAttribute(attributeNameIndex, attributesLength, debugExtensionTable));
                    break;
                }
                case "EnclosingMethod": {
                    classFileAttributeTable.add(new EnclosingMethodAttribute(attributeNameIndex, attributesLength, toInt(br.read(2)), toInt(br.read(2))));
                    break;
                }
                case "BootstrapMethods": {
                    final int numBootstrapMethods = toInt(br.read(2));
                    final BootstrapMethodsAttribute.BootstrapMethod[] bootstrapMethods =
                            new BootstrapMethodsAttribute.BootstrapMethod[numBootstrapMethods];
                    for (int j = 0; j < numBootstrapMethods; j++) {
                        final int refIndex = toInt(br.read(2));
                        final int numArgs = toInt(br.read(2));
                        final int[] args = new int[numArgs];
                        for (int k = 0; k < numArgs; k++) {
                            args[k] = toInt(br.read(2));
                        }
                        bootstrapMethods[j] = new BootstrapMethodsAttribute.BootstrapMethod(refIndex, numBootstrapMethods, args);
                    }
                    classFileAttributeTable.add(new BootstrapMethodsAttribute(attributeNameIndex, attributesLength, numBootstrapMethods, bootstrapMethods));
                    break;
                }
                case "Module": {
                    addModuleAttributeImpl(attributeNameIndex, attributesLength);
                    break;
                }
                case "ModulePackages": {
                    final int packagesCount = toInt(br.read(2));
                    final int[] packages = new int[packagesCount];
                    for (int j = 0; j < packagesCount; j++) {
                        packages[j] = toInt(br.read(2));
                    }
                    classFileAttributeTable.add(new ModulePackagesAttribute(attributeNameIndex, attributesLength, packagesCount, packages));
                    break;
                }
                case "ModuleMainClass": {
                    classFileAttributeTable.add(new ModuleMainClassAttribute(attributeNameIndex, attributesLength, toInt(br.read(2))));
                    break;
                }
                case "NestHost": {
                    classFileAttributeTable.add(new NestHostAttribute(attributeNameIndex, attributesLength, toInt(br.read(2))));
                    break;
                }
                case "NestMembers": {
                    final int numberOfClasses = toInt(br.read(2));
                    final int[] classes = new int[numberOfClasses];
                    for (int j = 0; j < numberOfClasses; j++) {
                        classes[j] = toInt(br.read(2));
                    }
                    classFileAttributeTable.add(new NestMembersAttribute(attributeNameIndex, attributesLength, numberOfClasses, classes));
                    break;
                }
                case "Record": {
                    addRecordImpl(attributeNameIndex, attributesLength, name);
                    break;
                }
                case "PermittedSubclasses": {
                    final int numberOfClasses = toInt(br.read(2));
                    final int[] classes = new int[numberOfClasses];
                    for (int j = 0; j < numberOfClasses; j++) {
                        classes[j] = toInt(br.read(2));
                    }
                    classFileAttributeTable.add(new PermittedSubclassesAttribute(attributeNameIndex, attributesLength, numberOfClasses, classes));
                    break;
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void addRecordImpl (int attributeNameIndex, int attributesLength, String name)
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException, IllegalAttributeNameException {
        final int componentsCount = toInt(br.read(2));
        final ArrayList<RecordAttribute.RecordComponentInfo> components = new ArrayList<>(componentsCount);
        for (int j = 0; j < componentsCount; j++) {
            final int nameIndex = toInt(br.read(2));
            final int descriptorIndex = toInt(br.read(2));
            final int attributesCount = toInt(br.read(2));
            final AttributeInfo[] attributes = new AttributeInfo[attributesCount];
            for (int k = 0; k < attributesCount; k++) {
                final int attributeNameIndex1 = toInt(br.read(2));
                final int attributeLength = toInt(br.read(2));
                final String name1 = ((ConstUtf8Info) cpInfo.get(attributeNameIndex1 - 1)).getUtf8();
                attributes[k] = switch (name1) {
                    case "Signature": {
                        yield new SignatureAttribute(nameIndex, toInt(br.read(2)));
                    }
                    case "RuntimeVisibleAnnotations": {
                        final int numAnnotation = toInt(br.read(2));
                        yield new RuntimeAnnotationAttribute.RuntimeVisibleAnnotationAttribute(nameIndex,
                                attributeLength, numAnnotation, readAnnotationAttribute(numAnnotation));
                    }
                    case "RuntimeInvisibleAnnotations": {
                        final int numAnnotation = toInt(br.read(2));
                        yield new RuntimeAnnotationAttribute.RuntimeInvisibleAnnotationAttribute(nameIndex,
                                attributeLength, numAnnotation, readAnnotationAttribute(numAnnotation));
                    }
                    case "RuntimeVisibleTypeAnnotations": {
                        final int numAnnotations;
                        yield new RuntimeTypeAnnotationAttribute.RuntimeVisibleTypeAnnotationsAttribute(nameIndex,
                                attributeLength, (numAnnotations = toInt(br.read(2))), readFieldInfoAndRecordTypeAnnotations(numAnnotations));
                    }
                    case "RuntimeInvisibleTypeAnnotations": {
                        final int numAnnotations;
                        yield new RuntimeTypeAnnotationAttribute.RuntimeInvisibleTypeAnnotationsAttribute(nameIndex,
                                attributeLength, (numAnnotations = toInt(br.read(2))), readFieldInfoAndRecordTypeAnnotations(numAnnotations));
                    }
                    default: {
                        throw new IllegalAttributeNameException("The name of attribute \""
                                + name + "\" is not the attribute of field_info!");
                    }
                };
            }
            components.add(new RecordAttribute.RecordComponentInfo(nameIndex, descriptorIndex, attributesCount, attributes));
        }
        classFileAttributeTable.add(new RecordAttribute(attributeNameIndex, attributesLength, componentsCount, components));
    }

    private void addModuleAttributeImpl (int attributeNameIndex, int attributesLength) {
        final int moduleNameIndex = toInt(br.read(2));
        final int moduleFlags = toInt(br.read(2));
        final int moduleVersionIndex = toInt(br.read(2));
        final int requiresCount = toInt(br.read(2));
        final ModuleAttribute.Require[] requires = new ModuleAttribute.Require[requiresCount];
        for (int j = 0; j < requiresCount; j++) {
            requires[j] = new ModuleAttribute.Require(toInt(br.read(2)), toInt(br.read(2)), toInt(br.read(2)));
        }
        final int exportsCount = toInt(br.read(2));
        final ModuleAttribute.Export[] exports = new ModuleAttribute.Export[exportsCount];
        for (int j = 0; j < exportsCount; j++) {
            final int index0 = toInt(br.read(2));
            final int flag0 = toInt(br.read(2));
            final int count0 = toInt(br.read(2));
            final int[] array = new int[count0];
            for (int k = 0; k < count0; k++) {
                array[k] = toInt(br.read(2));
            }
            exports[j] = new ModuleAttribute.Export(index0, flag0, count0, array);
        }
        final int opensCount = toInt(br.read(2));
        final ModuleAttribute.Open[] opens = new ModuleAttribute.Open[opensCount];
        for (int j = 0; j < opensCount; j++) {
            final int index0 = toInt(br.read(2));
            final int flag0 = toInt(br.read(2));
            final int count0 = toInt(br.read(2));
            final int[] array = new int[count0];
            for (int k = 0; k < count0; k++) {
                array[k] = toInt(br.read(2));
            }
            opens[j] = new ModuleAttribute.Open(index0, flag0, count0, array);
        }
        final int usesCount = toInt(br.read(2));
        final int[] uses = new int[usesCount];
        for (int j = 0; j < usesCount; j++) {
            uses[j] = toInt(br.read(2));
        }
        final int providesCount = toInt(br.read(2));
        final ModuleAttribute.Provide[] provides = new ModuleAttribute.Provide[providesCount];
        for (int j = 0; j < providesCount; j++) {
            final int index0 = toInt(br.read(2));
            final int count0 = toInt(br.read(2));
            final int[] array = new int[count0];
            for (int k = 0; k < count0; k++) {
                array[k] = toInt(br.read(2));
            }
            provides[j] = new ModuleAttribute.Provide(index0, count0, array);
        }
        classFileAttributeTable.add(new ModuleAttribute(attributeNameIndex,
                attributesLength,
                moduleNameIndex,
                moduleFlags,
                moduleVersionIndex,
                requiresCount,
                requires,
                exportsCount,
                exports,
                opensCount,
                opens,
                usesCount,
                uses,
                providesCount
                , provides));
    }

    /**
     * According to jvm standard(table 4.7-C), only next attribute_info belong to field_info:
     * <pre>
     *     Synthetic_info(2u,4u=2)
     *     Deprecated_info(2u,4u=2)
     *     Signature_info(2u,4u=2,2u)
     *     RuntimeVisibleAnnotations_info(2u,4u,2u,table)
     *     RuntimeInvisibleAnnotations_info(2u,4u,2u,table)
     *     RuntimeVisibleTypeAnnotations_info(2u,4u,2u,table)
     *     RuntimeInvisibleTypeAnnotations_info(2u,4u,2u,table)
     *     ConstantValue_info(2u,4u=2,2u)
     * </pre>
     */
    protected void readFields () throws IllegalAttributeNameException, IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        fieldsCount = toInt(br.read(2));
        fieldTable = new ArrayList<>(fieldsCount);
        for (int i = 0; i < fieldsCount; i++) {
            // How to read field_info:
            // Read access flags, name index, descriptor index and attribute count.
            final int count;
            fieldTable.add(new FieldInfo(toInt(br.read(2)),
                    toInt(br.read(2)),
                    toInt(br.read(2)),
                    (count = toInt(br.read(2))), readFieldAttributeTable(count)));
        }
    }

    @SuppressWarnings({"EnhancedSwitchMigration", "ConstantConditions"})
    protected ArrayList<AttributeInfo> readFieldAttributeTable (int count)
            throws IllegalAttributeNameException, IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        final var data = new ArrayList<AttributeInfo>(count);
        for (int i = 0; i < count; i++) {
            final int nameIndex = toInt(br.read(2));
            final int attributeLength = toInt(br.read(4));
            // how to differ different attribute:
            // the name index will tell the name of attribute,
            // then we can use it to differ.
            final String name = ((ConstUtf8Info) cpInfo.get(nameIndex - 1)).getUtf8();
            // build field_info attribute_info table.
            switch (name) {
                case "ConstantValue": {
                    data.add(new ConstantValueAttribute(nameIndex, toInt(br.read(2))));
                    break;
                }
                case "Synthetic": {
                    data.add(new SyntheticAttribute(nameIndex));
                    break;
                }
                case "Signature": {
                    data.add(new SignatureAttribute(nameIndex, toInt(br.read(2))));
                    break;
                }
                case "Deprecated": {
                    data.add(new DeprecatedAttribute(nameIndex));
                    break;
                }
                case "RuntimeVisibleAnnotations": {
                    final int numAnnotation = toInt(br.read(2));
                    data.add(new RuntimeAnnotationAttribute.RuntimeVisibleAnnotationAttribute(nameIndex,
                            attributeLength, numAnnotation, readAnnotationAttribute(numAnnotation)));
                    break;
                }
                case "RuntimeInvisibleAnnotations": {
                    final int numAnnotation = toInt(br.read(2));
                    data.add(new RuntimeAnnotationAttribute.RuntimeInvisibleAnnotationAttribute(nameIndex,
                            attributeLength, numAnnotation, readAnnotationAttribute(numAnnotation)));
                    break;
                }
                case "RuntimeVisibleTypeAnnotations": {
                    final int numAnnotations;
                    data.add(new RuntimeTypeAnnotationAttribute.RuntimeVisibleTypeAnnotationsAttribute(nameIndex,
                            attributeLength, (numAnnotations = toInt(br.read(2))), readFieldInfoAndRecordTypeAnnotations(numAnnotations)));
                    break;
                }
                case "RuntimeInvisibleTypeAnnotations": {
                    final int numAnnotations;
                    data.add(new RuntimeTypeAnnotationAttribute.RuntimeInvisibleTypeAnnotationsAttribute(nameIndex,
                            attributeLength, (numAnnotations = toInt(br.read(2))), readFieldInfoAndRecordTypeAnnotations(numAnnotations)));
                    break;
                }
                default: {
                    throw new IllegalAttributeNameException("The name of attribute \""
                            + name + "\" is not the attribute of field_info!");
                }
            }
        }
        return data;
    }

    protected ArrayList<RuntimeAnnotationAttribute.Annotation> readAnnotationAttribute (int count) throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new ArrayList<>(0);
        } else {
            final ArrayList<RuntimeAnnotationAttribute.Annotation> annotations = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                final int elementPairCount;
                annotations.add(new RuntimeAnnotationAttribute.Annotation(toInt(br.read(2)),
                        (elementPairCount = toInt(br.read(2))), readElementValuePairs(elementPairCount)));
            }
            return annotations;
        }
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    protected RuntimeAnnotationAttribute.Annotation.ElementValuePairs[] readElementValuePairs (int num) throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (num == 0) {
            return new RuntimeAnnotationAttribute.Annotation.ElementValuePairs[0];
        } else {
            final RuntimeAnnotationAttribute.Annotation.ElementValuePairs[] elementValuePairs =
                    new RuntimeAnnotationAttribute.Annotation.ElementValuePairs[num];
            for (int i = 0; i < num; i++) {
                final int elementNameIndex = toInt(br.read(2));
                final byte tag = br.read();
                final RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value value;
                switch (tag) {
                    case ELEMENT_VALUE_TYPE_STRING:
                    case ELEMENT_VALUE_TYPE_SHORT:
                    case ELEMENT_VALUE_TYPE_LONG:
                    case ELEMENT_VALUE_TYPE_INT:
                    case ELEMENT_VALUE_TYPE_FLOAT:
                    case ELEMENT_VALUE_TYPE_DOUBLE:
                    case ELEMENT_VALUE_TYPE_CHAR:
                    case ELEMENT_VALUE_TYPE_BOOLEAN:
                    case ELEMENT_VALUE_TYPE_BYTE: {
                        value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ConstValue(toInt(br.read(2)));
                        break;
                    }
                    case ELEMENT_VALUE_TYPE_ENUM_CLASS: {
                        value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.EnumConstValue(toInt(br.read(2)), toInt(br.read(2)));
                        break;
                    }
                    case ELEMENT_VALUE_TYPE_CLASS: {
                        value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ClassInfoValue(toInt(br.read(2)));
                        break;
                    }
                    case ELEMENT_VALUE_TYPE_ANNOTATION_INTERFACE: {
                        final int elementPairCount;
                        value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.AnnotationValue(
                                new RuntimeAnnotationAttribute.Annotation(toInt(br.read(2)),
                                        (elementPairCount = toInt(br.read(2))),
                                        readElementValuePairs(elementPairCount)));
                        break;
                    }
                    case ELEMENT_VALUE_TYPE_ARRAY_TYPE: {
                        final int count = toInt(br.read(2));
                        value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ArrayValue(count, readElementValues(count));
                        break;
                    }
                    default: {
                        throw new IllegalRuntimeAnnotationAttributeElementValueTagByteException("Illegal element_value tag:" + tag);
                    }
                }
                final RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue elementValue =
                        new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue(tag, value);
                elementValuePairs[i] = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs(elementNameIndex, elementValue);
            }
            return elementValuePairs;
        }
    }

    protected RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue[] readElementValues (int count)
            throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        if (count == 0) {
            return new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue[0];
        }
        final RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue[] elementValues =
                new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue[count];
        for (int i = 0; i < count; i++) {
            elementValues[i] = readElementValue();
        }
        return elementValues;
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    protected RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue readElementValue () throws IllegalRuntimeAnnotationAttributeElementValueTagByteException {
        final int elementNameIndex = toInt(br.read(2));
        final byte tag = br.read();
        final RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value value;
        switch (tag) {
            case ELEMENT_VALUE_TYPE_STRING:
            case ELEMENT_VALUE_TYPE_SHORT:
            case ELEMENT_VALUE_TYPE_LONG:
            case ELEMENT_VALUE_TYPE_INT:
            case ELEMENT_VALUE_TYPE_FLOAT:
            case ELEMENT_VALUE_TYPE_DOUBLE:
            case ELEMENT_VALUE_TYPE_CHAR:
            case ELEMENT_VALUE_TYPE_BOOLEAN:
            case ELEMENT_VALUE_TYPE_BYTE: {
                value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ConstValue(toInt(br.read(2)));
                break;
            }
            case ELEMENT_VALUE_TYPE_ENUM_CLASS: {
                value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.EnumConstValue(toInt(br.read(2)), toInt(br.read(2)));
                break;
            }
            case ELEMENT_VALUE_TYPE_CLASS: {
                value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ClassInfoValue(toInt(br.read(2)));
                break;
            }
            case ELEMENT_VALUE_TYPE_ANNOTATION_INTERFACE: {
                final int elementPairCount;
                value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.AnnotationValue(
                        new RuntimeAnnotationAttribute.Annotation(toInt(br.read(2)),
                                (elementPairCount = toInt(br.read(2))),
                                readElementValuePairs(elementPairCount)));
                break;
            }
            case ELEMENT_VALUE_TYPE_ARRAY_TYPE: {
                final int count = toInt(br.read(2));
                value = new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ArrayValue(count, readElementValues(count));
                break;
            }
            default: {
                throw new IllegalRuntimeAnnotationAttributeElementValueTagByteException("");
            }
        }
        return new RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue(tag, value);
    }

    protected void readInterfaces () throws IllegalConstPoolInfoTagByteException {
        interfaceCount = toInt(br.read(2));
        interfaceIndexes = new ArrayList<>(interfaceCount);
        //read interfaces as index of CONSTANT_Class_info
        for (int i = 0; i < interfaceCount; i++) {
            final byte tag = br.read();
            if (tag == ConstPoolInfo.CONSTANT_CLASS_INFO) {
                interfaceIndexes.add(toInt(br.read(2)));
            } else {
                throw new IllegalConstPoolInfoTagByteException("");
            }
        }
    }

    protected void readClassIndexes () {
        thisClassIndex = br.read(2);
        superClassIndex = br.read(2);
    }

    protected void readAccessFlags () {
        final var flag = br.read(2);
        //System.out.println("Access Flag:" + Arrays.toString(flag));
        accessFlags = toInt(flag);
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    protected void readCpInfo () throws IllegalConstPoolInfoTagByteException {
        cpInfo = new ArrayList<>(constPoolCount);
        for (int i = 1; i <= constPoolCount; i++) {
            final byte tag = br.read();
            switch (tag) {
                case ConstPoolInfo.CONSTANT_STRING_INFO: {
                    cpInfo.add(new ConstStringInfo(br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_CLASS_INFO: {
                    cpInfo.add(new ConstClassInfo(br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_METHOD_TYPE_INFO: {
                    cpInfo.add(new ConstMethodTypeInfo(br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_UTF8_INFO: {
                    final var bits = br.read(2);
                    final var ba = calculateUtf8Length(toInt(bits));
                    cpInfo.add(new ConstUtf8Info(ba));
                    break;
                }
                case ConstPoolInfo.CONSTANT_INTEGER_INFO: {
                    cpInfo.add(new ConstIntegerInfo(br.read(4)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_LONG_INFO: {
                    cpInfo.add(new ConstLongInfo(br.read(8)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_DOUBLE_INFO: {
                    cpInfo.add(new ConstDoubleInfo(br.read(8)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_FLOAT_INFO: {
                    cpInfo.add(new ConstFloatInfo(br.read(8)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_FIELDREF_INFO: {
                    cpInfo.add(new ConstFieldrefInfo(br.read(2), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_METHODREF_INFO: {
                    cpInfo.add(new ConstMethodrefInfo(br.read(2), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_INTERFACE_METHODREF: {
                    cpInfo.add(new ConstInterfaceMethodrefInfo(br.read(2), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_METHOD_HANDLE_INFO: {
                    cpInfo.add(new ConstMethodHandleInfo(br.read(1), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_NAME_AND_TYPE_INFO: {
                    cpInfo.add(new ConstNameAndTypeInfo(br.read(2), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_DYNAMIC: {
                    cpInfo.add(new ConstDynamicInfo(br.read(2), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_INVOKE_DYNAMIC_INFO: {
                    cpInfo.add(new ConstInvokeDynamicInfo(br.read(2), br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_MODULE: {
                    cpInfo.add(new ConstModuleInfo(br.read(2)));
                    break;
                }
                case ConstPoolInfo.CONSTANT_PACKAGE: {
                    cpInfo.add(new ConstPackageInfo(br.read(2)));
                    break;
                }
                default:
                    throw new IllegalConstPoolInfoTagByteException("The tag byte of CONSTANT_info is illegal:got " + tag);
            }
        }
    }

    protected byte[] calculateUtf8Length (int binaryLength) {
        byte[] result = new byte[binaryLength];
        int sum = 0;
        for (int i = 0; i < binaryLength; i++) {
            final byte b = br.read();
            final int first = (b & 0XFF);
            result[sum] = b;
            if (first <= (byte) 0X7F) {
                ++sum;
            } else if (first >= 0XC0 && first <= 0XDF) {
                result = plus(result, br.read());
                sum += 2;
            } else if (first >= 0XE0 && first <= 0XEF) {
                result = plus(result, br.read(2));
                sum += 3;
            } else if (first >= 0XF0 && first <= 0XF7) {
                result = plus(result, br.read(3));
                sum += 4;
            } else if (first >= 0XF8 && first <= 0XFB) {
                result = plus(result, br.read(4));
                sum += 5;
            } else if (first >= 0XFC && first <= 0XFD) {
                result = plus(result, br.read(5));
                sum += 6;
            }
        }
        return result;
    }

    private static byte[] plus (byte[] b1, byte... b2) {
        final byte[] cpy = b1;
        b1 = new byte[b1.length + b2.length];
        System.arraycopy(cpy, 0, b1, 0, cpy.length);
        System.arraycopy(b2, 0, b1, cpy.length, b2.length);
        return b1;
    }

    protected void readFileInfo () throws IllegalMagicNumberException {
        byte[] cache;
        //read file magic number
        if (Arrays.equals((cache = br.read(4)), FILE_HEAD)) {
            cache = br.read(2);
            minorBytecodeVersion = toInt(cache);
            cache = br.read(2);
            majorBytecodeVersion = toInt(cache);
            cache = br.read(2);
            constPoolCount = toInt(cache) - 1;
        } else {
            throw new IllegalMagicNumberException("The binary file magic number is " +
                    Arrays.toString(cache) + ", but the correct one is 0XCAFEBABE");
        }
    }

    public int getConstPoolCount () {
        return constPoolCount;
    }

    public int getMajorBytecodeVersion () {
        return majorBytecodeVersion;
    }

    public int getMinorBytecodeVersion () {
        return minorBytecodeVersion;
    }

    public ArrayList<ConstPoolInfo> getCpInfo () {
        return cpInfo;
    }

    @Override
    public String toString () {
        return "ClassReader{" +
                "br=" + br +
                ", constPoolCount=" + constPoolCount +
                ", majorBytecodeVersion=" + majorBytecodeVersion +
                ", minorBytecodeVersion=" + minorBytecodeVersion +
                ", cpInfo=" + cpInfo +
                ", accessFlags=" + accessFlags +
                ", thisClassIndex=" + Arrays.toString(thisClassIndex) +
                ", superClassIndex=" + Arrays.toString(superClassIndex) +
                ", interfaceCount=" + interfaceCount +
                ", interfaceIndexes=" + interfaceIndexes +
                ", fieldsCount=" + fieldsCount +
                ", fieldTable=" + fieldTable +
                ", methodsCount=" + methodsCount +
                ", methodTable=" + methodTable +
                ", classFileAttributesCount=" + classFileAttributesCount +
                ", classFileAttributeTable=" + classFileAttributeTable +
                '}';
    }

    @Override
    public void close () {
        br.close();
        constPoolCount = 0;
        majorBytecodeVersion = 0;
        minorBytecodeVersion = 0;
        cpInfo.clear();
        cpInfo = null;
        accessFlags = 0;
        thisClassIndex = null;
        superClassIndex = null;
        interfaceCount = 0;
        interfaceIndexes.clear();
        interfaceIndexes = null;
        fieldsCount = 0;
        fieldTable.clear();
        fieldTable = null;
        methodsCount = 0;
        methodTable.clear();
        methodTable = null;
        classFileAttributesCount = 0;
        classFileAttributeTable.clear();
        classFileAttributeTable = null;
    }

    public static final class IllegalMagicNumberException extends DecompilerException {
        public IllegalMagicNumberException (String message) {
            super(message);
        }
    }

    public static final class IllegalConstPoolInfoTagByteException extends DecompilerException {
        public IllegalConstPoolInfoTagByteException (String message) {
            super(message);
        }
    }

    public static final class IllegalAttributeNameException extends DecompilerException {
        public IllegalAttributeNameException (String message) {
            super(message);
        }
    }

    public static final class IllegalRuntimeAnnotationAttributeElementValueTagByteException extends DecompilerException {
        public IllegalRuntimeAnnotationAttributeElementValueTagByteException (String message) {
            super(message);
        }
    }

    public enum ClassFileAccessFlag {
        PUBLIC(ACC_PUBLIC, "public"),
        FINAL(ACC_FINAL, "final"),
        SUPER(ACC_SUPER, "undefined"),
        INTERFACE(ACC_INTERFACE, "interface"),
        ABSTRACT(ACC_ABSTRACT, "abstract"),
        SYNTHETIC(ACC_SYNTHETIC, "undefined"),
        ANNOTATION(ACC_ANNOTATION, "@interface"),
        ENUM(ACC_ENUM, "enum"),
        MODULE(ACC_MODULE, "module");
        final int accessFlag;
        final String readableName;

        ClassFileAccessFlag (int accessFlag, String readableName) {
            this.accessFlag = accessFlag;
            this.readableName = readableName;
        }

        public int getAccessFlag () {
            return accessFlag;
        }

        public String readableName () {
            return readableName;
        }
    }

    public enum MethodAccessFlag {
        SYNTHETIC

    }

    public enum FieldAccessFlag {
        PUBLIC(FIELD_ACCESS_FLAG_ACC_PUBLIC, "public"),
        PRIVATE(FIELD_ACCESS_FLAG_ACC_PRIVATE, "private"),
        PROTECTED(FIELD_ACCESS_FLAG_ACC_PROTECTED, "protected"),
        STATIC(FIELD_ACCESS_FLAG_ACC_STATIC, "static"),
        FINAL(FIELD_ACCESS_FLAG_ACC_FINAL, "final"),
        VOLATILE(FIELD_ACCESS_FLAG_ACC_VOLATILE, "volatile"),
        TRANSIENT(FIELD_ACCESS_FLAG_ACC_TRANSIENT, "transient"),
        SYNTHETIC(ACC_SYNTHETIC, "undefined"),
        ENUM(FIELD_ACCESS_FLAG_ACC_ENUM, "undefined");
        final int accessFlag;
        final String readableName;

        FieldAccessFlag (int accessFlag, String readableName) {
            this.accessFlag = accessFlag;
            this.readableName = readableName;
        }

        public int getAccessFlag () {
            return accessFlag;
        }

        public String readableName () {
            return readableName;
        }
    }

    public interface Member {
        String getFullyQualifiedType ();

        String getName ();

        boolean isSynthetic ();

        boolean isDeprecated ();

        ArrayList<Annotation> getAnnotations ();

        void setAnnotations (Annotation... annotations);

        default boolean typeIsPrimitive () {
            return switch (getFullyQualifiedType()) {
                case "int":
                case "boolean":
                case "char":
                case "double":
                case "long":
                case "byte":
                case "float":
                case "short":
                    yield true;
                default:
                    yield false;
            };
        }
    }

    public static final class Annotation {
        // TODO: 2022/5/23 finish annotation, method and constructor
    }

    @SuppressWarnings("FieldMayBeFinal")
    public static final class ClassStore {
        public static final ClassStore IntClass = getNameOnlyClass("int", "java.lang.Integer");
        public static final ClassStore LongClass = getNameOnlyClass("long", "java.lang.Long");
        public static final ClassStore ShortClass = getNameOnlyClass("short", "java.lang.Short");
        public static final ClassStore floatClass = getNameOnlyClass("float", "java.lang.Float");
        public static final ClassStore doubleClass = getNameOnlyClass("double", "java.lang.Double");
        public static final ClassStore byteClass = getNameOnlyClass("byte", "java.lang.Byte");
        public static final ClassStore boolClass = getNameOnlyClass("boolean", "java.lang.Boolean");
        public static final ClassStore charClass = getNameOnlyClass("char", "java.lang.Character");
        public static final ClassStore stringClass = getNameOnlyClass("String", "java.lang.String");

        static ClassStore getNameOnlyClass (String name, String typeFullName) {
            return new ClassStore(name,
                    typeFullName,
                    null,
                    null,
                    false,
                    new int[0],
                    typeFullName,
                    null,
                    null,
                    null);
        }

        private String name;
        private String typeFullName;
        private String superClassName;
        private ArrayList<FieldAccessFlag> identifies;
        private boolean deprecated;
        private int[] annotations;
        private String signature;
        private ArrayList<Annotation> annotationTable;
        private ArrayList<FieldStore> fields;
        private ArrayList<MethodStore> methods;

        public String getName () {
            return name;
        }

        public String getFullyQualifiedType () {
            return typeFullName;
        }

        public ClassStore (String name,
                           String typeFullName,
                           String superClassName,
                           ArrayList<FieldAccessFlag> identifies,
                           boolean deprecated, int[] annotations,
                           String signature,
                           ArrayList<Annotation> annotationTable,
                           ArrayList<FieldStore> fields,
                           ArrayList<MethodStore> methods) {
            this.name = name;
            this.typeFullName = typeFullName;
            this.superClassName = superClassName;
            this.identifies = identifies;
            this.deprecated = deprecated;
            this.annotations = annotations;
            this.signature = signature;
            this.annotationTable = annotationTable;
            this.fields = fields;
            this.methods = methods;
        }

        public ArrayList<FieldStore> getFields () {
            return fields;
        }

        public void setFields (ArrayList<FieldStore> fields) {
            this.fields = fields;
        }

        public String getSuperClassName () {
            return superClassName;
        }

        public String getTypeFullName () {
            return typeFullName;
        }

        public ArrayList<MethodStore> getMethods () {
            return methods;
        }

        public void setMethods (ArrayList<MethodStore> methods) {
            this.methods = methods;
        }

        public ArrayList<Annotation> getAnnotationTable () {
            return new ArrayList<>(annotationTable);
        }

        public ArrayList<FieldAccessFlag> getIdentifies () {
            return identifies;
        }

        public void setIdentifies (ArrayList<FieldAccessFlag> identifies) {
            this.identifies = identifies;
        }

        public boolean isDeprecated () {
            return deprecated;
        }

        public void setDeprecated (boolean deprecated) {
            this.deprecated = deprecated;
        }

        public int[] getAnnotations () {
            return annotations;
        }

        public void setAnnotations (int[] annotations) {
            this.annotations = annotations;
        }

        public String getSignature () {
            return signature;
        }

        public void setSignature (String signature) {
            this.signature = signature;
        }

        @Override
        public String toString () {
            return "ClassRef{" +
                    "name='" + name + '\'' +
                    ", typeFullName='" + typeFullName + '\'' +
                    ", superClassName='" + superClassName + '\'' +
                    ", identifies=" + identifies +
                    ", deprecated=" + deprecated +
                    ", annotations=" + Arrays.toString(annotations) +
                    ", signature='" + signature + '\'' +
                    ", annotationTable=" + annotationTable +
                    ", fields=" + fields +
                    ", methods=" + methods +
                    '}';
        }
    }

    @SuppressWarnings("FieldMayBeFinal")
    public static final class MethodStore implements Member {
        private ClassStore owner;
        private String name;
        private String typeFullName;
        private ArrayList<MethodAccessFlag> identifies;
        private boolean deprecated;
        private ArrayList<Integer> annotations;
        private String signature;
        private ArrayList<Byte> code;
        private ArrayList<ClassStore> paramTypes;
        private ArrayList<ClassStore> exceptionTypes;
        private ArrayList<LocalVar> localVariableTable;
        private ArrayList<LocalVarType> localVariableTypeTable;
        private ArrayList<StackMapTableAttribute.StackMapFrame> stackFrames;

        public MethodStore (ClassStore owner,
                            String name,
                            String typeFullName,
                            ArrayList<MethodAccessFlag> identifies,
                            boolean deprecated,
                            ArrayList<Integer> annotations,
                            String signature,
                            ArrayList<Byte> code,
                            ArrayList<ClassStore> paramTypes,
                            ArrayList<ClassStore> exceptionTypes,
                            ArrayList<LocalVar> localVariableTable,
                            ArrayList<LocalVarType> localVariableTypeTable,
                            ArrayList<StackMapTableAttribute.StackMapFrame> stackFrames) {
            this.owner = owner;
            this.name = name;
            this.typeFullName = typeFullName;
            this.identifies = identifies;
            this.deprecated = deprecated;
            this.annotations = annotations;
            this.signature = signature;
            this.code = code;
            this.paramTypes = paramTypes;
            this.exceptionTypes = exceptionTypes;
            this.localVariableTable = localVariableTable;
            this.localVariableTypeTable = localVariableTypeTable;
            this.stackFrames = stackFrames;
        }

        @Override
        public String getFullyQualifiedType () {
            return typeFullName;
        }

        @Override
        public String getName () {
            return name;
        }

        @Override
        public boolean isSynthetic () {
            return identifies.contains(MethodAccessFlag.SYNTHETIC);
        }

        public ArrayList<MethodAccessFlag> getIdentifies () {
            return identifies;
        }

        public void setIdentifies (MethodAccessFlag... identifies) {
            this.identifies.clear();
            this.identifies.addAll(Arrays.asList(identifies));
        }

        @Override
        public boolean isDeprecated () {
            return deprecated;
        }

        @Override
        public ArrayList<Annotation> getAnnotations () {
            final ArrayList<Annotation> annotations = new ArrayList<>(this.annotations.size());
            for (final int index : this.annotations) {
                annotations.add(owner.annotationTable.get(index));
            }
            return annotations;
        }

        @Override
        public void setAnnotations (Annotation... annotations) {
            setAnnotations(indexesOf(this.annotations, (Object[]) annotations));
        }

        public int annotationAmount () {
            return annotations.size();
        }

        public void setAnnotations (int... indexes) {
            setAnnotations(0, indexes);
        }

        public void setAnnotations (int start, int... indexes) {
            if (start == 0) {
                annotations.clear();
                Arrays.stream(indexes).forEach(index -> annotations.add(index));
            } else {
                if (start >= annotations.size()) {
                    Arrays.stream(indexes).forEach(index -> annotations.add(index));
                } else {
                    final int size = annotations.size();
                    annotations.subList(start, size).clear();
                    Arrays.stream(indexes).forEach(index -> annotations.add(index));
                }
            }
        }

        @Override
        public String toString () {
            return "MethodRef{" +
                    "owner=" + owner +
                    ", name='" + name + '\'' +
                    ", typeFullName='" + typeFullName + '\'' +
                    ", identifies=" + identifies +
                    ", deprecated=" + deprecated +
                    ", annotations=" + annotations +
                    ", signature='" + signature + '\'' +
                    ", code=" + code +
                    ", paramTypes=" + paramTypes +
                    ", exceptionTypes=" + exceptionTypes +
                    ", localVariableTable=" + localVariableTable +
                    ", localVariableTypeTable=" + localVariableTypeTable +
                    ", stackFrames=" + stackFrames +
                    '}';
        }

        public static final class LocalVar {
            public String name;
            public final String type;
            public final int slot;
            public int start;
            public int length;

            public LocalVar (String name, String type, int slot, int start, int length) {
                this.name = name;
                this.type = type;
                this.slot = slot;
                this.start = start;
                this.length = length;
            }
        }

        public static final class LocalVarType {
            public String name;
            public final String type;
            public final int slot;
            public int start;
            public int length;

            public LocalVarType (String name, String type, int slot, int start, int length) {
                this.name = name;
                this.type = type;
                this.slot = slot;
                this.start = start;
                this.length = length;
            }
        }

        public static abstract class VerificationMethod extends VerificationTypeInfo {
            protected VerificationMethod (byte tag) {
                super(tag);
            }

            public static final class TopVarInfo extends VerificationMethod {
                public TopVarInfo () {
                    super(TOP_VARIABLE_INFO);
                }
            }

            public static final class IntVarInfo extends VerificationMethod {
                public IntVarInfo () {
                    super(INTEGER_VARIABLE_INFO);
                }
            }

            public static final class FloatVarInfo extends VerificationMethod {
                public FloatVarInfo () {
                    super(FLOAT_VARIABLE_INFO);
                }
            }

            public static final class LongVarInfo extends VerificationMethod {
                public LongVarInfo () {
                    super(LONG_VARIABLE_INFO);
                }
            }

            public static final class DoubleVarInfo extends VerificationMethod {
                public DoubleVarInfo () {
                    super(DOUBLE_VARIABLE_INFO);
                }
            }

            public static final class NullVarInfo extends VerificationMethod {
                public NullVarInfo () {
                    super(NULL_VARIABLE_INFO);
                }
            }

            public static final class UninitializedThisVar extends VerificationMethod {
                public UninitializedThisVar () {
                    super(UNINITIALIZED_THIS);
                }
            }

            public static final class ObjectVarInfo extends VerificationMethod {
                public final String className;

                public ObjectVarInfo (String className) {
                    super(OBJECT_VARIABLE_INFO);
                    this.className = className;
                }
            }

            public static final class UninitializedVarInfo extends VerificationMethod {
                public final int offset;

                public UninitializedVarInfo (int offset) {
                    super(UNINITIALIZED_VARIABLE_INFO);
                    this.offset = offset;
                }
            }
        }
    }

    @SuppressWarnings("FieldMayBeFinal")
    public static final class FieldStore implements Member {
        private ClassStore owner;
        private String name;
        private String typeFullName;
        private ArrayList<FieldAccessFlag> identifies;
        private boolean deprecated;
        private ArrayList<Integer> annotations;
        @SuppressWarnings("FieldCanBeLocal")
        private String signature;

        public FieldStore (ClassStore owner,
                           String name,
                           String typeFullName,
                           ArrayList<FieldAccessFlag> identifies,
                           boolean deprecated,
                           ArrayList<Integer> annotations,
                           String signature) {
            this.owner = owner;
            this.name = name;
            this.typeFullName = typeFullName;
            this.identifies = identifies;
            this.deprecated = deprecated;
            this.annotations = annotations;
            this.signature = signature;
        }

        @Override
        public String getFullyQualifiedType () {
            return typeFullName;
        }

        @Override
        public String getName () {
            return name;
        }

        @Override
        public boolean isSynthetic () {
            return identifies.contains(FieldAccessFlag.SYNTHETIC);
        }

        public ArrayList<FieldAccessFlag> getIdentifies () {
            return identifies;
        }

        public void setIdentifies (FieldAccessFlag... identifies) {
            this.identifies.clear();
            this.identifies.addAll(Arrays.asList(identifies));
        }

        @Override
        public boolean isDeprecated () {
            return deprecated;
        }

        @Override
        public ArrayList<Annotation> getAnnotations () {
            final ArrayList<Annotation> annotations = new ArrayList<>(this.annotations.size());
            for (final int index : this.annotations) {
                annotations.add(owner.annotationTable.get(index));
            }
            return annotations;
        }

        @Override
        public void setAnnotations (Annotation... annotations) {
            setAnnotations(indexesOf(this.annotations, (Object[]) annotations));
        }

        public int annotationAmount () {
            return annotations.size();
        }

        public void setAnnotations (int... indexes) {
            setAnnotations(0, indexes);
        }

        public void setAnnotations (int start, int... indexes) {
            if (start == 0) {
                annotations.clear();
                Arrays.stream(indexes).forEach(index -> annotations.add(index));
            } else {
                if (start >= annotations.size()) {
                    Arrays.stream(indexes).forEach(index -> annotations.add(index));
                } else {
                    final int size = annotations.size();
                    annotations.subList(start, size).clear();
                    Arrays.stream(indexes).forEach(index -> annotations.add(index));
                }
            }
        }

        @Override
        public String toString () {
            return "FieldRef{" +
                    "owner=" + owner +
                    ", name='" + name + '\'' +
                    ", typeFullName='" + typeFullName + '\'' +
                    ", identifies=" + identifies +
                    ", deprecated=" + deprecated +
                    ", annotations=" + annotations +
                    ", signature='" + signature + '\'' +
                    '}';
        }
    }

    public static int[] indexesOf (ArrayList<?> a, Object... objects) {
        final int[] arr = new int[objects.length];
        final int aSize = a.size();
        int cur = 0;
        for (int i = 0; i < aSize; i++) {
            final var val = a.get(i);
            if (val == objects[cur]) {
                arr[cur++] = i;
            }
        }
        if (cur != objects.length - 1) {
            throw new IllegalArgumentException();
        }
        return arr;
    }
}
