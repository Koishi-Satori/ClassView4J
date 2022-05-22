package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public final class ModulePackagesAttribute extends AttributeInfo {
    private final int packagesCount;
    private final int[] packages;

    public ModulePackagesAttribute (int attributeNameIndex, int attributeLength, int packagesCount, int[] packages) {
        super(attributeNameIndex, attributeLength);
        this.packagesCount = packagesCount;
        this.packages = packages;
    }

    public int getPackagesCount () {
        return packagesCount;
    }

    public int[] getPackages () {
        return packages;
    }

    @Override
    public String toString () {
        return "ModulePackagesAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", packagesCount=" + packagesCount +
                ", packages=" + Arrays.toString(packages) +
                '}';
    }
}
