package top.kkoishi.cv4j.attr.frames;

import top.kkoishi.cv4j.attr.StackMapTableAttribute;

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
