package top.kkoishi.cv4j;

import java.util.ArrayList;

/**
 * @author KKoishi_
 */
public final class FieldInfo {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int attributeCount;
    private final ArrayList<Attribute_info> attributes;

    public FieldInfo (int accessFlags, int nameIndex, int descriptorIndex, int attributeCount, ArrayList<Attribute_info> attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributeCount = attributeCount;
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

    public int getAttributeCount () {
        return attributeCount;
    }

    public ArrayList<Attribute_info> getAttributes () {
        return attributes;
    }

    @Override
    public String toString () {
        return "FieldInfo{" +
                "accessFlags=" + accessFlags +
                ", nameIndex=" + nameIndex +
                ", descriptorIndex=" + descriptorIndex +
                ", attributeCount=" + attributeCount +
                ", attributes=" + attributes +
                '}';
    }
}
