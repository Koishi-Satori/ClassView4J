package top.kkoishi.d4j.attr.frames;

import top.kkoishi.d4j.attr.StackMapTableAttribute;

public final class SameFrameExtended extends StackMapTableAttribute.StackMapFrame {
    private final int offsetDelta;

    public SameFrameExtended (byte frameType, int offsetDelta) {
        super(frameType);
        this.offsetDelta = offsetDelta;
    }

    public int getOffsetDelta () {
        return offsetDelta;
    }

    @Override
    public String toString () {
        return "SameFrameExtended{" +
                "frameType=" + frameType +
                ", offsetDelta=" + offsetDelta +
                '}';
    }
}
