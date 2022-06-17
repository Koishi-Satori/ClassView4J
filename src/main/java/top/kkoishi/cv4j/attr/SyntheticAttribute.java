package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

/**
 * @author KKoishi_
 */
public final class SyntheticAttribute extends Attribute_info {

    public SyntheticAttribute (int attributeNameIndex) {
        super(attributeNameIndex, 2);
    }

    @Override
    public String toString () {
        return "SyntheticInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                '}';
    }
}
