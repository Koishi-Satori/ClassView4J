package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

public final class AnnotationDefaultAttribute extends AttributeInfo {
    private final RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue elementValue;

    public AnnotationDefaultAttribute (int attributeNameIndex, int attributeLength, RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue elementValue) {
        super(attributeNameIndex, attributeLength);
        this.elementValue = elementValue;
    }

    public RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue getElementValue () {
        return elementValue;
    }

    @Override
    public String toString () {
        return "AnnotationDefaultAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", elementValue=" + elementValue +
                '}';
    }
}
