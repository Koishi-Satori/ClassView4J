package top.kkoishi.cv4j.cp;

import top.kkoishi.cv4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstInterfaceMethodrefInfo extends ConstPoolInfo {
    private final byte[][] indexes = new byte[2][2];

    public ConstInterfaceMethodrefInfo (byte[] indexClassInfo, byte[] indexNameAndTypeInfo) {
        super(CONSTANT_INTERFACE_METHODREF);
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
        return "ConstInterfaceMethodrefInfo{" +
                "tag=" + tag +
                ", indexes=" + Arrays.deepToString(indexes) +
                '}';
    }
}
