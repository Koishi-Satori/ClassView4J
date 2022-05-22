package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

/**
 * @author DELL
 */
public final class ConstMethodHandleInfo extends ConstPoolInfo {
    private final byte[][] data = new byte[2][];

    public ConstMethodHandleInfo (byte[] referenceKind, byte[] referenceIndex) {
        super(CONSTANT_METHOD_HANDLE_INFO);
        data[0] = referenceKind;
        data[1] = referenceIndex;
    }

    @Override
    public int dataAmount () {
        return 2;
    }

    @Override
    public Object data () {
        return data;
    }

    @Override
    public String toString () {
        return "ConstMethodHandleInfo{" +
                "tag=" + tag +
                ", data=" + Arrays.deepToString(data) +
                '}';
    }
}
