package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public final class PermittedSubclassesAttribute extends AttributeInfo {
    private final int numberOfClasses;
    private final int[] classes;

    public PermittedSubclassesAttribute (int attributeNameIndex, int attributeLength, int numberOfClasses, int[] classes) {
        super(attributeNameIndex, attributeLength);
        this.numberOfClasses = numberOfClasses;
        this.classes = classes;
    }

    public int getNumberOfClasses () {
        return numberOfClasses;
    }

    public int[] getClasses () {
        return classes;
    }

    @Override
    public String toString () {
        return "PermittedSubclassesAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numberOfClasses=" + numberOfClasses +
                ", classes=" + Arrays.toString(classes) +
                '}';
    }
}
