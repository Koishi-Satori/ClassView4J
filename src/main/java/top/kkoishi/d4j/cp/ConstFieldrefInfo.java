package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstFieldrefInfo extends ConstPoolInfo {
    private final byte[][] indexes = new byte[2][2];

    public ConstFieldrefInfo (byte[] indexClassInfo, byte[] indexNameAndTypeInfo) {
        super(CONSTANT_FIELDREF_INFO);
        indexes[0] = indexClassInfo;
        indexes[1] = indexNameAndTypeInfo;
    }

    @Override
    public int dataAmount () {
        return 2;
    }

    @Override
    public Object data () {
        return indexes;
    }

    @Override
    public String toString () {
        return "ConstFieldrefInfo{" +
                "tag=" + tag +
                ", indexes=" + Arrays.deepToString(indexes) +
                '}';
    }
}
