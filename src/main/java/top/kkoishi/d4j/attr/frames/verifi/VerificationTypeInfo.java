package top.kkoishi.d4j.attr.frames.verifi;

/**
 * @author KKoishi_
 */
public abstract class VerificationTypeInfo {
    public static final byte TOP_VARIABLE_INFO = 0X00;
    public static final byte INTEGER_VARIABLE_INFO = 0X01;
    public static final byte FLOAT_VARIABLE_INFO = 0X02;
    public static final byte LONG_VARIABLE_INFO = 0X04;
    public static final byte DOUBLE_VARIABLE_INFO = 0X03;
    public static final byte NULL_VARIABLE_INFO = 0X05;
    public static final byte UNINITIALIZED_THIS = 0X06;
    public static final byte OBJECT_VARIABLE_INFO = 0X07;
    public static final byte UNINITIALIZED_VARIABLE_INFO = 0X08;

    protected final byte tag;

    public byte tag () {
        return tag;
    }

    public VerificationTypeInfo (byte tag) {
        this.tag = tag;
    }

    @Override
    public String toString () {
        return "VerificationTypeInfo{" +
                "tag=" + tag +
                '}';
    }
}
