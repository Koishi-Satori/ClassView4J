package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class LocalVariableTypeAttribute extends Attribute_info {
    private final int localVariableTypeTableLength;
    private final localVariableType[] localVariableTypeTable;

    public LocalVariableTypeAttribute (int attributeNameIndex, int attributeLength, int localVariableTypeTableLength, localVariableType[] localVariableTypeTable) {
        super(attributeNameIndex, attributeLength);
        this.localVariableTypeTableLength = localVariableTypeTableLength;
        this.localVariableTypeTable = localVariableTypeTable;
    }

    public int getLocalVariableTypeTableLength () {
        return localVariableTypeTableLength;
    }

    public localVariableType[] getLocalVariableTypeTable () {
        return localVariableTypeTable;
    }

    @Override
    public String toString () {
        return "LocalVariableTypeInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", localVariableTypeTableLength=" + localVariableTypeTableLength +
                ", localVariableTypeTable=" + Arrays.toString(localVariableTypeTable) +
                '}';
    }

    public static final class localVariableType {
        private final int startPc;
        private final int length;
        private final int nameIndex;
        private final int signatureIndex;
        private final int index;

        public localVariableType (int startPc, int length, int nameIndex, int signatureIndex, int index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.signatureIndex = signatureIndex;
            this.index = index;
        }

        public int getStartPc () {
            return startPc;
        }

        public int getLength () {
            return length;
        }

        public int getNameIndex () {
            return nameIndex;
        }

        public int getSignatureIndex () {
            return signatureIndex;
        }

        public int getIndex () {
            return index;
        }

        @Override
        public String toString () {
            return "localVariableType{" +
                    "startPc=" + startPc +
                    ", length=" + length +
                    ", nameIndex=" + nameIndex +
                    ", signatureIndex=" + signatureIndex +
                    ", index=" + index +
                    '}';
        }
    }
}
