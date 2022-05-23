package top.kkoishi.d4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * @author KKoishi_
 */
public final class Test {
    public static void main (String[] args)
            throws IOException, DecompilerException {
        final ClassReader cr = new ClassReader(Files.readAllBytes(Path.of("./ConstPoolInfo.class")));
        cr.read();
        ClassReader.report(cr);
        final var bits = Files.readAllBytes(Path.of("./Test.class"));
        System.out.println(Arrays.toString(bits));
        final ClassReader classReader = new ClassReader(bits);
        classReader.read();
        ClassReader.report(classReader);
    }
}
