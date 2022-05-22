package top.kkoishi.d4j;

public final class SignatureInfo extends AttributeInfo {
    private final int signatureIndex;

    public SignatureInfo (int attributeNameIndex, int signatureIndex) {
        super(attributeNameIndex, 2);
        this.signatureIndex = signatureIndex;
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
