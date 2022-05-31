import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class Test {
    @TestAnno
    public static StringBuilder buf = new StringBuilder();
    public static final Object LOCK = new Object();

    public double d() {
        return 0.0D;
    }

    public static StringBuilder getBuf () {
        return buf;
    }

    public static void setBuf (StringBuilder buf) {
        Test.buf = buf;
    }

    public final void mmm () {
        for (int i = 0; i < 114514; ++i) {
            System.out.println(Test.buf);
        }
    }

    class ByteReader {
        int pos = 0;
        final byte[] data;

        public ByteReader (byte[] data) {
            this.data = data;
        }

        public byte read () {
            //System.err.println(data[pos]);
            return data[pos++];
        }

        public boolean isEnded () {
            return pos >= data.length;
        }

        public byte[] read (int length) {
            final byte[] cpy = new byte[length];
            System.arraycopy(data, pos, cpy, 0, length);
            pos += length;
            return cpy;
        }

        void rest () {
            for (int i = pos; i < data.length; i++) {
                System.out.print(data[i] + "\t");
            }
            System.out.println("\n" + pos);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.TYPE })
    @interface TestAnno {
    }
}