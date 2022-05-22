package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstLongInfo extends ConstPoolInfo {
    private final byte[] longValue;

    public ConstLongInfo (byte[] longValue) {
        super(CONSTANT_LONG_INFO);
        this.longValue = longValue;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return longValue;
    }

    @Override
    public String toString () {
        return "ConstLongInfo{" +
                "tag=" + tag +
                ", longValue=" + Arrays.toString(longValue) +
                '}';
    }
}
