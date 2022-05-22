package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.ArrayList;
import java.util.Arrays;

public final class RecordAttribute extends AttributeInfo {
    private final int componentCount;
    private final ArrayList<RecordComponentInfo> recordComponents;

    public RecordAttribute (int attributeNameIndex, int attributeLength, int componentCount, ArrayList<RecordComponentInfo> recordComponents) {
        super(attributeNameIndex, attributeLength);
        this.componentCount = componentCount;
        this.recordComponents = recordComponents;
    }

    public int getComponentCount () {
        return componentCount;
    }

    public ArrayList<RecordComponentInfo> getRecordComponents () {
        return recordComponents;
    }

    @Override
    public String toString () {
        return "RecordAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", componentCount=" + componentCount +
                ", recordComponents=" + recordComponents +
                '}';
    }

    public static final class RecordComponentInfo {
        private final int recordComponentNameIndex;
        private final int descriptorIndex;
        private final int attributesCount;
        private final AttributeInfo[] attributeInfos;

        public RecordComponentInfo (int recordComponentNameIndex, int descriptorIndex, int attributesCount, AttributeInfo[] attributeInfos) {
            this.recordComponentNameIndex = recordComponentNameIndex;
            this.descriptorIndex = descriptorIndex;
            this.attributesCount = attributesCount;
            this.attributeInfos = attributeInfos;
        }

        public int getRecordComponentNameIndex () {
            return recordComponentNameIndex;
        }

        public int getDescriptorIndex () {
            return descriptorIndex;
        }

        public int getAttributesCount () {
            return attributesCount;
        }

        public AttributeInfo[] getAttributeInfos () {
            return attributeInfos;
        }

        @Override
        public String toString () {
            return "RecordComponentInfo{" +
                    "recordComponentNameIndex=" + recordComponentNameIndex +
                    ", descriptorIndex=" + descriptorIndex +
                    ", attributesCount=" + attributesCount +
                    ", attributeInfos=" + Arrays.toString(attributeInfos) +
                    '}';
        }
    }
}
