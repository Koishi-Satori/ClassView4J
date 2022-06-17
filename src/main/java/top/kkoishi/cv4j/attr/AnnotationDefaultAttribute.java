package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class AnnotationDefaultAttribute extends Attribute_info {
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
