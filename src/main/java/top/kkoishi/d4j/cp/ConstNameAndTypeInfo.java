package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstNameAndTypeInfo extends ConstPoolInfo {
    private final byte[][] indexes = new byte[2][2];

    public ConstNameAndTypeInfo (byte[] indexName, byte[] indexDescriptor) {
        super(CONSTANT_NAME_AND_TYPE_INFO);
        indexes[0] = indexName;
        indexes[1] = indexDescriptor;
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
        return "ConstNameAndTypeInfo{" +
                "tag=" + tag +
                ", indexes=" + Arrays.deepToString(indexes) +
                '}';
    }
}
