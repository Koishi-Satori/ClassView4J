package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstStringInfo extends ConstPoolInfo {
    private final byte[] index;

    public ConstStringInfo (byte[] index) {
        super(CONSTANT_STRING_INFO);
        this.index = index;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return index;
    }

    @Override
    public String toString () {
        return "ConstStringInfo{" +
                "tag=" + tag +
                ", index=" + Arrays.toString(index) +
                '}';
    }
}
