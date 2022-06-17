package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class NestHostAttribute extends Attribute_info {
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
