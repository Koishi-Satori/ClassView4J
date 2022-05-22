package top.kkoishi.d4j.attr.frames;

import top.kkoishi.d4j.attr.StackMapTableAttribute;

public final class SameFrame extends StackMapTableAttribute.StackMapFrame {
    public SameFrame (byte frameType) {
        super(frameType);
    }

    @Override
    public String toString () {
        return "SameFrame{" +
                "frameType=" + frameType +
                '}';
    }
}
