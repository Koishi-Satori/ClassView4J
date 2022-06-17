package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author KKoishi_
 */
public abstract class RuntimeAnnotationAttribute extends Attribute_info {
    public static final class RuntimeVisibleAnnotationAttribute extends RuntimeAnnotationAttribute {
        public RuntimeVisibleAnnotationAttribute (int attributeNameIndex, int attributeLength, int numAnnotations, ArrayList<Annotation> annotations) {
            super(attributeNameIndex, attributeLength, numAnnotations, annotations);
        }
    }

    public static final class RuntimeInvisibleAnnotationAttribute extends RuntimeAnnotationAttribute {
        public RuntimeInvisibleAnnotationAttribute (int attributeNameIndex, int attributeLength, int numAnnotations, ArrayList<Annotation> annotations) {
            super(attributeNameIndex, attributeLength, numAnnotations, annotations);
        }

        @Override
        public boolean isVisible () {
            return false;
        }
    }

    public record Annotation(int typeIndex, int numElementValuePairs, ElementValuePairs[] elementValuePairs) {

        public int getTypeIndex () {
            return typeIndex;
        }

        public int getNumElementValuePairs () {
            return numElementValuePairs;
        }

        @Override
        public int typeIndex () {
            return typeIndex;
        }

        @Override
        public int numElementValuePairs () {
            return numElementValuePairs;
        }

        @Override
        public ElementValuePairs[] elementValuePairs () {
            return elementValuePairs;
        }

        @Override
        public String toString () {
            return "Annotation{" +
                    "typeIndex=" + typeIndex +
                    ", numElementValuePairs=" + numElementValuePairs +
                    ", elementValuePairs=" + Arrays.toString(elementValuePairs) +
                    '}';
        }

        public static final class ElementValuePairs {
            private final int elementNameIndex;
            private final ElementValue elementValue;

            public ElementValuePairs (int elementNameIndex, ElementValue elementValue) {
                this.elementNameIndex = elementNameIndex;
                this.elementValue = elementValue;
            }

            public int getElementNameIndex () {
                return elementNameIndex;
            }

            public ElementValue getElementValue () {
                return elementValue;
            }

            @Override
            public String toString () {
                return "ElementValuePairs{" +
                        "elementNameIndex=" + elementNameIndex +
                        ", elementValue=" + elementValue +
                        '}';
            }

            public static final class ElementValue {
                private final byte tag;
                private final Value value;

                public ElementValue (byte tag, Value value) {
                    this.tag = tag;
                    this.value = value;
                }

                public byte getTag () {
                    return tag;
                }

                public Value getValue () {
                    return value;
                }

                @Override
                public String toString () {
                    return "ElementValue{" +
                            "tag=" + tag +
                            ", value=" + value +
                            '}';
                }

                public static abstract class Value {
                    public static final class ConstValue extends Value {
                        private final int constValueIndex;

                        public ConstValue (int constValueIndex) {
                            this.constValueIndex = constValueIndex;
                        }

                        public int getConstValueIndex () {
                            return constValueIndex;
                        }

                        @Override
                        public String toString () {
                            return "ConstValue{" +
                                    "constValueIndex=" + constValueIndex +
                                    '}';
                        }
                    }

                    public static final class EnumConstValue extends Value {
                        private final int typeNameIndex;
                        private final int constNameIndex;

                        public EnumConstValue (int typeNameIndex, int constNameIndex) {
                            this.typeNameIndex = typeNameIndex;
                            this.constNameIndex = constNameIndex;
                        }

                        public int getTypeNameIndex () {
                            return typeNameIndex;
                        }

                        public int getConstNameIndex () {
                            return constNameIndex;
                        }

                        @Override
                        public String toString () {
                            return "EnumConstValue{" +
                                    "typeNameIndex=" + typeNameIndex +
                                    ", constNameIndex=" + constNameIndex +
                                    '}';
                        }
                    }

                    public static final class ClassInfoValue extends Value {
                        private final int classInfoIndex;

                        public ClassInfoValue (int classInfoIndex) {
                            this.classInfoIndex = classInfoIndex;
                        }

                        public int getClassInfoIndex () {
                            return classInfoIndex;
                        }

                        @Override
                        public String toString () {
                            return "ClassInfoValue{" +
                                    "classInfoIndex=" + classInfoIndex +
                                    '}';
                        }
                    }

                    public static final class AnnotationValue extends Value {
                        private final Annotation annotationValue;

                        public AnnotationValue (Annotation annotationValue) {
                            this.annotationValue = annotationValue;
                        }

                        public Annotation getAnnotationValue () {
                            return annotationValue;
                        }

                        @Override
                        public String toString () {
                            return "AnnotationValue{" +
                                    "annotationValue=" + annotationValue +
                                    '}';
                        }
                    }

                    public static final class ArrayValue extends Value {
                        private final int numValue;
                        private final ElementValue[] values;

                        public ArrayValue (int numValue, ElementValue[] values) {
                            this.numValue = numValue;
                            this.values = values;
                        }

                        public int getNumValue () {
                            return numValue;
                        }

                        public ElementValue[] getValues () {
                            return values;
                        }

                        @Override
                        public String toString () {
                            return "ArrayValue{" +
                                    "numValue=" + numValue +
                                    ", values=" + Arrays.toString(values) +
                                    '}';
                        }
                    }
                }
            }
        }
    }

    private final int numAnnotations;
    private final ArrayList<Annotation> annotations;

    public RuntimeAnnotationAttribute (int attributeNameIndex, int attributeLength, int numAnnotations, ArrayList<Annotation> annotations) {
        super(attributeNameIndex, attributeLength);
        this.numAnnotations = numAnnotations;
        this.annotations = annotations;
    }

    public int getNumAnnotations () {
        return numAnnotations;
    }

    public ArrayList<Annotation> getAnnotations () {
        return annotations;
    }

    public boolean isVisible () {
        return true;
    }

    @Override
    public String toString () {
        return "RuntimeAnnotationInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numAnnotations=" + numAnnotations +
                ", annotations=" + annotations +
                '}';
    }
}
