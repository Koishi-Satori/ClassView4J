package top.kkoishi.cv4j.attr.frames;

import top.kkoishi.cv4j.attr.StackMapTableAttribute;
import top.kkoishi.cv4j.attr.frames.verifi.VerificationTypeInfo;

import java.util.ArrayList;

public final class AppendFrame extends StackMapTableAttribute.StackMapFrame {
    private final int offsetDelta;
    private final ArrayList<VerificationTypeInfo> locals;

    public AppendFrame (byte frameType, int offsetDelta, ArrayList<VerificationTypeInfo> locals) {
        super(frameType);
        this.offsetDelta = offsetDelta;
        this.locals = locals;
    }

    public int getOffsetDelta () {
        return offsetDelta;
    }

    public ArrayList<VerificationTypeInfo> getLocals () {
        return locals;
    }

    @Override
    public String toString () {
        return "AppendFrames{" +
                "offsetDelta=" + offsetDelta +
                ", locals=" + locals +
                ", frameType=" + frameType +
                '}';
    }
}
