package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstMethodTypeInfo extends ConstPoolInfo {
    private final byte[] indexDescriptor;

    public ConstMethodTypeInfo (byte[] indexDescriptor) {
        super(CONSTANT_METHOD_TYPE_INFO);
        this.indexDescriptor = indexDescriptor;
    }

    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return indexDescriptor;
    }

    @Override
    public String toString () {
        return "ConstMethodTypeInfo{" +
                "tag=" + tag +
                ", indexDescriptor=" + Arrays.toString(indexDescriptor) +
                '}';
    }
}
