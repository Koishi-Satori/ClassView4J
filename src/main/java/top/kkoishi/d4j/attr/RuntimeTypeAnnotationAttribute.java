package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public abstract class RuntimeTypeAnnotationAttribute extends AttributeInfo {
    public static final class RuntimeVisibleTypeAnnotationsAttribute extends RuntimeTypeAnnotationAttribute {
        public RuntimeVisibleTypeAnnotationsAttribute (int attributeNameIndex, int attributeLength, int numAnnotations, TypeAnnotation[] typeAnnotations) {
            super(attributeNameIndex, attributeLength, numAnnotations, typeAnnotations);
        }
    }

    public static final class RuntimeInvisibleTypeAnnotationsAttribute extends RuntimeTypeAnnotationAttribute {
        public RuntimeInvisibleTypeAnnotationsAttribute (int attributeNameIndex, int attributeLength, int numAnnotations, TypeAnnotation[] typeAnnotations) {
            super(attributeNameIndex, attributeLength, numAnnotations, typeAnnotations);
        }

        @Override
        public boolean isVisible () {
            return false;
        }
    }

    private final int numAnnotations;
    private final TypeAnnotation[] typeAnnotations;

    public RuntimeTypeAnnotationAttribute (int attributeNameIndex, int attributeLength, int numAnnotations, TypeAnnotation[] typeAnnotations) {
        super(attributeNameIndex, attributeLength);
        this.numAnnotations = numAnnotations;
        this.typeAnnotations = typeAnnotations;
    }

    public int getNumAnnotations () {
        return numAnnotations;
    }

    public TypeAnnotation[] getTypeAnnotations () {
        return typeAnnotations;
    }

    public boolean isVisible () {
        return true;
    }

    @Override
    public String toString () {
        return "RuntimeTypeAnnotationInfo{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numAnnotations=" + numAnnotations +
                ", typeAnnotations=" + Arrays.toString(typeAnnotations) +
                '}';
    }

    public static final class TypeAnnotation {
        /**
         * Type parameter declaration of generic class or interface.
         *
         * @apiNote type_parameter_target
         */
        public static final byte TYPE_PARAMETER_CLASS_INTERFACE = 0x00;

        /**
         * Type parameter declaration of generic method or constructor
         *
         * @apiNote type_parameter_target
         */
        public static final byte TYPE_PARAMETER_METHOD_CONSTRUCTOR = 0x01;

        /**
         * type in extends or implements clause of class declaration
         * (including the direct superclass or direct superinterface
         * of an anonymous class declaration), or in extends clause
         * of interface declaration
         *
         * @apiNote supertype_target
         */
        public static final byte TYPE_EXTEND_IMPLEMENTS = 0X10;

        /**
         * type in bound of type parameter declaration of generic class
         * or interface
         *
         * @apiNote type_parameter_bound_target
         */
        public static final byte TYPE_BOUND_CLASS_INTERFACE = 0x11;

        /**
         * type in bound of type parameter declaration of generic method
         * or constructor
         *
         * @apiNote type_parameter_bound_target
         */
        public static final byte TYPE_BOUND_METHOD_CONSTRUCTOR = 0X12;

        /**
         * type in field or record component declaration
         *
         * @apiNote empty_target
         */
        public static final byte TYPE_DECLARE_FIELD_RECORD = 0X13;

        /**
         * return type of method, or type of newly constructed object
         *
         * @apiNote empty_target
         */
        public static final byte TYPE_RETURN_METHOD_CONSTRUCTED_OBJECT = 0X14;

        /**
         * receiver type of method or constructor
         *
         * @apiNote empty_target
         */
        public static final byte TYPE_RECEIVER_METHOD_CONSTRUCTOR = 0X15;

        /**
         * type in formal parameter declaration of method, constructor, or lambda expression
         *
         * @apiNote formal_parameter_target
         */
        public static final byte TYPE_FORMAL_PARAMETER = 0X16;

        /**
         * type in throws clause of method or constructor
         *
         * @apiNote throws_target
         */
        public static final byte TYPE_THROW_CLAUSE = 0X17;

        /**
         * type in local variable declaration
         *
         * @apiNote localvar_target
         */
        public static final byte TYPE_DECLARE_LOCAL_VAR = 0X40;

        /**
         * type in resource variable declaration
         *
         * @apiNote localvar_target
         */
        public static final byte TYPE_DECLARE_RESOURCE_VAR = 0X41;

        /**
         * type in exception parameter declaration
         *
         * @apiNote catch_target
         */
        public static final byte TYPE_EXCEPTION_PARAMETER = 0X42;

        /**
         * type in instanceof expression
         *
         * @apiNote offset_target
         */
        public static final byte TYPE_EXPRESSION_INSTANCEOF = 0X43;

        /**
         * type in new expression
         *
         * @apiNote offset_target
         */
        public static final byte TYPE_EXPRESSION_NEW = 0X44;

        /**
         * type in method reference expression using ::new
         *
         * @apiNote offset_target
         */
        public static final byte TYPE_METHOD_REFERENCE_CONSTRUCTOR = 0X45;

        /**
         * type in method reference expression using ::Identifier
         *
         * @apiNote offset_target
         */
        public static final byte TYPE_METHOD_REFERENCE_METHOD = 0X46;

        /**
         * type in cast expression
         *
         * @apiNote type_argument_target
         */
        public static final byte TYPE_EXPRESSION_CAST = 0X47;

        /**
         * type argument for generic constructor in new expression or
         * explicit constructor invocation statement
         *
         * @apiNote type_argument_target
         */
        public static final byte TYPE_ARGUMENT_EXPRESSION_CONSTRUCTOR = 0X48;

        /**
         * type argument for generic method in method invocation expression
         *
         * @apiNote type_argument_target
         */
        public static final byte TYPE_ARGUMENT_EXPRESSION_METHOD = 0X49;

        /**
         * type argument for generic constructor in method reference
         * expression using ::new
         *
         * @apiNote type_argument_target
         */
        public static final byte TYPE_ARGUMENT_REFERENCE_CONSTRUCTOR = 0X4A;

        /**
         * type argument for generic method in method reference
         * expression using ::Identifier
         *
         * @apiNote type_argument_target
         */
        public static final byte TYPE_ARGUMENT_REFERENCE_METHOD = 0X4B;

        /*-----------------------------------------Fields---------------------------------------------------*/

        /**
         * Differ the target_info to build.
         * It must be one of byte const defined in TypeAnnotation class.
         * The value of the target_type item denotes the kind of target
         * on which the annotation appears.
         */
        private final byte targetType;

        /**
         * The value of the target_info item denotes precisely which type
         * in a declaration or expression is annotated.
         *
         * @see TargetInfo
         */
        private final TargetInfo targetInfo;

        /**
         * The value of the target_path item denotes precisely which part
         * of the type indicated by target_info is annotated.
         *
         * @see TargetPath
         */
        private final TargetPath targetPath;
        private final int typeIndex;
        private final int numberElementValuePairs;
        private final RuntimeAnnotationAttribute.Annotation.ElementValuePairs[] elementValuePairs;

        /**
         * Construct a type_annotation instance.
         *
         * @param targetType target_type
         * @param targetInfo target_info
         * @param targetPath target_path
         * @param typeIndex target_index
         * @param numberElementValuePairs number_element_value_pairs
         * @param elementValuePairs element_value_pair array, its length
         *                         must be <code>number_element_value_pairs</code>
         */
        public TypeAnnotation (byte targetType, TargetInfo targetInfo, TargetPath targetPath, int typeIndex, int numberElementValuePairs, RuntimeAnnotationAttribute.Annotation.ElementValuePairs[] elementValuePairs) {
            this.targetType = targetType;
            this.targetInfo = targetInfo;
            this.targetPath = targetPath;
            this.typeIndex = typeIndex;
            this.numberElementValuePairs = numberElementValuePairs;
            this.elementValuePairs = elementValuePairs;
        }

        public byte getTargetType () {
            return targetType;
        }

        public TargetInfo getTargetInfo () {
            return targetInfo;
        }

        public TargetPath getTargetPath () {
            return targetPath;
        }

        public int getTypeIndex () {
            return typeIndex;
        }

        public int getNumberElementValuePairs () {
            return numberElementValuePairs;
        }

        public RuntimeAnnotationAttribute.Annotation.ElementValuePairs[] getElementValuePairs () {
            return elementValuePairs;
        }

        @Override
        public String toString () {
            return "TypeAnnotation{" +
                    "targetType=" + targetType +
                    ", targetInfo=" + targetInfo +
                    ", targetPath=" + targetPath +
                    ", typeIndex=" + typeIndex +
                    ", numberElementValuePairs=" + numberElementValuePairs +
                    ", elementValuePairs=" + Arrays.toString(elementValuePairs) +
                    '}';
        }

        public static abstract class TargetInfo {
            public static final class TypeParameterTarget extends TargetInfo {
                private final byte index;

                public TypeParameterTarget (byte index) {
                    this.index = index;
                }

                public byte getIndex () {
                    return index;
                }
            }

            public static final class SuperTypeTarget extends TargetInfo {
                private final int superTypeIndex;

                public SuperTypeTarget (int superTypeIndex) {
                    this.superTypeIndex = superTypeIndex;
                }

                public int getSuperTypeIndex () {
                    return superTypeIndex;
                }
            }

            public static final class TypeParameterBoundTarget extends TargetInfo {
                private final byte typeIndex;
                private final byte boundIndex;

                public TypeParameterBoundTarget (byte typeIndex, byte boundIndex) {
                    this.typeIndex = typeIndex;
                    this.boundIndex = boundIndex;
                }

                public byte getTypeIndex () {
                    return typeIndex;
                }

                public byte getBoundIndex () {
                    return boundIndex;
                }
            }

            public static final class EmptyTarget extends TargetInfo {
            }

            public static final class FormalParameterTarget extends TargetInfo {
                private final byte formalIndex;

                public FormalParameterTarget (byte formalIndex) {
                    this.formalIndex = formalIndex;
                }

                public byte getFormalIndex () {
                    return formalIndex;
                }
            }

            public static final class ThrowTarget extends TargetInfo {
                private final int throwsTypeIndex;

                public ThrowTarget (int throwsTypeIndex) {
                    this.throwsTypeIndex = throwsTypeIndex;
                }

                public int getThrowsTypeIndex () {
                    return throwsTypeIndex;
                }
            }

            public static final class LocalVarTarget extends TargetInfo {
                private final int tableLength;
                private final LocalVar[] table;

                public LocalVarTarget (int tableLength, LocalVar[] table) {
                    this.tableLength = tableLength;
                    this.table = table;
                }

                public int getTableLength () {
                    return tableLength;
                }

                public LocalVar[] getTable () {
                    return table;
                }

                public static final class LocalVar {
                    private final int startPc;
                    private final int length;
                    private final int index;

                    public LocalVar (int startPc, int length, int index) {
                        this.startPc = startPc;
                        this.length = length;
                        this.index = index;
                    }

                    public int getStartPc () {
                        return startPc;
                    }

                    public int getLength () {
                        return length;
                    }

                    public int getIndex () {
                        return index;
                    }
                }
            }

            public static final class CatchTarget extends TargetInfo {
                private final int exceptionTableIndex;

                public CatchTarget (int exceptionTableIndex) {
                    this.exceptionTableIndex = exceptionTableIndex;
                }

                public int getExceptionTableIndex () {
                    return exceptionTableIndex;
                }
            }

            public static final class OffsetTarget extends TargetInfo {
                private final int offset;

                public OffsetTarget (int offset) {
                    this.offset = offset;
                }

                public int getOffset () {
                    return offset;
                }
            }

            public static final class TypeArgumentTarget extends TargetInfo {
                private final int offset;
                private final byte typeArgIndex;

                public TypeArgumentTarget (int offset, byte typeArgIndex) {
                    this.offset = offset;
                    this.typeArgIndex = typeArgIndex;
                }

                public int getOffset () {
                    return offset;
                }

                public byte getTypeArgIndex () {
                    return typeArgIndex;
                }
            }
        }

        public static final class TargetPath {
            private final int pathLength;
            private final Path[] paths;

            public TargetPath (int pathLength, Path[] paths) {
                this.pathLength = pathLength;
                this.paths = paths;
            }

            public int getPathLength () {
                return pathLength;
            }

            public Path[] getPaths () {
                return paths;
            }

            public static final class Path {
                /**
                 * <pre>
                 *  Value	Interpretation
                 * 0	    Annotation is deeper in an array type
                 * 1	    Annotation is deeper in a nested type
                 * 2	    Annotation is on the bound of a wildcard type argument of a parameterized type
                 * 3	    Annotation is on a type argument of a parameterized type
                 * </pre>
                 */
                private final byte typePathKind;
                private final byte typeArgumentLength;

                public Path (byte typePathKind, byte typeArgumentLength) {
                    this.typePathKind = typePathKind;
                    this.typeArgumentLength = typeArgumentLength;
                }

                public byte getTypePathKind () {
                    return typePathKind;
                }

                public byte getTypeArgumentLength () {
                    return typeArgumentLength;
                }
            }
        }
    }
}
