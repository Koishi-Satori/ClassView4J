package top.kkoishi.cw4j

import sun.misc.Unsafe
import top.kkoishi.cv4j.Attribute_info
import top.kkoishi.cv4j.Bytecodes
import top.kkoishi.cv4j.attr.CodeAttribute
import java.lang.invoke.MethodHandles

infix fun Bytecodes.Instruction.combine(other_bytes: ByteArray) = FullInstruction(this, other_bytes)

data class FullInstruction(val instruction: Bytecodes.Instruction, val other_bytes: ByteArray) {
    init {
        if (instruction.otherBytes() != other_bytes.size) {
            throw IllegalArgumentException()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FullInstruction) return false
        if (instruction != other.instruction) return false
        if (!other_bytes.contentEquals(other.other_bytes)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = instruction.hashCode()
        result = 31 * result + other_bytes.contentHashCode()
        return result
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun length() = instruction.otherBytes() + 1
}

@Suppress("NOTHING_TO_INLINE")
abstract class CodeAttributeBuilder internal constructor(
    private val max_stack: Int,
    private val max_locals: Int,
    private val instructions: ArrayDeque<FullInstruction>,
    private val code_exceptions: ArrayDeque<CodeAttribute.CodeException>,
    private val code_attributes: ArrayDeque<Attribute_info>,
) {
    @Suppress("MemberVisibilityCanBePrivate")
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
        @Suppress("unused")
        internal val L = accessLookup(CodeAttributeBuilder::class.java)

        @JvmStatic
        internal inline fun accessLookup(callerClass: Class<*>): MethodHandles.Lookup {
            val constructor = MethodHandles.Lookup::class.java.getDeclaredConstructor(Class::class.java)
            constructor.isAccessible = true
            return constructor.newInstance(callerClass)
        }

        @JvmStatic
        private inline fun accessOffset(name: String): Long =
            U.objectFieldOffset(CodeAttributeBuilder::class.java.getDeclaredField(name))

        @JvmStatic
        private val offset_max_stack = accessOffset("max_stack")

        @JvmStatic
        private val offset_max_locals = accessOffset("max_locals")

        /**
         * Get default implement of CodeAttributeFactory.
         *
         * @param name_Index the index of "CodeAttribute" in Constant_pool.
         * @param max_stack the max stack size of the code attribute
         * @param max_locals the max local_variable_table size of the code attribute
         * @return instance
         */
        @JvmOverloads
        @JvmStatic
        fun getInstance(
            name_Index: Int,
            max_stack: Int,
            max_locals: Int,
            instructions: ArrayDeque<FullInstruction> = ArrayDeque(),
            code_exceptions: ArrayDeque<CodeAttribute.CodeException> = ArrayDeque(),
            code_attributes: ArrayDeque<Attribute_info> = ArrayDeque(),
        ) =
            object : CodeAttributeBuilder(max_stack, max_locals, instructions, code_exceptions, code_attributes) {
                override fun attributeIndex(): Int = name_Index
            }
    }

    fun setMaxStack(max_stack: Int) = U.compareAndSwapInt(this, offset_max_stack, this.max_stack, max_stack)

    fun setMaxLocals(max_locals: Int) = U.compareAndSwapInt(this, offset_max_locals, this.max_locals, max_locals)

    fun maxStack() = max_stack

    fun maxLocals() = max_locals

    fun addInstruction(instruction: FullInstruction): CodeAttributeBuilder {
        this.instructions.add(instruction)
        return this
    }

    inline fun addInstruction(instruction: Bytecodes.Instruction, other_bytes: ByteArray) = addInstruction(
        FullInstruction(instruction, other_bytes))

    fun setInstruction(index: Int, instruction: FullInstruction): CodeAttributeBuilder {
        this.instructions[index] = instruction
        return this
    }

    inline fun setInstruction(index: Int, instruction: Bytecodes.Instruction, other_bytes: ByteArray) =
        setInstruction(index, FullInstruction(instruction, other_bytes))

    fun addCodeExceptions(code_exception: CodeAttribute.CodeException): CodeAttributeBuilder {
        this.code_exceptions.add(code_exception)
        return this
    }

    fun setCodeExceptions(index: Int, code_exception: CodeAttribute.CodeException): CodeAttributeBuilder {
        this.code_exceptions[index] = code_exception
        return this
    }

    fun addCodeAttributes(code_attribute: Attribute_info): CodeAttributeBuilder {
        this.code_attributes.add(code_attribute)
        return this
    }

    fun setCodeAttributes(index: Int, code_attribute: Attribute_info): CodeAttributeBuilder {
        this.code_attributes[index] = code_attribute
        return this
    }

    internal abstract fun attributeIndex(): Int

    @Suppress("UNCHECKED_CAST")
    fun build(): CodeAttribute {
        val codes = ArrayList<Byte>((instructions.size * 1.5).toInt())
        for (instruction in instructions) {
            codes.add(instruction.instruction.instruction())
            instruction.other_bytes.forEach { codes.add(it) }
        }
        val exceptions = ArrayList<CodeAttribute.CodeException>(code_exceptions.size)
        code_exceptions.forEach { exceptions.add(it) }
        val attributes = code_attributes.toArray() as Array<Attribute_info>
        val code_attribute = CodeAttribute(
            attributeIndex(),
            0,
            max_stack,
            max_locals,
            instructions.size,
            codes,
            code_exceptions.size,
            exceptions,
            code_attributes.size,
            attributes)
        code_attribute.attributeLength = ClassBytekode.countCodeAttributesLength(code_attribute)
        return code_attribute
    }

    fun close() {
        val expect_max_stack = max_stack
        val expect_max_local = max_locals
        U.compareAndSwapInt(this, offset_max_stack, expect_max_stack, 0)
        U.compareAndSwapInt(this, offset_max_locals, expect_max_local, 0)
        instructions.clear()
        code_exceptions.clear()
        code_attributes.clear()
    }

    fun autoCloseBuild(): CodeAttribute {
        val codes = ArrayList<Byte>((instructions.size * 1.5).toInt())
        while (instructions.isNotEmpty()) {
            val rev = instructions.removeFirst()
            codes.add(rev.instruction.instruction())
            rev.other_bytes.forEach(codes::add)
        }
        val exceptions = ArrayList<CodeAttribute.CodeException>(code_exceptions.size)
        while (code_exceptions.isNotEmpty()) {
            exceptions.add(code_exceptions.removeFirst())
        }
        @Suppress("UNCHECKED_CAST") val attributes = code_attributes.toArray() as Array<Attribute_info>
        code_attributes.clear()
        val code_attribute = CodeAttribute(
            attributeIndex(),
            0,
            max_stack,
            max_locals,
            codes.size,
            codes,
            exceptions.size,
            exceptions,
            attributes.size,
            attributes
        )
        code_attribute.attributeLength = ClassBytekode.Utils.countCodeAttributesLength(code_attribute)
        return code_attribute
    }
}