package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

public final class SignatureAttribute extends AttributeInfo {
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
