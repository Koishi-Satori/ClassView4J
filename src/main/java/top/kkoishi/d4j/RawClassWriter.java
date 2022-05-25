package top.kkoishi.d4j;

import top.kkoishi.d4j.attr.ConstantValueAttribute;
import top.kkoishi.d4j.attr.RuntimeAnnotationAttribute;
import top.kkoishi.d4j.attr.RuntimeTypeAnnotationAttribute;
import top.kkoishi.d4j.cp.ConstUtf8Info;

import java.util.ArrayList;

import static top.kkoishi.d4j.ClassDecoder.IllegalFileAttributeException;
import static top.kkoishi.d4j.ClassDecoder.to2bytes;
import static top.kkoishi.d4j.ClassDecoder.to4bytes;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_ANNOTATION_INTERFACE;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_ARRAY_TYPE;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_BOOLEAN;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_BYTE;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_CHAR;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_CLASS;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_DOUBLE;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_ENUM_CLASS;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_FLOAT;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_INT;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_LONG;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_SHORT;
import static top.kkoishi.d4j.ClassReader.ELEMENT_VALUE_TYPE_STRING;

class RawClassWriter {
    static final String CONSTANT_VALUE = "ConstantValue";
    static final String SYNTHETIC = "Synthetic";
    static final String DEPRECATED = "Deprecated";
    static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
    private byte[] majorBytecodeVersion = {0X00, 0X52};
    private byte[] minorBytecodeVersion = {0X00, 0X00};
    public final ArrayList<ConstPoolInfo> constPool = new ArrayList<>();
    public final ArrayList<ClassReader.ClassFileAccessFlag> accessFlags = new ArrayList<>(2);
    public int thisClassIndex;
    public int superClassIndex;
    public final ArrayList<Integer> interfaces = new ArrayList<>();
    public final ArrayList<FieldInfo> fields = new ArrayList<>();
    public final ArrayList<MethodInfo> methods = new ArrayList<>();
    public final ArrayList<AttributeInfo> attributeInfos = new ArrayList<>(2);
    protected ByteBuffer buffer = new ByteBuffer();
    private boolean closed = false;

    public RawClassWriter () {
        buffer.appendAll(ClassReader.FILE_HEAD);
    }

    public byte[] getMajorBytecodeVersion () {
        return majorBytecodeVersion;
    }

    public byte[] getMinorBytecodeVersion () {
        return minorBytecodeVersion;
    }

    public void setMajorBytecodeVersion (byte[] majorBytecodeVersion)
            throws IllegalFileAttributeException {
        if (majorBytecodeVersion.length != 2) {
            throw new IllegalFileAttributeException("The amount of major bytecode version bytes must be 2!");
        }
        this.majorBytecodeVersion = majorBytecodeVersion;
    }

    public void setMinorBytecodeVersion (byte[] minorBytecodeVersion) throws IllegalFileAttributeException {
        if (minorBytecodeVersion.length != 2) {
            throw new IllegalFileAttributeException("The amount of minor bytecode version bytes must be 2!");
        }
        this.minorBytecodeVersion = minorBytecodeVersion;
    }

    /**
     * Write the class info to the buffer.
     * This method will not check if the indexes is invalid.
     *
     * @throws IllegalTypeException when the tag of cp_info is illegal, or the type of some structure is invalid.
     * @see ClassReader
     */
    public void write () throws IllegalTypeException {
        if (closed) {
            throw new IllegalStateException("The RawClassWriter has been closed!");
        }
        buffer.appendAll(minorBytecodeVersion);
        buffer.appendAll(majorBytecodeVersion);
        buffer.appendAll(to2bytes(constPool.size() + 1));

        // write the const pool.
        for (final ConstPoolInfo cpInfo : constPool) {
            final byte tag = cpInfo.tag;
            buffer.append(tag);
            // The tag must be tags defined in jvm bytecode standard.
            // And permitted tags have been listed in ConstPoolInfo.class.
            final boolean isIllegalTag = (tag == ConstPoolInfo.CONSTANT_UTF8_INFO ||
                    (tag >= ConstUtf8Info.CONSTANT_INTEGER_INFO && tag <= ConstUtf8Info.CONSTANT_NAME_AND_TYPE_INFO) ||
                    (tag >= ConstUtf8Info.CONSTANT_METHOD_HANDLE_INFO && tag <= ConstPoolInfo.CONSTANT_PACKAGE));
            if (isIllegalTag) {
                // If the data_amount is 1, then the data must be [B, or [[B.
                if (cpInfo.dataAmount() == 1) {
                    if (cpInfo instanceof final ConstUtf8Info utfCastInfo) {
                        // calculate length.
                        buffer.appendAll(to2bytes(utfCastInfo.length()));
                        buffer.appendAll(utfCastInfo.getBytes());
                    } else {
                        buffer.appendAll((byte[]) cpInfo.data());
                    }
                } else {
                    for (final byte[] datum : ((byte[][]) cpInfo.data())) {
                        buffer.appendAll(datum);
                    }
                }
            } else {
                throw new IllegalTypeException("The tag of cp_info(" + cpInfo + ") is illegal!");
            }
        }

        // write access_flags and class_index.
        buffer.appendAll(to2bytes(decodeFileAccessFlags(accessFlags)));
        buffer.appendAll(to2bytes(thisClassIndex));
        buffer.appendAll(to2bytes(superClassIndex));

        // write interfaces.
        buffer.appendAll(to2bytes(interfaces.size()));
        for (final int index : interfaces) {
            buffer.appendAll(to2bytes(index));
        }

        // write fields.
        buffer.appendAll(to2bytes(fields.size()));
        for (final FieldInfo fieldInfo : fields) {
            // write four u2, then write the attribute_info table.
            buffer.appendAll(to2bytes(fieldInfo.getAccessFlags()));
            buffer.appendAll(to2bytes(fieldInfo.getNameIndex()));
            buffer.appendAll(to2bytes(fieldInfo.getDescriptorIndex()));
            buffer.appendAll(to2bytes(fieldInfo.getAttributeCount()));
            writeFieldAttributes(fieldInfo.getAttributes());
        }
    }

    /**
     * Write the field_info attribute table elements.
     * Supported attribute_info type:
     * ConstantValue, Synthetic, Signature, Deprecated, RuntimeVisibleAnnotations,
     * RuntimeInvisibleAnnotations, RuntimeVisibleTypeAnnotations,
     * RuntimeInvisibleTypeAnnotations
     *
     * @param attributes attribute_info table.
     * @throws IllegalTypeException when the type is not supported.
     */
    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected void writeFieldAttributes (ArrayList<AttributeInfo> attributes) throws IllegalTypeException {
        for (final AttributeInfo attributeInfo : attributes) {
            buffer.appendAll(to2bytes(attributeInfo.attributeNameIndex));
            buffer.appendAll(to4bytes(attributeInfo.attributeLength));
            // get the name of attribute_info and check if illegal.
            final String name = ((ConstUtf8Info) constPool.get(attributeInfo.attributeNameIndex - 1)).getUtf8();
            // why fu*king IDEA tells that the f**king condition is unreachable????
            // and fu*k alibaba standard, why this sh*t can not support enhanced-switch sentence?
            switch (name) {
                case CONSTANT_VALUE: {
                    buffer.appendAll(to2bytes(((ConstantValueAttribute) attributeInfo).getConstantValueIndex()));
                    break;
                }
                case RUNTIME_INVISIBLE_ANNOTATIONS:
                case RUNTIME_VISIBLE_ANNOTATIONS: {
                    final var attr = (RuntimeAnnotationAttribute) attributeInfo;
                    buffer.appendAll(to2bytes(attr.getNumAnnotations()));
                    for (final RuntimeAnnotationAttribute.Annotation annotation : attr.getAnnotations()) {
                        buffer.appendAll(to2bytes(annotation.getTypeIndex()));
                        buffer.appendAll(to2bytes(annotation.getNumElementValuePairs()));
                        for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs elementValuePairs : annotation.elementValuePairs()) {
                            buffer.appendAll(to2bytes(elementValuePairs.getElementNameIndex()));
                            final var value = elementValuePairs.getElementValue();
                            writeValue(value.getValue(), value.getTag());
                        }
                    }
                    break;
                }
                case RUNTIME_VISIBLE_TYPE_ANNOTATIONS: {
                    final var attr = (RuntimeTypeAnnotationAttribute) attributeInfo;
                    buffer.appendAll(to2bytes(attr.getNumAnnotations()));
                    for (final RuntimeTypeAnnotationAttribute.TypeAnnotation typeAnnotation : attr.getTypeAnnotations()) {
                        buffer.append(typeAnnotation.getTargetType());
                        // write target_info, target_path and so on.

                    }
                }
                case DEPRECATED:
                case SYNTHETIC: {
                    break;
                }
                default:
                    throw new IllegalTypeException("The attribute_info type " + name + " is invalid!");
            }
        }
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    protected void writeValue (RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value value,
                               byte tag)
            throws IllegalTypeException {
        buffer.append(tag);
        switch (tag) {
            case ELEMENT_VALUE_TYPE_STRING:
            case ELEMENT_VALUE_TYPE_SHORT:
            case ELEMENT_VALUE_TYPE_LONG:
            case ELEMENT_VALUE_TYPE_INT:
            case ELEMENT_VALUE_TYPE_FLOAT:
            case ELEMENT_VALUE_TYPE_DOUBLE:
            case ELEMENT_VALUE_TYPE_CHAR:
            case ELEMENT_VALUE_TYPE_BOOLEAN:
            case ELEMENT_VALUE_TYPE_BYTE: {
                buffer.appendAll(to2bytes(((
                        RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ConstValue) value)
                        .getConstValueIndex()));
                break;
            }
            case ELEMENT_VALUE_TYPE_ENUM_CLASS: {
                final var castValue = (RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.EnumConstValue) value;
                buffer.appendAll(to2bytes(castValue.getTypeNameIndex()));
                buffer.appendAll(to2bytes(castValue.getConstNameIndex()));
                break;
            }
            case ELEMENT_VALUE_TYPE_CLASS: {
                buffer.appendAll(to2bytes(((
                        RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ClassInfoValue) value)
                        .getClassInfoIndex()));
                break;
            }
            case ELEMENT_VALUE_TYPE_ANNOTATION_INTERFACE: {
                final var annotation =
                        ((RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.AnnotationValue) value).getAnnotationValue();
                buffer.appendAll(to2bytes(annotation.getTypeIndex()));
                buffer.appendAll(to2bytes(annotation.getNumElementValuePairs()));
                for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs elementValuePairs : annotation.elementValuePairs()) {
                    buffer.appendAll(to2bytes(elementValuePairs.getElementNameIndex()));
                    final var elementValue = elementValuePairs.getElementValue();
                    writeValue(elementValue.getValue(), elementValue.getTag());
                }
                break;
            }
            case ELEMENT_VALUE_TYPE_ARRAY_TYPE: {
                final var castValue = ((RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue.Value.ArrayValue) value);
                buffer.appendAll(to2bytes(castValue.getNumValue()));
                for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs.ElementValue elementValue : castValue.getValues()) {
                    writeValue(elementValue.getValue(), elementValue.getTag());
                }
                break;
            }
            default:
                throw new IllegalTypeException("The tag of element_value::value (" + tag + ") is invalid!");
        }
    }

    public byte[] get () {
        closed = true;
        return buffer.build();
    }

    public static class ClassWriterException extends Exception {
        public ClassWriterException () {
        }

        public ClassWriterException (String message) {
            super(message);
        }

        public ClassWriterException (String message, Throwable cause) {
            super(message, cause);
        }

        public ClassWriterException (Throwable cause) {
            super(cause);
        }
    }

    public static final class IllegalTypeException extends ClassWriterException {
        public IllegalTypeException (String message) {
            super(message);
        }
    }

    static int decodeFileAccessFlags (ArrayList<ClassReader.ClassFileAccessFlag> accessFlags) {
        int accessFlagsNumber = 0;
        for (final ClassReader.ClassFileAccessFlag accessFlag : accessFlags) {
            accessFlagsNumber += accessFlag.accessFlag;
        }
        return accessFlagsNumber;
    }
}
