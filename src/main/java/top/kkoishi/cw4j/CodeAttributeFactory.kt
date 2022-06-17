package top.kkoishi.cw4j

import sun.misc.Unsafe
import top.kkoishi.cv4j.Bytecodes
import top.kkoishi.cv4j.attr.CodeAttribute

@Suppress("NOTHING_TO_INLINE")
abstract class CodeAttributeFactory internal constructor(
    private val max_stack: Int,
    private val max_locals: Int,
    private val instructions: ArrayDeque<Bytecodes.Instruction>,
    private val code_exceptions: ArrayDeque<CodeAttribute.CodeException>,
    private val code_attributes: ArrayDeque<CodeAttribute>,
) {
    companion object {
        @JvmStatic
        private inline fun getUnsafe(): Unsafe {
            val f = Unsafe::class.java.getDeclaredField("theUnsafe")
            f.isAccessible = true
            return f[null] as Unsafe
        }

        @JvmStatic
        internal val U = getUnsafe()

        @JvmStatic
        private inline fun accessOffset(name: String): Long =
            U.objectFieldOffset(CodeAttributeFactory::class.java.getDeclaredField(name))

        @JvmStatic
        private val offset_max_stack = accessOffset("max_stack")

        @JvmStatic
        private val offset_max_locals = accessOffset("max_locals")
    }

    fun setMaxStack(max_stack: Int) = U.getAndSetInt(this, offset_max_stack, max_stack)

    fun setMaxLocals(max_locals: Int) = U.getAndSetInt(this, offset_max_locals, max_locals)

    fun maxStack() = max_stack

    fun maxLocals() = max_locals

    fun addInstruction(instruction: Bytecodes.Instruction, vararg other_bytes: Byte): CodeAttributeFactory {
        if (other_bytes.size != instruction.otherBytes()) {
            throw IllegalArgumentException("The other_byte_length should be ${instruction.otherBytes()}, but got:${other_bytes.size}")
        }
        instructions.addLast(instruction)
        return this
    }

    fun setInstruction (index: Int, instruction: Bytecodes.Instruction, vararg other_bytes: Byte) {

    }
}