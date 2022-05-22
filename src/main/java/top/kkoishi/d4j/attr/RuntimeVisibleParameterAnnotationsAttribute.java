package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public final class RuntimeVisibleParameterAnnotationsAttribute extends AttributeInfo {
    private final byte numParameters;
    private final ParameterAnnotation[] parameterAnnotations;

    public RuntimeVisibleParameterAnnotationsAttribute (int attributeNameIndex, int attributeLength, byte numParameters, ParameterAnnotation[] parameterAnnotations) {
        super(attributeNameIndex, attributeLength);
        this.numParameters = numParameters;
        this.parameterAnnotations = parameterAnnotations;
    }

    public byte getNumParameters () {
        return numParameters;
    }

    public ParameterAnnotation[] getParameterAnnotations () {
        return parameterAnnotations;
    }

    @Override
    public String toString () {
        return "RuntimeVisibleParameterAnnotationsAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numParameters=" + numParameters +
                ", parameterAnnotations=" + Arrays.toString(parameterAnnotations) +
                '}';
    }

    public static final class ParameterAnnotation {
        private final int numAnnotations;
        private final RuntimeAnnotationAttribute.Annotation[] annotations;

        public ParameterAnnotation (int numAnnotations, RuntimeAnnotationAttribute.Annotation[] annotations) {
            this.numAnnotations = numAnnotations;
            this.annotations = annotations;
        }

        public int getNumAnnotations () {
            return numAnnotations;
        }

        public RuntimeAnnotationAttribute.Annotation[] getAnnotations () {
            return annotations;
        }

        @Override
        public String toString () {
            return "ParameterAnnotation{" +
                    "numAnnotations=" + numAnnotations +
                    ", annotations=" + Arrays.toString(annotations) +
                    '}';
        }
    }
}
