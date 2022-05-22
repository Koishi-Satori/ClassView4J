package top.kkoishi.d4j;

    public  abstract class AttributeInfo {
        protected final int attributeNameIndex;
        protected final int attributeLength;

        public AttributeInfo (int attributeNameIndex, int attributeLength) {
            this.attributeNameIndex = attributeNameIndex;
            this.attributeLength = attributeLength;
        }
    }

