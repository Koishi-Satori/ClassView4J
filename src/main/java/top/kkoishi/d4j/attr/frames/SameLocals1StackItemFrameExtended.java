package top.kkoishi.d4j.attr.frames;

import top.kkoishi.d4j.attr.StackMapTableAttribute;
import top.kkoishi.d4j.attr.frames.verifi.VerificationTypeInfo;

public final class SameLocals1StackItemFrameExtended extends StackMapTableAttribute.StackMapFrame {
    private final int offsetDelta;
    private final VerificationTypeInfo stack;

    public SameLocals1StackItemFrameExtended (byte frameType, int offsetDelta, VerificationTypeInfo stack) {
        super(frameType);
        this.offsetDelta = offsetDelta;
        this.stack = stack;
    }

    @Override
    public String toString () {
        return "SameLocals1StackItemFrameExtended{" +
                "frameType=" + frameType +
                ", offsetDelta=" + offsetDelta +
                ", stack=" + stack +
                '}';
    }
}
