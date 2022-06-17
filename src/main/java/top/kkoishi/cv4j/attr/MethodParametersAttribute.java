package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class MethodParametersAttribute extends Attribute_info {
    private final byte parametersCount;
    private final Parameter[] parameters;

    public MethodParametersAttribute (int attributeNameIndex, int attributeLength, byte parametersCount, Parameter[] parameters) {
        super(attributeNameIndex, attributeLength);
        this.parametersCount = parametersCount;
        this.parameters = parameters;
    }

    public byte getParametersCount () {
        return parametersCount;
    }

    public Parameter[] getParameters () {
        return parameters;
    }

    @Override
    public String toString () {
        return "MethodParametersAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", parametersCount=" + parametersCount +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }

    public static final class Parameter {
        private final int nameIndex;
        private final int accessFlags;

        public Parameter (int nameIndex, int accessFlags) {
            this.nameIndex = nameIndex;
            this.accessFlags = accessFlags;
        }

        public int getNameIndex () {
            return nameIndex;
        }

        public int getAccessFlags () {
            return accessFlags;
        }

        @Override
        public String toString () {
            return "Parameter{" +
                    "nameIndex=" + nameIndex +
                    ", accessFlags=" + accessFlags +
                    '}';
        }
    }
}
