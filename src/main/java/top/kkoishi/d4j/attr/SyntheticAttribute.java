package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

/**
 * @author KKoishi_
 */
public final class SyntheticAttribute extends AttributeInfo {

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
