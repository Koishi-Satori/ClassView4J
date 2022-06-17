package top.kkoishi.cv4j;

import java.util.Arrays;

/**
 * @author KKoishi_
 */
public final class MethodInfo {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int attributesCount;
    private final Attribute_info[] attributes;

    public MethodInfo (int accessFlags, int nameIndex, int descriptorIndex, int attributesCount, Attribute_info[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }

    public int getAccessFlags () {
        return accessFlags;
    }

    public int getNameIndex () {
        return nameIndex;
    }

    public int getDescriptorIndex () {
        return descriptorIndex;
    }

    public int getAttributesCount () {
        return attributesCount;
    }

    public Attribute_info[] getAttributes () {
        return attributes;
    }

    @Override
    public String toString () {
        return "MethodInfo{" +
                "accessFlags=" + accessFlags +
                ", nameIndex=" + nameIndex +
                ", descriptorIndex=" + descriptorIndex +
                ", attributesCount=" + attributesCount +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }
}
