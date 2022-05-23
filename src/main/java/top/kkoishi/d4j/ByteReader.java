package top.kkoishi.d4j;

import java.io.Closeable;
import java.util.Arrays;

class ByteReader implements Closeable {
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

    @Override
    public void close () {
        pos = 0;
        Arrays.fill(data, (byte) 0);
    }
}
