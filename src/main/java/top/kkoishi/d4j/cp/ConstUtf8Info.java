package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.nio.charset.StandardCharsets;

public final class ConstUtf8Info extends ConstPoolInfo {
    private final byte[] bytes;

    public ConstUtf8Info (byte[] bytes) {
        super(CONSTANT_UTF8_INFO);
        this.bytes = bytes;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return bytes;
    }

    public String getUtf8 () {
        return new String(bytes);
    }

    public byte[] getBytes () {
        return bytes;
    }

    public int length () {
        return new String(bytes, StandardCharsets.UTF_8).length();
    }

    @Override
    public String toString () {
        return "ConstUtf8Info{" +
                "tag=" + tag +
                ", value=\"" + new String(bytes) +
                "\"}";
    }
}
