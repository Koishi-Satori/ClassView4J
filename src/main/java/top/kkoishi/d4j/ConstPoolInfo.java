package top.kkoishi.d4j;

/**
 * @author DELL
 */
public abstract class ConstPoolInfo {
    public static final byte CONSTANT_UTF8_INFO = 0X01;
    public static final byte CONSTANT_INTEGER_INFO = 0X03;
    public static final byte CONSTANT_FLOAT_INFO = 0X04;
    public static final byte CONSTANT_LONG_INFO = 0X05;
    public static final byte CONSTANT_DOUBLE_INFO = 0X06;
    public static final byte CONSTANT_CLASS_INFO = 0X07;
    public static final byte CONSTANT_STRING_INFO = 0X08;
    public static final byte CONSTANT_FIELDREF_INFO = 0X09;
    public static final byte CONSTANT_METHODREF_INFO = 0X0A;
    public static final byte CONSTANT_INTERFACE_METHODREF = 0X0B;
    public static final byte CONSTANT_NAME_AND_TYPE_INFO = 0X0C;
    public static final byte CONSTANT_METHOD_HANDLE_INFO = 0X0F;
    public static final byte CONSTANT_METHOD_TYPE_INFO = 0X10;
    public static final byte CONSTANT_DYNAMIC = 0X11;
    public static final byte CONSTANT_INVOKE_DYNAMIC_INFO = 0X12;
    public static final byte CONSTANT_MODULE = 0X13;
    public static final byte CONSTANT_PACKAGE = 0X14;

    protected final byte tag;

    public ConstPoolInfo (byte tag) {
        this.tag = tag;
    }

    public byte tag () {
        return tag;
    }

    public abstract int dataAmount ();

    public abstract Object data ();
}
