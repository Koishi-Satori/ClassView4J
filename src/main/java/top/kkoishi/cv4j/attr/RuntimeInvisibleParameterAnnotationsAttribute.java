package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class RuntimeInvisibleParameterAnnotationsAttribute extends Attribute_info {
    private final byte numParameters;
    private final RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[] parameterAnnotations;

    public RuntimeInvisibleParameterAnnotationsAttribute (int attributeNameIndex, int attributeLength, byte numParameters, RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[] parameterAnnotations) {
        super(attributeNameIndex, attributeLength);
        this.numParameters = numParameters;
        this.parameterAnnotations = parameterAnnotations;
    }

    public byte getNumParameters () {
        return numParameters;
    }

    public RuntimeVisibleParameterAnnotationsAttribute.ParameterAnnotation[] getParameterAnnotations () {
        return parameterAnnotations;
    }

    @Override
    public String toString () {
        return "RuntimeInvisibleParameterAnnotationsAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numParameters=" + numParameters +
                ", parameterAnnotations=" + Arrays.toString(parameterAnnotations) +
                '}';
    }
}
