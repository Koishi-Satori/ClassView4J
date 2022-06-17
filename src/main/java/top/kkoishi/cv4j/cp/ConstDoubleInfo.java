package top.kkoishi.cv4j.cp;

import top.kkoishi.cv4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstDoubleInfo extends ConstPoolInfo {
    private final byte[] doubleValue;

    public ConstDoubleInfo (byte[] doubleValue) {
        super(CONSTANT_DOUBLE_INFO);
        this.doubleValue = doubleValue;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return doubleValue;
    }

    @Override
    public String toString () {
        return "ConstDoubleInfo{" +
                "tag=" + tag +
                ", doubleValue=" + Arrays.toString(doubleValue) +
                '}';
    }
}
