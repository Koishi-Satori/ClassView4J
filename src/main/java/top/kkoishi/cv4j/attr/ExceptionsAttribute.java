package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class ExceptionsAttribute extends Attribute_info {
    public static final class ExceptionIndexTable {
        private final int exceptionClassIndex;

        public ExceptionIndexTable (int exceptionClassIndex) {
            this.exceptionClassIndex = exceptionClassIndex;
        }

        public int getExceptionClassIndex () {
            return exceptionClassIndex;
        }

        @Override
        public String toString () {
            return "ExceptionIndexTable{" +
                    "exceptionClassIndex=" + exceptionClassIndex +
                    '}';
        }
    }
    private final int numberOfExceptions;
    private final ExceptionIndexTable[] exceptionIndexTable;

    public ExceptionsAttribute (int attributeNameIndex, int attributeLength, int numberOfExceptions, ExceptionIndexTable[] exceptionIndexTable) {
        super(attributeNameIndex, attributeLength);
        this.numberOfExceptions = numberOfExceptions;
        this.exceptionIndexTable = exceptionIndexTable;
    }

    public int getNumberOfExceptions () {
        return numberOfExceptions;
    }

    public ExceptionIndexTable[] getExceptionIndexTable () {
        return exceptionIndexTable;
    }

    @Override
    public String toString () {
        return "ExceptionsInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numberOfExceptions=" + numberOfExceptions +
                ", exceptionIndexTable=" + Arrays.toString(exceptionIndexTable) +
                '}';
    }
}
