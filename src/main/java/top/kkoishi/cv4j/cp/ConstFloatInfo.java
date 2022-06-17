package top.kkoishi.cv4j.cp;

import top.kkoishi.cv4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstFloatInfo extends ConstPoolInfo {
    private final byte[] floatValue;

    public ConstFloatInfo (byte[] floatValue) {
        super(CONSTANT_FLOAT_INFO);
        this.floatValue = floatValue;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return floatValue;
    }

    @Override
    public String toString () {
        return "ConstFloatInfo{" +
                "tag=" + tag +
                ", floatValue=" + Arrays.toString(floatValue) +
                '}';
    }
}
