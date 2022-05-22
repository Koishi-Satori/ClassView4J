package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

public final class NestHostAttribute extends AttributeInfo {
    private final int hotClassIndex;

    public NestHostAttribute (int attributeNameIndex, int attributeLength, int hotClassIndex) {
        super(attributeNameIndex, attributeLength);
        this.hotClassIndex = hotClassIndex;
    }

    public int getHotClassIndex () {
        return hotClassIndex;
    }

    @Override
    public String toString () {
        return "NestHostAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", hotClassIndex=" + hotClassIndex +
                '}';
    }
}
