package top.kkoishi.cw4j

import top.kkoishi.cv4j.Attribute_info
import top.kkoishi.cv4j.attr.*
import top.kkoishi.cv4j.attr.frames.AppendFrame
import top.kkoishi.cv4j.attr.frames.FullFrame
import top.kkoishi.cv4j.attr.frames.SameLocals1StackItemFrame
import top.kkoishi.cv4j.attr.frames.SameLocals1StackItemFrameExtended
import top.kkoishi.cv4j.attr.frames.verifi.VerificationTypeInfo
import java.io.InvalidClassException
import java.util.ArrayList

class ClassBytekode {
    internal companion object Utils {
        @JvmStatic
        internal fun countCodeAttributesLength(instance: CodeAttribute): Int {
            var c = 19 + instance.codeLength + 8 * instance.exceptionTableLength
            instance.attributes.forEach { c += countCodeAttributesLengthImpl(it) }
            return c
        }

        @JvmStatic
        private inline fun <reified T : Attribute_info> countCodeAttributesLengthImpl(instance: T): Int {
            return when (T::class) {
                LineNumberTableAttribute::class -> 8 + (instance as LineNumberTableAttribute).lineNumberTableLength * 4
                LocalVariableTableAttribute::class, LocalVariableTypeTableAttribute::class ->
                    8 + (instance as LocalVariableTableAttribute).localVariableTableLength * 10
                StackMapTableAttribute::class -> 8 + countStackMapFramesTotalLength((instance as StackMapTableAttribute).stackMapFrameEntries)
                else -> throw InvalidClassException("The KClass ${T::class} is not allowed here!")
            }
        }

        @JvmStatic
        @Suppress("MemberVisibilityCanBePrivate")
        internal fun countStackMapFramesTotalLength(stackMapFrameEntries: ArrayList<StackMapTableAttribute.StackMapFrame>): Int {
            var a: Int = 0
            stackMapFrameEntries.forEach { frame -> a += countStackMapFrameBytesLength(frame) }
            return a
        }

        @JvmStatic
        @Suppress("MemberVisibilityCanBePrivate", "NOTHING_TO_INLINE")
        internal inline fun countStackMapFrameBytesLength(frame: StackMapTableAttribute.StackMapFrame): Int {
            return when (frame.frameType.toInt().and(0xff)) {
                in 0..63 -> 1
                in 64..127 -> 1 + countVerificationTypeInfoLength((frame as SameLocals1StackItemFrame).stack)
                247 -> 3 + countVerificationTypeInfoLength((frame as SameLocals1StackItemFrameExtended).stack)
                in 248..251 -> 3
                in 252..254 -> {
                    var c = 3
                    (frame as AppendFrame).locals.forEach { c += countVerificationTypeInfoLength(it) }
                    c
                }
                else -> {
                    var c = 7
                    val cast = (frame as FullFrame)
                    cast.stack.forEach { c += countVerificationTypeInfoLength(it) }
                    cast.locals.forEach { c += countVerificationTypeInfoLength(it) }
                    c
                }
            }
        }

        @Suppress("NOTHING_TO_INLINE")
        private inline fun countVerificationTypeInfoLength(verification_type_info: VerificationTypeInfo): Int =
            when (verification_type_info.tag()) {
                in 0..6 -> 1
                else -> 3
            }
    }
}