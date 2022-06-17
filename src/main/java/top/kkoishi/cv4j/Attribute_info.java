package top.kkoishi.cv4j;

/**
 * The super class of union attribute_info.
 * The constant_pool #attribute_name_index must be CONSTANT_Utf8_info
 * , and it represents the name of this instance.(e.g. The name of
 * Code_attribute must be "CodeAttribute")
 * And attribute_length is 4bytes unsigned int which equals with
 * the length of byte array describes detailed information.
 * JVM class file format:
 * <a href="https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-4.html">
 *     The class file format_jdk17</a>}
 *
 * @author KKoishi_
 * @apiNote Naming will follow the jvm class file format(jdk17)
 * @implSpec Do not try to extend this abstract class.
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class Attribute_info {
    protected final int attributeNameIndex;
    protected final int attributeLength;

    public Attribute_info (int attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    public int getAttributeNameIndex () {
        return attributeNameIndex;
    }

    public int getAttributeLength () {
        return attributeLength;
    }
}

