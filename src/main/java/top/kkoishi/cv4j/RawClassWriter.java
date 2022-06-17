package top.kkoishi.cv4j;

import top.kkoishi.cv4j.attr.AnnotationDefaultAttribute;
import top.kkoishi.cv4j.attr.BootstrapMethodsAttribute;
import top.kkoishi.cv4j.attr.CodeAttribute;
import top.kkoishi.cv4j.attr.ConstantValueAttribute;
import top.kkoishi.cv4j.attr.EnclosingMethodAttribute;
import top.kkoishi.cv4j.attr.ExceptionsAttribute;
import top.kkoishi.cv4j.attr.InnerClassAttribute;
import top.kkoishi.cv4j.attr.LineNumberTableAttribute;
import top.kkoishi.cv4j.attr.LocalVariableTableAttribute;
import top.kkoishi.cv4j.attr.LocalVariableTypeAttribute;
import top.kkoishi.cv4j.attr.MethodParametersAttribute;
import top.kkoishi.cv4j.attr.ModuleAttribute;
import top.kkoishi.cv4j.attr.ModuleMainClassAttribute;
import top.kkoishi.cv4j.attr.ModulePackagesAttribute;
import top.kkoishi.cv4j.attr.NestHostAttribute;
import top.kkoishi.cv4j.attr.NestMembersAttribute;
import top.kkoishi.cv4j.attr.PermittedSubclassesAttribute;
import top.kkoishi.cv4j.attr.RecordAttribute;
import top.kkoishi.cv4j.attr.RuntimeAnnotationAttribute;
import top.kkoishi.cv4j.attr.RuntimeTypeAnnotationAttribute;
import top.kkoishi.cv4j.attr.SignatureAttribute;
import top.kkoishi.cv4j.attr.SourceDebugExtensionAttribute;
import top.kkoishi.cv4j.attr.SourceFileAttribute;
import top.kkoishi.cv4j.attr.StackMapTableAttribute;
import top.kkoishi.cv4j.attr.frames.AppendFrames;
import top.kkoishi.cv4j.attr.frames.ChopFrame;
import top.kkoishi.cv4j.attr.frames.FullFrame;
import top.kkoishi.cv4j.attr.frames.SameFrameExtended;
import top.kkoishi.cv4j.attr.frames.SameLocals1StackItemFrame;
import top.kkoishi.cv4j.attr.frames.SameLocals1StackItemFrameExtended;
import top.kkoishi.cv4j.attr.frames.verifi.ObjectVariableInfo;
import top.kkoishi.cv4j.attr.frames.verifi.UninitializedVariableInfo;
import top.kkoishi.cv4j.attr.frames.verifi.VerificationTypeInfo;
import top.kkoishi.cv4j.cp.ConstUtf8Info;

import java.util.ArrayList;

import static top.kkoishi.cv4j.ClassDecoder.IllegalFileAttributeException;
import static top.kkoishi.cv4j.ClassDecoder.to2bytes;
import static top.kkoishi.cv4j.ClassDecoder.to4bytes;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_ANNOTATION_INTERFACE;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_ARRAY_TYPE;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_BOOLEAN;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_BYTE;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_CHAR;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_CLASS;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_DOUBLE;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_ENUM_CLASS;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_FLOAT;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_INT;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_LONG;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_SHORT;
import static top.kkoishi.cv4j.ClassReader.ELEMENT_VALUE_TYPE_STRING;

public class RawClassWriter {
    static final String CONSTANT_VALUE = "ConstantValue";
    static final String SYNTHETIC = "Synthetic";
    static final String DEPRECATED = "Deprecated";
    static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
    static final String SOURCE_FILE = "SourceFile";
    static final String CODE = "Code";
    static final String LINE_NUMBER_TABLE = "LineNumberTable";
    static final String EXCEPTIONS = "Exceptions";
    static final String METHOD_PARAMETERS = "MethodParameters";
    static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS = "RuntimeInvisibleTypeAnnotations";
    static final String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    static final String LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
    static final String STACK_MAP_TABLE = "StackMapTable";
    static final String ANNOTATION_DEFAULT = "AnnotationDefault";
    static final String SIGNATURE = "Signature";
    static final String INNER_CLASSES = "InnerClasses";
    static final String ENCLOSING_METHOD = "EnclosingMethod";
    static final String SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
    static final String BOOTSTRAP_METHODS = "BootstrapMethods";
    static final String MODULE = "Module";
    static final String MODULE_PACKAGES = "ModulePackages";
    static final String MODULE_MAIN_CLASS = "ModuleMainClass";
    static final String NEST_HOST = "NestHost";
    static final String NEST_MEMBERS = "NestMembers";
    static final String RECORD = "Record";
    static final String PERMITTED_SUBCLASSES = "PermittedSubclasses";

    private byte[] majorBytecodeVersion = {0X00, 0X52};
    private byte[] minorBytecodeVersion = {0X00, 0X00};
    public final ArrayList<ConstPoolInfo> constPool = new ArrayList<>();
    public final ArrayList<ClassReader.ClassFileAccessFlag> accessFlags = new ArrayList<>(2);
    public int thisClassIndex;
    public int superClassIndex;
    public final ArrayList<Integer> interfaces = new ArrayList<>();
    public final ArrayList<FieldInfo> fields = new ArrayList<>();
    public final ArrayList<MethodInfo> methods = new ArrayList<>();
    public final ArrayList<Attribute_info> attributeInfo = new ArrayList<>(2);
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

        //write methods.
        buffer.appendAll(to2bytes(methods.size()));
        for (final MethodInfo method : methods) {
            buffer.appendAll(to2bytes(method.getAccessFlags()));
            buffer.appendAll(to2bytes(method.getNameIndex()));
            buffer.appendAll(to2bytes(method.getDescriptorIndex()));
            buffer.appendAll(to2bytes(method.getAttributesCount()));
            writeMethodAttributes(method.getAttributes());
        }


        //write attributes table.
        buffer.appendAll(to2bytes(attributeInfo.size()));
        writeClassFileAttributes();
    }

    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected void writeMethodAttributes (Attribute_info[] attributes) throws IllegalTypeException {
        for (final Attribute_info attribute : attributes) {
            buffer.appendAll(to2bytes(attribute.attributeNameIndex));
            buffer.appendAll(to4bytes(attribute.attributeLength));
            final String name = ((ConstUtf8Info) constPool.get(attribute.attributeNameIndex - 1)).getUtf8();
            switch (name) {
                case CODE: {
                    final var castAttr = (CodeAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getMaxStack()));
                    buffer.appendAll(to2bytes(castAttr.getMaxLocals()));
                    buffer.appendAll(to4bytes(castAttr.getCodeLength()));
                    buffer.appendAll(castAttr.getCode());
                    buffer.appendAll(to2bytes(castAttr.getExceptionTableLength()));
                    for (final CodeAttribute.CodeException codeException : castAttr.getExceptionTable()) {
                        buffer.appendAll(to2bytes(codeException.getStartPc()));
                        buffer.appendAll(to2bytes(codeException.getEndPc()));
                        buffer.appendAll(to2bytes(codeException.getHandlerPc()));
                        buffer.appendAll(to2bytes(codeException.getCatchType()));
                    }
                    buffer.appendAll(to2bytes(castAttr.getAttributesCount()));
                    writeCodeAttributes(castAttr.getAttributes());
                    break;
                }
                case EXCEPTIONS: {
                    final var castAttr = (ExceptionsAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumberOfExceptions()));
                    for (final ExceptionsAttribute.ExceptionIndexTable exceptionIndex : castAttr.getExceptionIndexTable()) {
                        buffer.appendAll(to2bytes(exceptionIndex.getExceptionClassIndex()));
                    }
                    break;
                }
                case METHOD_PARAMETERS: {
                    final var castAttr = (MethodParametersAttribute) attribute;
                    buffer.append(castAttr.getParametersCount());
                    for (final MethodParametersAttribute.Parameter parameter : castAttr.getParameters()) {
                        // The parameter access_flags must be:METHOD_PARAMETERS_ACC_MANDATED,
                        // METHOD_PARAMETERS_ACC_SYNTHETIC, METHOD_PARAMETERS_ACC_FINAL
                        buffer.appendAll(to2bytes(parameter.getNameIndex()));
                        buffer.appendAll(to2bytes(parameter.getAccessFlags()));
                    }
                    break;
                }
                case RUNTIME_INVISIBLE_ANNOTATIONS:
                case RUNTIME_VISIBLE_ANNOTATIONS: {
                    final var castAttr = (RuntimeAnnotationAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumAnnotations()));
                    for (final RuntimeAnnotationAttribute.Annotation annotation : castAttr.getAnnotations()) {
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
                case RUNTIME_INVISIBLE_TYPE_ANNOTATIONS:
                case RUNTIME_VISIBLE_TYPE_ANNOTATIONS: {
                    final var castAttr = (RuntimeTypeAnnotationAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumAnnotations()));
                    for (final RuntimeTypeAnnotationAttribute.TypeAnnotation typeAnnotation : castAttr.getTypeAnnotations()) {
                        final byte tag = typeAnnotation.getTargetType();
                        buffer.append(tag);
                        final var targetPath = typeAnnotation.getTargetPath();
                        buffer.append((byte) targetPath.getPathLength());
                        for (final RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path path : targetPath.getPaths()) {
                            buffer.append(path.getTypePathKind());
                            buffer.append(path.getTypeArgumentLength());
                        }
                        buffer.appendAll(to2bytes(typeAnnotation.getTypeIndex()));
                        buffer.appendAll(to2bytes(typeAnnotation.getNumberElementValuePairs()));
                        for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs elementValuePair : typeAnnotation.getElementValuePairs()) {
                            buffer.appendAll(to2bytes(elementValuePair.getElementNameIndex()));
                            final var value = elementValuePair.getElementValue();
                            writeValue(value.getValue(), value.getTag());
                        }
                    }
                    break;
                }
                case ANNOTATION_DEFAULT: {
                    final var value = ((AnnotationDefaultAttribute) attribute).getElementValue();
                    writeValue(value.getValue(), value.getTag());
                    break;
                }
                case SIGNATURE: {
                    buffer.appendAll(to2bytes(((SignatureAttribute) attribute).getSignatureIndex()));
                    break;
                }
                case SYNTHETIC:
                case DEPRECATED: {
                    break;
                }
                default:// TODO: 2022/5/26
            }
        }
    }

    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected void writeCodeAttributes (Attribute_info[] attributes) throws IllegalTypeException {
        for (final Attribute_info attribute : attributes) {
            buffer.appendAll(to2bytes(attribute.attributeNameIndex));
            buffer.appendAll(to4bytes(attribute.attributeLength));
            final String name = ((ConstUtf8Info) constPool.get(attribute.attributeNameIndex - 1)).getUtf8();
            switch (name) {
                case LINE_NUMBER_TABLE: {
                    final var castAttr = (LineNumberTableAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getLineNumberTableLength()));
                    for (final LineNumberTableAttribute.LineNumber lineNumber : castAttr.getLineNumberTable()) {
                        buffer.appendAll(to2bytes(lineNumber.getStartPc()));
                        buffer.appendAll(to2bytes(lineNumber.getLineNumber()));
                    }
                    break;
                }
                case LOCAL_VARIABLE_TABLE: {
                    final var castAttr = (LocalVariableTableAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getLocalVariableTableLength()));
                    for (final LocalVariableTableAttribute.LocalVariable localVariable : castAttr.getLocalVariables()) {
                        buffer.appendAll(to2bytes(localVariable.getStartPc()));
                        buffer.appendAll(to2bytes(localVariable.getLength()));
                        buffer.appendAll(to2bytes(localVariable.getNameIndex()));
                        buffer.appendAll(to2bytes(localVariable.getDescriptorIndex()));
                        buffer.appendAll(to2bytes(localVariable.getIndex()));
                    }
                    break;
                }
                case LOCAL_VARIABLE_TYPE_TABLE: {
                    final var castAttr = (LocalVariableTypeAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getLocalVariableTypeTableLength()));
                    for (final LocalVariableTypeAttribute.localVariableType localVariableType : castAttr.getLocalVariableTypeTable()) {
                        buffer.appendAll(to2bytes(localVariableType.getStartPc()));
                        buffer.appendAll(to2bytes(localVariableType.getLength()));
                        buffer.appendAll(to2bytes(localVariableType.getNameIndex()));
                        buffer.appendAll(to2bytes(localVariableType.getSignatureIndex()));
                        buffer.appendAll(to2bytes(localVariableType.getIndex()));
                    }
                    break;
                }
                case STACK_MAP_TABLE: {
                    final var castAttr = (StackMapTableAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumberOfEntries()));
                    for (final StackMapTableAttribute.StackMapFrame frame : castAttr.getStackMapFrameEntries()) {
                        final var type = frame.getFrameType();
                        buffer.append(type);
                        switch (type) {
                            case (byte) 247: {
                                final var castFrame = (SameLocals1StackItemFrameExtended) frame;
                                buffer.appendAll(to2bytes(castFrame.getOffsetDelta()));
                                writeVerificationTypeInfo(castFrame.getStack());
                                break;
                            }
                            case (byte) 248:
                            case (byte) 249:
                            case (byte) 250: {
                                buffer.appendAll(to2bytes(((ChopFrame) frame).getOffsetDelta()));
                                break;
                            }
                            case (byte) 251: {
                                buffer.appendAll(to2bytes(((SameFrameExtended) frame).getOffsetDelta()));
                                break;
                            }
                            case (byte) 252:
                            case (byte) 253:
                            case (byte) 254: {
                                final var castFrame = (AppendFrames) frame;
                                buffer.appendAll(to2bytes(castFrame.getOffsetDelta()));
                                if (castFrame.getLocals().size() != (type & 0XFF) - 251) {
                                    throw new IllegalTypeException("");
                                }
                                for (VerificationTypeInfo verificationTypeInfo : castFrame.getLocals()) {
                                    writeVerificationTypeInfo(verificationTypeInfo);
                                }
                                break;
                            }
                            case (byte) 255: {
                                final var castFrame = (FullFrame) frame;
                                buffer.appendAll(to2bytes(castFrame.getOffsetDelta()));
                                buffer.appendAll(to2bytes(castFrame.getNumberOfLocals()));
                                for (VerificationTypeInfo verificationTypeInfo : castFrame.getLocals()) {
                                    writeVerificationTypeInfo(verificationTypeInfo);
                                }
                                buffer.appendAll(to2bytes(castFrame.getNumberOfStackItems()));
                                for (VerificationTypeInfo verificationTypeInfo : castFrame.getStack()) {
                                    writeVerificationTypeInfo(verificationTypeInfo);
                                }
                                break;
                            }
                            default: {
                                if (type >= 64 && type <= 127) {
                                    writeVerificationTypeInfo(((SameLocals1StackItemFrame) frame).getStack());
                                } else if (type < 0 || type > 64) {
                                    throw new IllegalTypeException("");
                                }
                            }
                        }
                    }
                }
                default: {
                    throw new IllegalTypeException("");
                }
            }
        }
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    protected void writeVerificationTypeInfo (VerificationTypeInfo verificationTypeInfo) throws IllegalTypeException {
        final byte tag = verificationTypeInfo.tag();
        buffer.append(tag);
        switch (tag) {
            case VerificationTypeInfo.TOP_VARIABLE_INFO:
            case VerificationTypeInfo.INTEGER_VARIABLE_INFO:
            case VerificationTypeInfo.DOUBLE_VARIABLE_INFO:
            case VerificationTypeInfo.FLOAT_VARIABLE_INFO:
            case VerificationTypeInfo.NULL_VARIABLE_INFO:
            case VerificationTypeInfo.UNINITIALIZED_THIS:
            case VerificationTypeInfo.LONG_VARIABLE_INFO: {
                break;
            }
            case VerificationTypeInfo.UNINITIALIZED_VARIABLE_INFO: {
                buffer.appendAll(to2bytes(((UninitializedVariableInfo) verificationTypeInfo).getOffset()));
                break;
            }
            case VerificationTypeInfo.OBJECT_VARIABLE_INFO: {
                buffer.appendAll(to2bytes(((ObjectVariableInfo) verificationTypeInfo).getCpoolIndex()));
                break;
            }
            default: {
                throw new IllegalTypeException("");
            }
        }
    }

    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected void writeClassFileAttributes () throws IllegalTypeException {
        for (final Attribute_info attribute : attributeInfo) {
            buffer.appendAll(to2bytes(attribute.attributeNameIndex));
            buffer.appendAll(to4bytes(attribute.attributeLength));
            final String name = ((ConstUtf8Info) constPool.get(attribute.attributeNameIndex - 1)).getUtf8();
            switch (name) {
                case SOURCE_FILE: {
                    buffer.appendAll(to2bytes(((SourceFileAttribute) attribute).getSourceFileIndex()));
                    break;
                }
                case RUNTIME_INVISIBLE_ANNOTATIONS:
                case RUNTIME_VISIBLE_ANNOTATIONS: {
                    final var castAttr = (RuntimeAnnotationAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumAnnotations()));
                    for (final RuntimeAnnotationAttribute.Annotation annotation : castAttr.getAnnotations()) {
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
                case RUNTIME_INVISIBLE_TYPE_ANNOTATIONS:
                case RUNTIME_VISIBLE_TYPE_ANNOTATIONS: {
                    final var castAttr = (RuntimeTypeAnnotationAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumAnnotations()));
                    for (final RuntimeTypeAnnotationAttribute.TypeAnnotation typeAnnotation : castAttr.getTypeAnnotations()) {
                        final byte tag = typeAnnotation.getTargetType();
                        buffer.append(tag);
                        final var targetPath = typeAnnotation.getTargetPath();
                        buffer.append((byte) targetPath.getPathLength());
                        for (final RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path path : targetPath.getPaths()) {
                            buffer.append(path.getTypePathKind());
                            buffer.append(path.getTypeArgumentLength());
                        }
                        buffer.appendAll(to2bytes(typeAnnotation.getTypeIndex()));
                        buffer.appendAll(to2bytes(typeAnnotation.getNumberElementValuePairs()));
                        for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs elementValuePair : typeAnnotation.getElementValuePairs()) {
                            buffer.appendAll(to2bytes(elementValuePair.getElementNameIndex()));
                            final var value = elementValuePair.getElementValue();
                            writeValue(value.getValue(), value.getTag());
                        }
                    }
                    break;
                }
                case SIGNATURE: {
                    buffer.appendAll(to2bytes(((SignatureAttribute) attribute).getSignatureIndex()));
                    break;
                }
                case INNER_CLASSES: {
                    final var castAttr = (InnerClassAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumberOfClasses()));
                    for (final InnerClassAttribute.InnerClassesInfo innerClass : castAttr.getInnerClassesInfo()) {
                        buffer.appendAll(to2bytes(innerClass.getInnerClassInfoIndex()));
                        buffer.appendAll(to2bytes(innerClass.getOuterClassInfoIndex()));
                        buffer.appendAll(to2bytes(innerClass.getInnerNameIndex()));
                        buffer.appendAll(to2bytes(innerClass.getInnerClassAccessFlags()));
                    }
                    break;
                }
                case ENCLOSING_METHOD: {
                    final var castAttr = (EnclosingMethodAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getClassIndex()));
                    buffer.appendAll(to2bytes(castAttr.getMethodIndex()));
                    break;
                }
                case SOURCE_DEBUG_EXTENSION: {
                    final var bits = ((SourceDebugExtensionAttribute) attribute).getDebugExtensionTable();
                    if (bits.length != attribute.attributeLength) {
                        throw new IllegalTypeException("");
                    }
                    buffer.appendAll(bits);
                    break;
                }
                case BOOTSTRAP_METHODS: {
                    final var castAttr = (BootstrapMethodsAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumBootstrapMethods()));
                    for (final BootstrapMethodsAttribute.BootstrapMethod method : castAttr.getBootstrapMethods()) {
                        buffer.appendAll(to2bytes(method.getBootstrapMethodRef()));
                        buffer.appendAll(to2bytes(method.getNumBootstrapArguments()));
                        for (final int i : method.getBootStrapArguments()) {
                            buffer.appendAll(to2bytes(i));
                        }
                    }
                    break;
                }
                case MODULE: {
                    final var castAttr = (ModuleAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getModuleNameIndex()));
                    buffer.appendAll(to2bytes(castAttr.getModuleFlags()));
                    buffer.appendAll(to2bytes(castAttr.getModuleVersionIndex()));
                    buffer.appendAll(to2bytes(castAttr.getRequiresCount()));
                    for (final ModuleAttribute.Require require : castAttr.getRequires()) {
                        buffer.appendAll(to2bytes(require.getRequiresIndex()));
                        buffer.appendAll(to2bytes(require.getRequiresFlags()));
                        buffer.appendAll(to2bytes(require.getRequiresVersionIndex()));
                    }
                    //read export, open, uses(int), and provide table.
                    buffer.appendAll(to2bytes(castAttr.getExportsCount()));
                    for (final ModuleAttribute.Export export : castAttr.getExports()) {
                        buffer.appendAll(to2bytes(export.getExportsIndex()));
                        buffer.appendAll(to2bytes(export.getExportsFlags()));
                        buffer.appendAll(to2bytes(export.getExportsToCount()));
                        for (final int exportsToIndex : export.getExportsToIndexes()) {
                            buffer.appendAll(to2bytes(exportsToIndex));
                        }
                    }
                    buffer.appendAll(to2bytes(castAttr.getOpensCount()));
                    for (final ModuleAttribute.Open open : castAttr.getOpens()) {
                        buffer.appendAll(to2bytes(open.getOpensIndex()));
                        buffer.appendAll(to2bytes(open.getOpensFlags()));
                        buffer.appendAll(to2bytes(open.getOpensToCount()));
                        for (final int openTo : open.getOpensToIndexes()) {
                            buffer.appendAll(to2bytes(openTo));
                        }
                    }
                    buffer.appendAll(to2bytes(castAttr.getUsesCount()));
                    for (final int use : castAttr.getUses()) {
                        buffer.appendAll(to2bytes(use));
                    }
                    buffer.appendAll(to2bytes(castAttr.getProvidesCount()));
                    for (final ModuleAttribute.Provide provide : castAttr.getProvides()) {
                        buffer.appendAll(to2bytes(provide.getProvidesIndex()));
                        buffer.appendAll(to2bytes(provide.getProvidesCount()));
                        for (final int index : provide.getProvides()) {
                            buffer.appendAll(to2bytes(index));
                        }
                    }
                    break;
                }
                case MODULE_PACKAGES: {
                    final var castAttr = (ModulePackagesAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getPackagesCount()));
                    for (final int packageIndex : castAttr.getPackages()) {
                        buffer.appendAll(to2bytes(packageIndex));
                    }
                    break;
                }
                case MODULE_MAIN_CLASS: {
                    buffer.appendAll(to2bytes(((ModuleMainClassAttribute) attribute).getMainClassIndex()));
                    break;
                }
                case NEST_HOST: {
                    buffer.appendAll(to2bytes(((NestHostAttribute) attribute).getHotClassIndex()));
                    break;
                }
                case NEST_MEMBERS: {
                    final var castAttr = (NestMembersAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumberOfClasses()));
                    for (final int clz : castAttr.getClasses()) {
                        buffer.appendAll(to2bytes(clz));
                    }
                    break;
                }
                case RECORD: {
                    final var castAttr = (RecordAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getComponentCount()));
                    for (final RecordAttribute.RecordComponentInfo comp : castAttr.getRecordComponents()) {
                        buffer.appendAll(to2bytes(comp.getRecordComponentNameIndex()));
                        buffer.appendAll(to2bytes(comp.getDescriptorIndex()));
                        buffer.appendAll(to2bytes(comp.getAttributesCount()));
                        writeRecordCompAttribute(comp.getAttributeInfos());
                    }
                    break;
                }
                case PERMITTED_SUBCLASSES: {
                    final var castAttr = (PermittedSubclassesAttribute) attribute;
                    buffer.appendAll(to2bytes(castAttr.getNumberOfClasses()));
                    for (final int index : castAttr.getClasses()) {
                        buffer.appendAll(to2bytes(index));
                    }
                    break;
                }
                case SYNTHETIC:
                case DEPRECATED: {
                    break;
                }
                default:
                    throw new IllegalTypeException("");
            }
        }
    }

    @SuppressWarnings({"ConstantConditions", "EnhancedSwitchMigration"})
    protected void writeRecordCompAttribute (Attribute_info[] attributes) throws IllegalTypeException {
        for (final Attribute_info attribute : attributes) {
            buffer.appendAll(to2bytes(attribute.attributeNameIndex));
            buffer.appendAll(to4bytes(attribute.attributeLength));
            // get the name of attribute_info and check if illegal.
            final String name = ((ConstUtf8Info) constPool.get(attribute.attributeNameIndex - 1)).getUtf8();
            // why fu*king IDEA tells that the f**king condition is unreachable????
            // and fu*k alibaba standard, why this sh*t can not support enhanced-switch sentence?
            switch (name) {
                case RUNTIME_INVISIBLE_ANNOTATIONS:
                case RUNTIME_VISIBLE_ANNOTATIONS: {
                    final var attr = (RuntimeAnnotationAttribute) attribute;
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
                case RUNTIME_INVISIBLE_TYPE_ANNOTATIONS:
                case RUNTIME_VISIBLE_TYPE_ANNOTATIONS: {
                    final var attr = (RuntimeTypeAnnotationAttribute) attribute;
                    buffer.appendAll(to2bytes(attr.getNumAnnotations()));
                    for (final RuntimeTypeAnnotationAttribute.TypeAnnotation typeAnnotation : attr.getTypeAnnotations()) {
                        final byte tag = typeAnnotation.getTargetType();
                        buffer.append(tag);
                        final var targetPath = typeAnnotation.getTargetPath();
                        buffer.append((byte) targetPath.getPathLength());
                        for (final RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path path : targetPath.getPaths()) {
                            buffer.append(path.getTypePathKind());
                            buffer.append(path.getTypeArgumentLength());
                        }
                        buffer.appendAll(to2bytes(typeAnnotation.getTypeIndex()));
                        buffer.appendAll(to2bytes(typeAnnotation.getNumberElementValuePairs()));
                        for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs elementValuePair : typeAnnotation.getElementValuePairs()) {
                            buffer.appendAll(to2bytes(elementValuePair.getElementNameIndex()));
                            final var value = elementValuePair.getElementValue();
                            writeValue(value.getValue(), value.getTag());
                        }
                    }
                    break;
                }
                case SIGNATURE: {
                    buffer.appendAll(to2bytes(((SignatureAttribute) attribute).getSignatureIndex()));
                    break;
                }
                default:
                    throw new IllegalTypeException("");
            }
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
    protected void writeFieldAttributes (ArrayList<Attribute_info> attributes) throws IllegalTypeException {
        for (final Attribute_info attribute : attributes) {
            buffer.appendAll(to2bytes(attribute.attributeNameIndex));
            buffer.appendAll(to4bytes(attribute.attributeLength));
            // get the name of attribute_info and check if illegal.
            final String name = ((ConstUtf8Info) constPool.get(attribute.attributeNameIndex - 1)).getUtf8();
            // why fu*king IDEA tells that the f**king condition is unreachable????
            // and fu*k alibaba standard, why this sh*t can not support enhanced-switch sentence?
            switch (name) {
                case CONSTANT_VALUE: {
                    buffer.appendAll(to2bytes(((ConstantValueAttribute) attribute).getConstantValueIndex()));
                    break;
                }
                case RUNTIME_INVISIBLE_ANNOTATIONS:
                case RUNTIME_VISIBLE_ANNOTATIONS: {
                    final var attr = (RuntimeAnnotationAttribute) attribute;
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
                case RUNTIME_INVISIBLE_TYPE_ANNOTATIONS:
                case RUNTIME_VISIBLE_TYPE_ANNOTATIONS: {
                    final var attr = (RuntimeTypeAnnotationAttribute) attribute;
                    buffer.appendAll(to2bytes(attr.getNumAnnotations()));
                    for (final RuntimeTypeAnnotationAttribute.TypeAnnotation typeAnnotation : attr.getTypeAnnotations()) {
                        final byte tag = typeAnnotation.getTargetType();
                        buffer.append(tag);
                        final var targetPath = typeAnnotation.getTargetPath();
                        buffer.append((byte) targetPath.getPathLength());
                        for (final RuntimeTypeAnnotationAttribute.TypeAnnotation.TargetPath.Path path : targetPath.getPaths()) {
                            buffer.append(path.getTypePathKind());
                            buffer.append(path.getTypeArgumentLength());
                        }
                        buffer.appendAll(to2bytes(typeAnnotation.getTypeIndex()));
                        buffer.appendAll(to2bytes(typeAnnotation.getNumberElementValuePairs()));
                        for (final RuntimeAnnotationAttribute.Annotation.ElementValuePairs elementValuePair : typeAnnotation.getElementValuePairs()) {
                            buffer.appendAll(to2bytes(elementValuePair.getElementNameIndex()));
                            final var value = elementValuePair.getElementValue();
                            writeValue(value.getValue(), value.getTag());
                        }
                    }
                    break;
                }
                case SIGNATURE: {
                    buffer.appendAll(to2bytes(((SignatureAttribute) attribute).getSignatureIndex()));
                    break;
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
