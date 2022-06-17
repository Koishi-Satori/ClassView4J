package top.kkoishi.cv4j.attr.frames.verifi;

public final class ObjectVariableInfo extends VerificationTypeInfo {
    private final int cpoolIndex;

    public ObjectVariableInfo (int cpoolIndex) {
        super(OBJECT_VARIABLE_INFO);
        this.cpoolIndex = cpoolIndex;
    }

    public int getCpoolIndex () {
        return cpoolIndex;
    }

    @Override
    public String toString () {
        return "ObjectVariableInfo{" +
                "cpoolIndex=" + cpoolIndex +
                ", tag=" + tag +
                '}';
    }
}
