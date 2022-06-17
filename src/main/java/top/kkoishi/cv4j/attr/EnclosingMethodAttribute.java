package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

public final class EnclosingMethodAttribute extends Attribute_info {
    /**
     * The value of the class_index item must be a valid index into
     * the constant_pool table
     */
    private final int classIndex;

    /**
     * If the current class is not immediately enclosed by a method
     * or constructor, then the value of the method_index item must be zero.
     * <br>
     * In particular, method_index must be zero if the current class
     * was immediately enclosed in source code by an instance initializer
     * , static initializer, instance variable initializer, or class
     * variable initializer. (The first two concern both local classes
     * and anonymous classes, while the last two concern anonymous
     * classes declared on the right hand side of a field assignment.)
     * <br>
     * Otherwise, the value of the method_index item must be a valid
     * index into the constant_pool table.
     */
    private final int methodIndex;

    public EnclosingMethodAttribute (int attributeNameIndex, int attributeLength, int classIndex, int methodIndex) {
        super(attributeNameIndex, attributeLength);
        this.classIndex = classIndex;
        this.methodIndex = methodIndex;
    }

    public int getClassIndex () {
        return classIndex;
    }

    public int getMethodIndex () {
        return methodIndex;
    }

    @Override
    public String toString () {
        return "EnclosingMethodAttribute{" +
                "classIndex=" + classIndex +
                ", methodIndex=" + methodIndex +
                '}';
    }
}
