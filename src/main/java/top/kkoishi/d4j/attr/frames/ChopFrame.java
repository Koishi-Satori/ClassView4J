package top.kkoishi.d4j.attr.frames;

import top.kkoishi.d4j.attr.StackMapTableAttribute;

public final class ChopFrame extends StackMapTableAttribute.StackMapFrame {
    private final int offsetDelta;

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
