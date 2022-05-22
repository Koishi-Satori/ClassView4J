package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public final class LocalVariableTableAttribute extends AttributeInfo {
    private final int localVariableTableLength;
    private final LocalVariable[] localVariables;

    public LocalVariableTableAttribute (int attributeNameIndex, int attributeLength, int localVariableTableLength, LocalVariable[] localVariables) {
        super(attributeNameIndex, attributeLength);
        this.localVariableTableLength = localVariableTableLength;
        this.localVariables = localVariables;
    }

    public int getLocalVariableTableLength () {
        return localVariableTableLength;
    }

    public LocalVariable[] getLocalVariables () {
        return localVariables;
    }

    @Override
    public String toString () {
        return "LocalVariableTableInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", localVariableTableLength=" + localVariableTableLength +
                ", localVariables=" + Arrays.toString(localVariables) +
                '}';
    }

    public static final class LocalVariable {
        private final int startPc;
        private final int length;
        private final int nameIndex;
        private final int descriptorIndex;
        private final int index;

        public LocalVariable (int startPc, int length, int nameIndex, int descriptorIndex, int index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }

        public int getStartPc () {
            return startPc;
        }

        public int getLength () {
            return length;
        }

        public int getDescriptorIndex () {
            return descriptorIndex;
        }

        public int getNameIndex () {
            return nameIndex;
        }

        public int getIndex () {
            return index;
        }

        @Override
        public String toString () {
            return "LocalVariable{" +
                    "startPc=" + startPc +
                    ", length=" + length +
                    ", nameIndex=" + nameIndex +
                    ", descriptorIndex=" + descriptorIndex +
                    ", index=" + index +
                    '}';
        }
    }
}
