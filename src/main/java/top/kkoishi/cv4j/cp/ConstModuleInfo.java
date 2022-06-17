package top.kkoishi.cv4j.cp;

import top.kkoishi.cv4j.ConstPoolInfo;

import java.util.Arrays;

public final class ConstModuleInfo extends ConstPoolInfo {
    private final byte[] nameIndex;

    public ConstModuleInfo (byte[] nameIndex) {
        super(CONSTANT_MODULE);
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
        return "ConstModuleInfo{" +
                "nameIndex=" + Arrays.toString(nameIndex) +
                '}';
    }
}
