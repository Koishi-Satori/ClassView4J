package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class ModuleMainClassAttribute extends Attribute_info {
    private final int mainClassIndex;

    public ModuleMainClassAttribute (int attributeNameIndex, int attributeLength, int mainClassIndex) {
        super(attributeNameIndex, attributeLength);
        this.mainClassIndex = mainClassIndex;
    }

    public int getMainClassIndex () {
        return mainClassIndex;
    }

    @Override
    public String toString () {
        return "ModuleMainClassAttribute{" +
                "mainClassIndex=" + mainClassIndex +
                '}';
    }
}
