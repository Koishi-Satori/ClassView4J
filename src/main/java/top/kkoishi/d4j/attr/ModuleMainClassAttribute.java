package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

public final class ModuleMainClassAttribute extends AttributeInfo {
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
