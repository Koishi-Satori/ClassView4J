package top.kkoishi.d4j.cp;

import top.kkoishi.d4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstPackageInfo extends ConstPoolInfo {
    private final byte[] nameIndex;

    public ConstPackageInfo (byte[] nameIndex) {
        super(CONSTANT_PACKAGE);
        this.nameIndex = nameIndex;
    }


    @Override
    public int dataAmount () {
        return 1;
    }

    @Override
    public Object data () {
        return nameIndex;
    }

    @Override
    public String toString () {
        return "ConstPackageInfo{" +
                "tag=" + tag +
                ", nameIndex=" + Arrays.toString(nameIndex) +
                '}';
    }
}
