package top.kkoishi.d4j.attr.frames;

import top.kkoishi.d4j.attr.frames.verifi.VerificationTypeInfo;
import top.kkoishi.d4j.attr.StackMapTableAttribute;

public final class SameLocals1StackItemFrame extends StackMapTableAttribute.StackMapFrame {
    private final VerificationTypeInfo stack;

    public SameLocals1StackItemFrame (byte frameType, VerificationTypeInfo stack) {
        super(frameType);
        this.stack = stack;
    }

    public VerificationTypeInfo getStack () {
        return stack;
    }

    @Override
    public String toString () {
        return "SameLocals1StackItemFrame{" +
                "frameType=" + frameType +
                ", stack=" + stack +
                '}';
    }
}
