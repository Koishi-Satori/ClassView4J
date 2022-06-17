package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class NestMembersAttribute extends Attribute_info {
    private final int numberOfClasses;
    private final int[] classes;

    public NestMembersAttribute (int attributeNameIndex, int attributeLength, int numberOfClasses, int[] classes) {
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
        return "NestMembersAttribute{" +
                "numberOfClasses=" + numberOfClasses +
                ", classes=" + Arrays.toString(classes) +
                '}';
    }
}
