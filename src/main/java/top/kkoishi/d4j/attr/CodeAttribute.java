package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.ArrayList;
import java.util.Arrays;

public final class CodeAttribute extends AttributeInfo {
    private final int maxStack;
    private final int maxLocals;
    private final int codeLength;
    private final ArrayList<Byte> code;
    private final int exceptionTableLength;
    private final ArrayList<CodeException> exceptionTable;
    private final int attributesCount;
    private final AttributeInfo[] attributes;

    public CodeAttribute (int attributeNameIndex,
                          int attributeLength,
                          int maxStack,
                          int maxLocals,
                          int codeLength,
                          ArrayList<Byte> code,
                          int exceptionTableLength,
                          ArrayList<CodeException> exceptionTable,
                          int attributesCount,
                          AttributeInfo[] attributes) {
        super(attributeNameIndex, attributeLength);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.code = code;
        this.exceptionTableLength = exceptionTableLength;
        this.exceptionTable = exceptionTable;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }

    public int getMaxStack () {
        return maxStack;
    }

    public int getMaxLocals () {
        return maxLocals;
    }

    public int getCodeLength () {
        return codeLength;
    }

    public ArrayList<Byte> getCode () {
        return code;
    }

    public int getExceptionTableLength () {
        return exceptionTableLength;
    }

    public ArrayList<CodeException> getExceptionTable () {
        return exceptionTable;
    }

    public int getAttributesCount () {
        return attributesCount;
    }

    public AttributeInfo[] getAttributes () {
        return attributes;
    }

    @Override
    public String toString () {
        return "CodeInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", maxStack=" + maxStack +
                ", maxLocals=" + maxLocals +
                ", codeLength=" + codeLength +
                ", code=" + code +
                ", exceptionTableLength=" + exceptionTableLength +
                ", exceptionTable=" + exceptionTable +
                ", attributesCount=" + attributesCount +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }

    public static final class CodeException {
        private final int startPc;
        private final int endPc;
        private final int handlerPc;
        private final int catchType;

        public CodeException (int startPc, int endPc, int handlerPc, int catchType) {
            this.startPc = startPc;
            this.endPc = endPc;
            this.handlerPc = handlerPc;
            this.catchType = catchType;
        }

        public int getStartPc () {
            return startPc;
        }

        public int getEndPc () {
            return endPc;
        }

        public int getHandlerPc () {
            return handlerPc;
        }

        public int getCatchType () {
            return catchType;
        }

        @Override
        public String toString () {
            return "CodeException{" +
                    "startPc=" + startPc +
                    ", endPc=" + endPc +
                    ", handlerPc=" + handlerPc +
                    ", catchType=" + catchType +
                    '}';
        }
    }
}
