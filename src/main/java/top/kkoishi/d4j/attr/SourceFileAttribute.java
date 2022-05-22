package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

public final class SourceFileAttribute extends AttributeInfo {
    private final int sourceFileIndex;

    public SourceFileAttribute (int attributeNameIndex, int attributeLength, int sourceFileIndex) {
        super(attributeNameIndex, attributeLength);
        this.sourceFileIndex = sourceFileIndex;
    }

    public int getSourceFileIndex () {
        return sourceFileIndex;
    }

    @Override
    public String toString () {
        return "SourceFileAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", sourceFileIndex=" + sourceFileIndex +
                '}';
    }
}
