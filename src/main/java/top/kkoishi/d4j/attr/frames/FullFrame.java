package top.kkoishi.d4j.attr.frames;

import top.kkoishi.d4j.attr.StackMapTableAttribute;
import top.kkoishi.d4j.attr.frames.verifi.VerificationTypeInfo;

import java.util.ArrayList;

public final class FullFrame extends StackMapTableAttribute.StackMapFrame {
    private final int offsetDelta;
    private final int numberOfLocals;
    private final ArrayList<VerificationTypeInfo> locals;
    private final int numberOfStackItems;
    private final ArrayList<VerificationTypeInfo> stack;

    public FullFrame (byte frameType, int offsetDelta, int numberOfLocals, ArrayList<VerificationTypeInfo> locals,
                      int numberOfStackItems, ArrayList<VerificationTypeInfo> stack) {
        super(frameType);
        this.offsetDelta = offsetDelta;
        this.numberOfLocals = numberOfLocals;
        this.locals = locals;
        this.numberOfStackItems = numberOfStackItems;
        this.stack = stack;
    }

    @Override
    public String toString () {
        return "FullFrame{" +
                "frameType=" + frameType +
                ", offsetDelta=" + offsetDelta +
                ", numberOfLocals=" + numberOfLocals +
                ", locals=" + locals +
                ", numberOfStackItems=" + numberOfStackItems +
                ", stack=" + stack +
                '}';
    }
}
