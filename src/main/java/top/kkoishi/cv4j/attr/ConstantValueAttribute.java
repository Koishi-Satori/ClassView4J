package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class ConstantValueAttribute extends Attribute_info {
    private final int constantValueIndex;

    public ConstantValueAttribute (int attributeNameIndex, int constantValueIndex) {
        super(attributeNameIndex, 2);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex () {
        return constantValueIndex;
    }

    @Override
    public String toString () {
        return "ConstantValueInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", constantValueIndex=" + constantValueIndex +
                '}';
    }
}
