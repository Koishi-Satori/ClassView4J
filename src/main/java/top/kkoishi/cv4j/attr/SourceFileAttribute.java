package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class SourceFileAttribute extends Attribute_info {
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
