package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.ArrayList;

public final class InnerClassAttribute extends AttributeInfo {
    public static final class InnerClassesInfo {
        private final int innerClassInfoIndex;
        private final int outerClassInfoIndex;
        private final int innerNameIndex;
        private final int innerClassAccessFlags;

        public InnerClassesInfo (int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags) {
            this.innerClassInfoIndex = innerClassInfoIndex;
            this.outerClassInfoIndex = outerClassInfoIndex;
            this.innerNameIndex = innerNameIndex;
            this.innerClassAccessFlags = innerClassAccessFlags;
        }

        @Override
        public String toString () {
            return "InnerClassesInfo{" +
                    "innerClassInfoIndex=" + innerClassInfoIndex +
                    ", outerClassInfoIndex=" + outerClassInfoIndex +
                    ", innerNameIndex=" + innerNameIndex +
                    ", innerClassAccessFlags=" + innerClassAccessFlags +
                    '}';
        }
    }

    private final int numberOfClasses;
    private final ArrayList<InnerClassesInfo> innerClassesInfo;

    public InnerClassAttribute (int attributeNameIndex, int attributeLength, int numberOfClasses, ArrayList<InnerClassesInfo> innerClassesInfo) {
        super(attributeNameIndex, attributeLength);
        this.numberOfClasses = numberOfClasses;
        this.innerClassesInfo = innerClassesInfo;
    }

    @Override
    public String toString () {
        return "InnerClassInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numberOfClasses=" + numberOfClasses +
                ", innerClassesInfo=" + innerClassesInfo +
                '}';
    }
}
