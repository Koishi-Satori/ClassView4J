package top.kkoishi.d4j.attr.frames.verifi;

public final class UninitializedVariableInfo extends VerificationTypeInfo {
    private final int offset;
    public UninitializedVariableInfo (int offset) {
        super(UNINITIALIZED_VARIABLE_INFO);
        this.offset = offset;
    }

    public int getOffset () {
        return offset;
    }

    @Override
    public String toString () {
        return "UninitializedVariableInfo{" +
                "offset=" + offset +
                '}';
    }
}
