package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

/**
 * @author KKoishi_
 */
public final class ConstDynamicInfo extends ConstPoolInfo {
    private final byte[][] indexes = new byte[2][2];

    public ConstDynamicInfo (byte[] indexBootstarpMethodAttr, byte[] indexNameAndType) {
        super(CONSTANT_DYNAMIC);
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
}
