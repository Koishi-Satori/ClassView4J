package top.kkoishi.d4j;

import java.util.ArrayList;

public class ClassDecoder {
    protected int majorBytecodeVersion;
    protected int minorBytecodeVersion;
    protected ArrayList<ConstPoolInfo> cpInfo;
    protected int accessFlags;
    protected int thisClassIndex;
    protected int superClassIndex;
    protected ArrayList<Integer> interfaceIndexes;
    protected ArrayList<FieldInfo> fieldTable;
    protected ArrayList<MethodInfo> methodTable;
    protected ArrayList<AttributeInfo> classFileAttributeTable;

    protected ClassDecoder (int majorBytecodeVersion,
                            int minorBytecodeVersion, ArrayList<ConstPoolInfo> cpInfo,
                            int accessFlags,
                            int thisClassIndex,
                            int superClassIndex,
                            ArrayList<Integer> interfaceIndexes,
                            ArrayList<FieldInfo> fieldTable,
                            ArrayList<MethodInfo> methodTable,
                            ArrayList<AttributeInfo> classFileAttributeTable) {
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

    public static class ClassResult {
        protected ArrayList<ClassReader.ClassFileAccessFlag> fileAccessFlags;
        protected final int[] bytecodeVersion = new int[]{0x0000, 0x0052};
        protected String resourceFileName;
        protected ArrayList<String> innerClasses;
        protected ClassReader.ClassRef clz;


    }
}
