package top.kkoishi.d4j;

class ByteBuffer {
    private byte[] bytes = new byte[0];
    private int elementCount = 0;

    void append (byte v) {
        grow(1);
        bytes[elementCount++] = v;
    }

    void appendAll (byte[] vs) {
        grow(vs.length);
        for (final byte v : vs) {
            bytes[elementCount++] = v;
        }
    }

    ByteBuffer reverse () {
        for (int i = 0; i < elementCount / 2; i++) {
            final byte cpy = bytes[i];
            bytes[i] = bytes[elementCount - i - 1];
            bytes[elementCount - i - 1] = cpy;
        }
        return this;
    }

    byte[] build () {
        elementCount = 0;
        return bytes;
    }

    protected final void grow (int inc) {
        final byte[] cpy = bytes;
        bytes = new byte[elementCount + inc];
        System.arraycopy(cpy, 0, bytes, 0, cpy.length);
    }
}
