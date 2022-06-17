package top.kkoishi.cv4j.cp;

import top.kkoishi.cv4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstInvokeDynamicInfo extends ConstPoolInfo {
    private final byte[][] indexes = new byte[2][2];

    public ConstInvokeDynamicInfo (byte[] indexBootstarpMethodAttr, byte[] indexNameAndType) {
        super(CONSTANT_INVOKE_DYNAMIC_INFO);
        indexes[0] = indexBootstarpMethodAttr;
        indexes[1] = indexNameAndType;
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
        return "ConstInvokeDynamicInfo{" +
                "tag=" + tag +
                ", indexes=" + Arrays.deepToString(indexes) +
                '}';
    }
}
