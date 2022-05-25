package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

public final class ConstantValueAttribute extends AttributeInfo {
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
