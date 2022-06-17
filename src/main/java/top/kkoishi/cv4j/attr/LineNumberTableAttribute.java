package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class LineNumberTableAttribute extends Attribute_info {
    private final int lineNumberTableLength;
    private final LineNumber[] lineNumberTable;

    public LineNumberTableAttribute (int attributeNameIndex, int attributeLength, int lineNumberTableLength, LineNumber[] lineNumberTable) {
        super(attributeNameIndex, attributeLength);
        this.lineNumberTableLength = lineNumberTableLength;
        this.lineNumberTable = lineNumberTable;
    }

    public int getLineNumberTableLength () {
        return lineNumberTableLength;
    }

    public LineNumber[] getLineNumberTable () {
        return lineNumberTable;
    }

    @Override
    public String toString () {
        return "LineNumberTableAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", lineNumberTableLength=" + lineNumberTableLength +
                ", lineNumberTable=" + Arrays.toString(lineNumberTable) +
                '}';
    }

    public static final class LineNumber {
        private final int startPc;
        private final int lineNumber;

        public LineNumber (int startPc, int lineNumber) {
            this.startPc = startPc;
            this.lineNumber = lineNumber;
        }

        public int getStartPc () {
            return startPc;
        }

        public int getLineNumber () {
            return lineNumber;
        }

        @Override
        public String toString () {
            return "LineNumberTable{" +
                    "startPc=" + startPc +
                    ", lineNumber=" + lineNumber +
                    '}';
        }
    }
}
