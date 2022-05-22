package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstIntegerInfo extends ConstPoolInfo {
    private final byte[] digits;

    public ConstIntegerInfo (byte[] bytes) {
        super(CONSTANT_INTEGER_INFO);
        this.digits = bytes;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return digits;
    }

    @Override
    public String toString () {
        return "ConstIntegerInfo{" +
                "tag=" + tag +
                ", digits=" + Arrays.toString(digits) +
                '}';
    }
}
