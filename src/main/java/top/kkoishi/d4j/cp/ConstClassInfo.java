package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

/**
 * @author DELL
 */
public final class ConstClassInfo extends ConstPoolInfo {
    private final byte[] index;

    public ConstClassInfo (byte[] index) {
        super(CONSTANT_CLASS_INFO);
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
        return "ConstClassInfo{" +
                "tag=" + tag +
                ", index=" + Arrays.toString(index) +
                '}';
    }
}
