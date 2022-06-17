package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.ArrayList;

/**
 * @author KKoishi_
 */
public final class StackMapTableAttribute extends Attribute_info {

    /**
     * <pre>
     * frame_type rule:
     * same_frame:0-63
     * same_locals_1_stack_item_frame:64-127
     * same_locals_1_stack_item_frame_extended:247
     * chop_frame:248-250
     * append_frame:252-254(And the size of locals must be frame_type - 251)
     * full_frame:255
     * </pre>
     *
     * @author KKoishi_
     */
    public static abstract class StackMapFrame {
        protected final byte frameType;

        public byte getFrameType () {
            return frameType;
        }

        public StackMapFrame (byte frameType) {
            this.frameType = frameType;
        }
    }

    private final int numberOfEntries;
    private final ArrayList<StackMapFrame> stackMapFrameEntries;

    public StackMapTableAttribute (int attributeNameIndex, int attributeLength, int numberOfEntries, ArrayList<StackMapFrame> stackMapFrameEntries) {
        super(attributeNameIndex, attributeLength);
        this.numberOfEntries = numberOfEntries;
        this.stackMapFrameEntries = stackMapFrameEntries;
    }

    public int getNumberOfEntries () {
        return numberOfEntries;
    }

    public ArrayList<StackMapFrame> getStackMapFrameEntries () {
        return stackMapFrameEntries;
    }

    @Override
    public String toString () {
        return "StackMapTableInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numberOfEntries=" + numberOfEntries +
                ", stackMapFrameEntries=" + stackMapFrameEntries +
                '}';
    }
}
