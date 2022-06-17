package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class SignatureAttribute extends Attribute_info {
    private final int signatureIndex;

    public SignatureAttribute (int attributeNameIndex, int signatureIndex) {
        super(attributeNameIndex, 2);
        this.signatureIndex = signatureIndex;
    }

    public int getSignatureIndex () {
        return signatureIndex;
    }

    @Override
    public String toString () {
        return "SignatureInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", signatureIndex=" + signatureIndex +
                '}';
    }
}
