package top.kkoishi.cv4j;

import top.kkoishi.cv4j.attr.CodeAttribute;
import top.kkoishi.cv4j.attr.LineNumberTableAttribute;
import top.kkoishi.cv4j.attr.SourceFileAttribute;
import top.kkoishi.cv4j.cp.ConstClassInfo;
import top.kkoishi.cv4j.cp.ConstFieldrefInfo;
import top.kkoishi.cv4j.cp.ConstNameAndTypeInfo;
import top.kkoishi.cv4j.cp.ConstUtf8Info;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KKoishi_
 */
public final class Test {
    public static void main (String[] args)
            throws IOException, DecompilerException {
        final ClassReader cr = new ClassReader(Files.readAllBytes(Path.of("./Test.class")));
        cr.read();
        ClassReader.report(cr);
        final var bits = Files.readAllBytes(Path.of("./EmptyClass.class"));
        final ClassReader classReader = new ClassReader(bits);
        classReader.read();
        ClassReader.report(classReader);
        System.out.println(classReader.getFullQualifiedName());
        final var rcw = new RawClassWriter();
        rcw.setMajorBytecodeVersion(new byte[]{0x00, 0x34});
        rcw.setMinorBytecodeVersion(new byte[]{0x00, 0x00});
        rcw.accessFlags.addAll(List.of(ClassReader.ClassFileAccessFlag.PUBLIC, ClassReader.ClassFileAccessFlag.SUPER, ClassReader.ClassFileAccessFlag.ABSTRACT));
        rcw.constPool.addAll(List.of(new ConstUtf8Info("EmptyClz".getBytes()),
                new ConstUtf8Info("SourceFile".getBytes()),
                new ConstUtf8Info("EmptyClz.fuckLzu".getBytes()),
                new ConstUtf8Info("java/lang/Object".getBytes()),
                new ConstClassInfo(new byte[]{0x00, 0x01}),
                new ConstClassInfo(new byte[]{0x00, 0x04}),
                new ConstUtf8Info("[[I".getBytes()),
                new ConstUtf8Info("NVIDIA_FUCK_YOU".getBytes()),
                new ConstNameAndTypeInfo(new byte[]{0x00, 0x08}, new byte[]{0x00, 0x07}),
                new ConstFieldrefInfo(new byte[]{0x00, 0x05}, new byte[]{0x00, 0x09}),
                new ConstUtf8Info("[[[[[B".getBytes()),
                new ConstUtf8Info("main".getBytes()),
                new ConstUtf8Info("([Ljava/lang/String;)D".getBytes()),
                new ConstUtf8Info("Code".getBytes()),
                new ConstUtf8Info("LineNumberTable".getBytes()),
                new ConstUtf8Info("([Ljava/lang/String;)B".getBytes()),
                new ConstUtf8Info("illegal".getBytes()),
                new ConstUtf8Info("([Ljava/lang/String;)V".getBytes())));
        rcw.thisClassIndex = 0x05;
        rcw.superClassIndex = 0x06;
        //write main method
        rcw.methods.add(new MethodInfo(ClassReader.METHOD_ACCESS_FLAG_ACC_PUBLIC +
                ClassReader.METHOD_ACCESS_FLAG_ACC_STATIC, 0x0c, 0x12,
                1, new Attribute_info[]{new CodeAttribute(0x0e,
                25, 0, 1, 1, new ArrayList<>(List.of((byte) -79)),
                0, new ArrayList<>(0), 1,
                new Attribute_info[]{new LineNumberTableAttribute(0x0f, 6, 1,
                        new LineNumberTableAttribute.LineNumber[]{new LineNumberTableAttribute.LineNumber(0x00,
                                0x03)})})}));

        // write "public illegal([Ljava/lang/String)D"
        rcw.methods.add(new MethodInfo(ClassReader.METHOD_ACCESS_FLAG_ACC_PUBLIC, 0x11, 0x0d,
                1, new Attribute_info[] {new CodeAttribute(14, 26, 2,
                2, 2, Bytecodes.parseInstructions("DCONST_0", "DRETURN"), 0,
                new ArrayList<>(0), 1, new Attribute_info[] {new LineNumberTableAttribute(0x0f,
                6, 1, new LineNumberTableAttribute.LineNumber[] {new LineNumberTableAttribute.LineNumber(
                        0x00, 0x08)})})}));

        rcw.methods.add(new MethodInfo(ClassReader.METHOD_ACCESS_FLAG_ACC_PUBLIC +
                ClassReader.METHOD_ACCESS_FLAG_ACC_ABSTRACT, 0x11, 0x10,
                0, new Attribute_info[0]));

//        rcw.fields.add(new FieldInfo(ClassReader.FIELD_ACCESS_FLAG_ACC_STATIC +
//                ClassReader.FIELD_ACCESS_FLAG_ACC_PUBLIC,
//                0x08, 0x07, 0, new ArrayList<>(0)));
//        rcw.fields.add(new FieldInfo(ClassReader.FIELD_ACCESS_FLAG_ACC_STATIC +
//                ClassReader.FIELD_ACCESS_FLAG_ACC_PRIVATE + ClassReader.FIELD_ACCESS_FLAG_ACC_TRANSIENT,
//                0x08, 0x0b, 0, new ArrayList<>(0)));
        rcw.fields.add(new FieldInfo(ClassReader.FIELD_ACCESS_FLAG_ACC_STATIC +
                ClassReader.FIELD_ACCESS_FLAG_ACC_PRIVATE + ClassReader.FIELD_ACCESS_FLAG_ACC_TRANSIENT,
                0x08, 0x0b, 0, new ArrayList<>(0)));
        rcw.attributeInfo.add(new SourceFileAttribute(2, 2, 3));
        System.out.println(Bytecodes.forName("GOTO"));
        try {
            rcw.write();
            final var bs = rcw.get();
            final var br = new ClassReader(bs);
            br.read();
            ClassReader.report(br);
            final FileOutputStream fos = new FileOutputStream("./EmptyClz.class");
            fos.write(bs);
            fos.close();
        } catch (RawClassWriter.IllegalTypeException e) {
            final URL url = new URL("https://www.stackoverflow.com/search?q=" + e.getMessage());
            final BufferedInputStream bis = new BufferedInputStream(url.openStream());
            System.out.println(new String(bis.readAllBytes()));
            bis.close();
            e.printStackTrace();
        }
    }
}
