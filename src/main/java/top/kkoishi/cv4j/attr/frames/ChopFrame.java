package top.kkoishi.cv4j.attr.frames;

import top.kkoishi.cv4j.attr.StackMapTableAttribute;

public final class ChopFrame extends StackMapTableAttribute.StackMapFrame {
    private final int offsetDelta;

    public int getOffsetDelta () {
        return offsetDelta;
    }

    public ChopFrame (byte frameType, int offsetDelta) {
        super(frameType);
        this.offsetDelta = offsetDelta;
    }

    @Override
    public String toString () {
        return "ChopFrame{" +
                "frameType=" + frameType +
                ", offsetDelta=" + offsetDelta +
                '}';
    }
}
