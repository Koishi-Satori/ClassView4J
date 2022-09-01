package top.kkoishi.cv4j;

import sun.misc.Unsafe;

import java.util.*;
import java.util.stream.Collectors;

import static top.kkoishi.cv4j.ClassReader.toInt;

/**
 * Bytecodes static class of this asm4j frame.
 * <br>
 * For all the compare-use instruction, when value_1 > value_2, push 1 onto
 * the stack; and if value_1 == value_2, push 0 onto the stack;
 * or push -1 onto the stack.
 *
 * @author KKoishi_
 */
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "SpellCheckingInspection", "unused"})
public final class Bytecodes {

    static final Instruction[] jvm_instructions_array = new Instruction[256];

    static final HashMap<String, Instruction> jvm_instructions_map = new HashMap<>(4 * 256);

    static Unsafe getUnsafe () {
        try {
            final var f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static Unsafe U = getUnsafe();

    /**
     * Load onto a stack reference from an array.
     * <br>
     * Stack Status:array, int -> value
     * <br>
     * value = array[int]
     */
    public static final byte AALOAD = 0x32;

    /**
     * Store a value reference into an array.
     * <br>
     * Stack Status:array, int, value -> $empty
     * <br>
     * array[int] = value
     */
    public static final byte AASTORE = 0x53;

    /**
     * Push a null onto the stack.
     */
    public static final byte ACONST_NULL = 0x01;

    /**
     * Load a local variable onto the stack.
     * <br>
     * Other bytes:1 byte, the index from local_variable_table
     */
    public static final byte ALOAD = 0x19;

    /**
     * Load a reference onto the stack from local variable 0.
     */
    public static final byte ALOAD_0 = 0x2a;

    /**
     * Load a reference onto the stack from local variable 1.
     */
    public static final byte ALOAD_1 = 0x2b;

    /**
     * Load a reference onto the stack from local variable 2.
     */
    public static final byte ALOAD_2 = 0x2c;

    /**
     * Load a reference onto the stack from local variable 3.
     */
    public static final byte ALOAD_3 = 0x2d;

    /**
     * Other bytes:2 bytes. indexbyte1 and indexbyte2
     * <br>
     * Create a new array of references of length count and
     * component type identified by the class reference index
     * (indexbyte1 << 8 | indexbyte2) in the constant pool
     */
    public static final byte ANEWARRAY = (byte) 0xbd;

    /**
     * Return a reference from a method.
     * <br>
     * Stack Status:<code>object_ref -> [empty]</code>
     */
    public static final byte ARETURN = (byte) 0xb0;

    /**
     * Get the length of an array.
     * <br>
     * Stack Status:<code>array_ref -> length</code>
     */
    public static final byte ARRAYLENGTH = (byte) 0xbe;

    /**
     * Other bytes: 1 byte. index
     * <br>
     * Store a local_variable onto the local_variable_table
     * as the index one from the stack.
     */
    public static final byte ASTORE = 0x3a;

    /**
     * Store a reference into local variable 0
     */
    public static final byte ASTORE_0 = 0x4b;

    /**
     * Store a reference into local variable 1
     */
    public static final byte ASTORE_1 = 0x4c;

    /**
     * Store a reference into local variable 2
     */
    public static final byte ASTORE_2 = 0x4d;

    /**
     * Store a reference into local variable 3
     */
    public static final byte ASTORE_3 = 0x4e;

    /**
     * Throws an error or exception (notice that the
     * rest of the stack is cleared, leaving only a reference to
     * the Throwable)
     * <br>
     * Stack Status:
     * ..., ref(throwable)->ref
     */
    public static final byte ATHROW = (byte) 0xbf;

    /**
     * Load a byte or boolean value from an array.
     * <br>
     * Stack Status:array, int->value
     */
    public static final byte BALOAD = 0x33;

    /**
     * Store a byte or boolean value into an array.
     * <br>
     * Stack Status:array, int, value->[empty]
     */
    public static final byte BASTORE = 0x54;

    /**
     * Other bytes:1, byte value.
     * <br>
     * Push a byte value given after the instruction onto
     * the stack as an Integer value.
     * <br>
     * Stack Statue:[empty]->int_value
     */
    public static final byte BIPUSH = 0x10;

    /**
     * Load a char from an array onto the stack.
     * <br>
     * Stack Status:array, int -> value
     */
    public static final byte CALOAD = 0x34;

    /**
     * Store a char into the array.
     * <br>
     * Stack Status:array, int, value->[empty]
     */
    public static final byte CASTORE = 0x55;

    /**
     * Other bytes: 2bytes, can be converted to the index of cp.
     * <br>
     * Checks whether an object_ref is of a certain type, the
     * class reference of which is in the constant pool at index
     * (index_byte1 << 8 + index_byte2)
     * <br>
     * Stack Status:ref -> ref
     */
    public static final byte CHECKCAST = (byte) 0xc0;

    /**
     * Convert a double to a float.
     * <br>
     * Stack Status:value->result
     */
    public static final byte D2F = (byte) 0x90;

    /**
     * Convert a double to an integer.
     * <br>
     * Stack Status:value->result
     */
    public static final byte D2I = (byte) 0x8e;

    /**
     * Convert a double to a long.
     * <br>
     * Stack Status:value->result
     */
    public static final byte D2L = (byte) 0x8f;

    /**
     * Add two doubles.
     * <br>
     * Stack Status: double_0, double_1 -> result
     */
    public static final byte DADD = 0x63;

    /**
     * Load a double from an array onto the stack.
     * <br>
     * Stack Status:array, int -> value
     */
    public static final byte DALOAD = 0x31;

    /**
     * Store a double into the array.
     * <br>
     * Stack Status:array, int, value->[empty]
     */
    public static final byte DASTORE = 0x52;

    /**
     * Compare two doubles.
     * When one of double is NaN, push 1 to the stack.
     * <br>
     * Stack Status:value_1, value_2->result.
     */
    public static final byte DCMPG = (byte) 0x98;

    /**
     * Compare two doubles.
     * When one of double is NaN, push -1 to the stack.
     * <br>
     * Stack Status:value_1, value_2->result.
     */
    public static final byte DCMPL = (byte) 0x97;

    /**
     * Push the constant double 0.0 to the stack.
     * <br>
     * Stack Status:[empty]->0.0
     */
    public static final byte DCONST_0 = 0x0e;

    /**
     * Push the constant double 1.0 to the stack.
     * <br>
     * Stack Status:[empty]->1.0
     */
    public static final byte DCONST_1 = 0x0f;

    /**
     * Divide two doubles.
     * <br>
     * Stack Status:value_1, value_2->result
     */
    public static final byte DDIV = 0x6f;

    /**
     * Other bytes:1 bytes, index.
     * <br>
     * Load a double from local variable table.
     * <br>
     * Stack Status:[empty]->value
     */
    public static final byte DLOAD = 0x18;

    /**
     * Load a double from local variable table[0].
     * <br>
     * Stack Status:[empty]->value
     */
    public static final byte DLOAD_0 = 0x26;

    /**
     * Load a double from local variable table[1].
     * <br>
     * Stack Status:[empty]->value
     */
    public static final byte DLOAD_1 = 0x27;

    /**
     * Load a double from local variable table[2].
     * <br>
     * Stack Status:[empty]->value
     */
    public static final byte DLOAD_2 = 0x28;

    /**
     * Load a double from local variable table[3].
     * <br>
     * Stack Status:[empty]->value
     */
    public static final byte DLOAD_3 = 0x29;

    /**
     * Multiply two doubles.
     * <br>
     * Stack Status:value_1, value_2->result
     */
    public static final byte DMUL = 0x6b;

    /**
     * Negate the double value.
     * <br>
     * Stack Status:value->result
     */
    public static final byte DNEG = 0x77;

    /**
     * Get the remainder from the division between two doubles.
     * <br>
     * Stack Status:value_1, value_2->result
     */
    public static final byte DREM = 0x73;

    /**
     * Return a double from a method.
     * <br>
     * Stack Status:value->[empty]
     */
    public static final byte DRETURN = (byte) 0xaf;

    /**
     * Other bytes:1byte, index.
     * <br>
     * Store a double value to the local variable table #index
     * <br>
     * Stack Status:value->[empty]
     */
    public static final byte DSTORE = 0x39;

    /**
     * Store a double value to the local variable table #0
     * <br>
     * Stack Status:value->[empty]
     */
    public static final byte DSTORE_0 = 0x47;

    /**
     * Store a double value to the local variable table #1
     * <br>
     * Stack Status:value->[empty]
     */
    public static final byte DSTORE_1 = 0x48;

    /**
     * Store a double value to the local variable table #2
     * <br>
     * Stack Status:value->[empty]
     */
    public static final byte DSTORE_2 = 0x49;

    /**
     * Store a double value to the local variable table #3
     * <br>
     * Stack Status:value->[empty]
     */
    public static final byte DSTORE_3 = 0x4a;

    /**
     * Subtract a double from another.
     * <br>
     * Stack Status:value_1, value_2->result
     */
    public static final byte DSUB = 0x67;

    /**
     * Duplicate the top value of the stack.
     * <br>
     * Stack Status:value->value, value
     */
    public static final byte DUP = 0x59;

    /**
     * Insert a copy of the top value into the stack two values from
     * the top. value_1 and value_2 must not be of the type double
     * or long.
     * <br>
     * Stack Status:value_2, value_1->value_1, value_2, value_1
     */
    public static final byte DUP_X1 = 0x5a;

    /**
     * Insert a copy of the top value into the stack two (if value2
     * is double or long it takes up the entry of value3, too) or
     * three values (if value2 is neither double nor long) from the
     * top.
     * <br>
     * Stack Status:value_3, value_2, value_1->value_1, value_3,
     * value_2, value_1
     */
    public static final byte DUP_X2 = 0x5b;

    /**
     * Duplicate top two stack words (two values, if value1 is not
     * double nor long; a single value, if value1 is double or long).
     * <br>
     * Stack Status:<code>{value_1, value_2}->{value_1, value_2},
     * {value_1, value_2}</code>
     */
    public static final byte DUP2 = 0x5c;

    /**
     * Duplicate two words and insert beneath third word (see
     * explanation above).
     * <br>
     * Stack Status:<code>value_3, {value_1, value_2}->{value_1, value_2},
     * value_3, {value_1, value_2}</code>
     */
    public static final byte DUP2_X1 = 0x5d;

    /**
     * Duplicate two words and insert beneath fourth word.
     * <br>
     * Stack Status:<code>{value_4, value_3}, {value_2, value_1}->
     * {value_2, value_1}, {value_4, value_3}, {value_2, value_1}
     * </code>
     */
    public static final byte DUP2_X2 = 0x5e;

    /**
     * Convert a float to a double.
     * <br>
     * Stack Status:<code>value -> result</code>
     */
    public static final byte F2D = (byte) 0x8d;

    /**
     * Convert a float to an integer.
     * <br>
     * Stack Status:<code>value -> result</code>
     */
    public static final byte F2I = (byte) 0x8b;

    /**
     * Convert a float to a long.
     * <br>
     * Stack Status:<code>value -> result</code>
     */
    public static final byte F2L = (byte) 0x8c;

    /**
     * Add two floats.
     * <br>
     * Stack Status:<code>value_1, value_2 -> result</code>
     */
    public static final byte FADD = 0x62;

    /**
     * Load a float from an array.
     * <br>
     * Stack Status:<code>array_ref, index -> value</code>
     */
    public static final byte FALOAD = 0x30;

    /**
     * Store a float into an array.
     * <br>
     * Stack Status:<code>array_ref, index, value -> [empty]</code>
     */
    public static final byte FASTORE = 0x51;

    /**
     * Compare two floats.
     * When one of float is NaN, push 1 to the stack.
     * <br>
     * Stack Status:value_1, value_2 -> result.
     */
    public static final byte FCMPG = (byte) 0x96;

    /**
     * Compare two floats.
     * When one of float is NaN, push -1 to the stack.
     * <br>
     * Stack Status:<code>value_1, value_2 -> result.</code>
     */
    public static final byte FCMPL = (byte) 0x95;

    /**
     * Push float value 0.0f onto the stack.
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FCONST_0 = 0x0b;

    /**
     * Push float value 1.0f onto the stack.
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FCONST_1 = 0x0c;

    /**
     * Push float value 2.0f onto the stack.
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FCONST_2 = 0x0d;

    /**
     * Divide two floats.
     * <br>
     * Stack Status:<code>value_1, value_2 -> result</code>
     */
    public static final byte FDIV = 0x6e;

    /**
     * Other bytes:1, index to the local_variable_table.
     * <br>
     * Load a float value from local_variable_table #index.
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FLOAD = 0x17;

    /**
     * Load a float value from local_variable_table #0
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FLOAD_0 = 0x22;

    /**
     * Load a float value from local_variable_table #1
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FLOAD_1 = 0x23;

    /**
     * Load a float value from local_variable_table #2
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FLOAD_2 = 0x24;

    /**
     * Load a float value from local_variable_table #3
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte FLOAD_3 = 0x25;

    /**
     * Multiply two floats and push the result onto the stack.
     * <br>
     * Stack Status:<code>value_1, value_2 -> result</code>
     */
    public static final byte FMUL = 0x6a;

    /**
     * Negative a float.
     * <br>
     * Stack Status:<code>value -> result</code>
     */
    public static final byte FNEG = 0x76;

    /**
     * Get the remaining value form division of two floats.
     * <br>
     * Stack Status:<code>value_1, value_2 -> result</code>
     */
    public static final byte FREM = 0x72;

    /**
     * Return a float value.
     * <br>
     * Stack Status:<code>value -> [empty]</code>
     */
    public static final byte FRETURN = (byte) 0xae;

    /**
     * Other bytes:1, index of local_variable_table.
     * <br>
     * Store a float value into local_variable_table #index.
     * <br>
     * Stack Status:<code>value -> [empty]</code>
     */
    public static final byte FSTORE = 0x38;

    /**
     * Store a float value into local_variable_table #0.
     * <br>
     * Stack Status:<code>value -> [empty]</code>
     */
    public static final byte FSTORE_0 = 0x43;

    /**
     * Store a float value into local_variable_table #1.
     * <br>
     * Stack Status:<code>value -> [empty]</code>
     */
    public static final byte FSTORE_1 = 0x44;

    /**
     * Store a float value into local_variable_table #2.
     * <br>
     * Stack Status:<code>value -> [empty]</code>
     */
    public static final byte FSTORE_2 = 0x45;

    /**
     * Store a float value into local_variable_table #3.
     * <br>
     * Stack Status:<code>value -> [empty]</code>
     */
    public static final byte FSTORE_3 = 0x46;

    /**
     * Subtract two floats.
     * <br>
     * Stack Status:<code>value_1, value_2 -> result</code>
     */
    public static final byte FSUB = 0x66;

    /**
     * Other bytes:2bytes(b_1, b_2) represent index.
     * <br>
     * Get a field value of an object object_ref, where the field is identified
     * by the field_ref in the constant_pool index(b_1 << 8 + b_2).
     * <br>
     * Stack Status:<code>object_ref -> value</code>
     */
    public static final byte GETFIELD = (byte) 0xb4;

    /**
     * Other bytes:2bytes(b_1, b_2) represent index.
     * <br>
     * Get a static field value of a class , where the field is identified by
     * the field_ref in the constant_pool index(b_1 << 8 + b_2).
     * <br>
     * Stack Status:<code>[empty] -> value</code>
     */
    public static final byte GETSTATIC = (byte) 0xb2;

    /**
     * Other_bytes:2bytes(b_1, b_2) represent the pos jump to.
     * <br>
     * Goes to another instruction at index pos(b_1 << 8 + b_2).
     * <br>
     * Stack Status:None change.
     */
    public static final byte GOTO = (byte) 0xa7;

    /**
     * Other_bytes:4bytes(b_1...b_4) represent the pos jump to.
     * <br>
     * Goes to another instruction at index pos
     * (b_1 << 24 + b_2 << 16 + b_3 << 8 + b_4).
     * <br>
     * Stack Status:None change.
     */
    public static final byte GOTO_W = (byte) 0xc8;

    /**
     * Convert an int to byte.
     * <br>
     * Stack Status:value -> result
     */
    public static final byte I2B = (byte) 0x91;

    /**
     * Convert an int to char.
     * <br>
     * Stack Status:value -> result
     */
    public static final byte I2C = (byte) 0x92;

    /**
     * Convert an int to double.
     * <br>
     * Stack Status:value -> result
     */
    public static final byte I2D = (byte) 0x87;

    /**
     * Convert an int to float.
     * <br>
     * Stack Status:value -> result
     */
    public static final byte I2F = (byte) 0x86;

    /**
     * Convert an int to long.
     * <br>
     * Stack Status:value -> result
     */
    public static final byte I2L = (byte) 0x85;

    /**
     * Convert an int to short.
     * <br>
     * Stack Status:value -> result
     */
    public static final byte I2S = (byte) 0x93;


    public static final byte IADD = 0x60;

    public static final byte IALOAD = 0x2e;

    public static final byte IAND = 0x7e;

    public static final byte IASTORE = 0x4f;

    public static final byte ICONST_M1 = 0x02;

    public static final byte ICONST_0 = 0x03;

    public static final byte ICONST_1 = 0x04;

    public static final byte ICONST_2 = 0x05;

    public static final byte ICONST_3 = 0x06;

    public static final byte ICONST_4 = 0x07;

    public static final byte ICONST_5 = 0x08;

    public static final byte IDIV = 0x6c;

    public static final byte IF_ACMPEQ = (byte) 0xa5;

    public static final byte IF_ACMPNE = (byte) 0xa6;

    public static final byte IF_ICMPEQ = (byte) 0x9f;

    public static final byte IF_ICMPNE = (byte) 0xa0;

    public static final byte IF_ICMPLT = (byte) 0xa1;

    public static final byte IF_ICMPGE = (byte) 0xa2;

    public static final byte IF_ICMPGT = (byte) 0xa3;

    public static final byte IF_ICMPLE = (byte) 0xa4;

    public static final byte IFEQ = (byte) 0x99;

    public static final byte IFNE = (byte) 0x9a;

    public static final byte IFLT = (byte) 0x9b;

    public static final byte IFGE = (byte) 0x9c;

    public static final byte IFGT = (byte) 0x9d;

    public static final byte IFLE = (byte) 0x9e;

    public static final byte IFNONNULL = (byte) 0xc7;

    public static final byte IFNULL = (byte) 0xc6;

    public static final byte IINC = (byte) 0x84;

    public static final byte ILOAD = (byte) 0x15;

    public static final byte ILOAD_0 = 0x1a;

    public static final byte ILOAD_1 = 0x1b;

    public static final byte ILOAD_2 = 0x1c;

    public static final byte ILOAD_3 = 0x1d;

    public static final byte IMUL = 0x68;

    public static final byte INEG = 0x74;

    public static final byte INSTANCEOF = (byte) 0xc1;

    public static final byte INVOKEDYNAMIC = (byte) 0xba;

    public static final byte INVOKEINTERFACE = (byte) 0xb9;

    public static final byte INVOKESPECIAL = (byte) 0xb8;

    public static final byte INVOKESTATIC = (byte) 0xb7;

    public static final byte INVOKEVIRTUAL = (byte) 0xb6;

    public static final byte IOR = (byte) 0x80;

    public static final byte IREM = (byte) 0x70;

    public static final byte IRETURN = (byte) 0xac;

    public static final byte ISHL = (byte) 0x78;

    public static final byte ISHR = (byte) 0x7a;

    public static final byte ISTORE = 0x36;

    public static final byte ISTORE_0 = 0x3b;

    public static final byte ISTORE_1 = 0x3c;

    public static final byte ISTORE_2 = 0x3d;

    public static final byte ISTORE_3 = 0x3e;

    public static final byte ISUB = 0x64;

    public static final byte IUSHR = 0x7c;

    public static final byte IXOR = (byte) 0x82;

    public static final byte JSR = (byte) 0xa8;

    public static final byte JSR_W = (byte) 0xc9;

    public static final byte L2D = (byte) 0x8a;

    public static final byte L2F = (byte) 0x89;

    public static final byte L2I = (byte) 0x88;

    public static final byte LADD = 0x61;

    public static final byte LALOAD = 0x2f;

    public static final byte LAND = 0x7f;

    public static final byte LASRORE = 0x50;

    public static final byte LCMP = (byte) 0x94;

    public static final byte LCONST_0 = 0x09;

    public static final byte LCONST_1 = 0x0a;

    public static final byte LDC = 0x12;

    public static final byte LDC_W = 0x13;

    public static final byte LDC2_W = 0x14;

    public static final byte LDIV = 0x6d;

    public static final byte LLOAD = 0x16;

    public static final byte LLOAD_0 = 0x1e;

    public static final byte LLOAD_1 = 0x1f;

    public static final byte LLOAD_2 = 0x20;

    public static final byte LLOAD_3 = 0x21;

    public static final byte LMUL = 0x69;

    public static final byte LNEG = 0x75;

    public static final byte LOOKUPSWITCH = (byte) 0xab;

    public static final byte LOR = (byte) 0x81;

    public static final byte LREM = 0x71;

    public static final byte LRETURN = (byte) 0xad;

    public static final byte LSHL = 0x79;

    public static final byte LSHR = 0x7b;

    public static final byte LSTORE = 0x37;

    public static final byte LSTORE_0 = 0x3f;

    public static final byte LSTORE_1 = 0x40;

    public static final byte LSTORE_2 = 0x41;

    public static final byte LSTORE_3 = 0x42;

    public static final byte LSUB = 0x65;

    public static final byte LUSHR = 0x7d;

    public static final byte LXOR = (byte) 0x83;

    public static final byte MONITORENTER = (byte) 0xc3;

    public static final byte MONITOREXIT = (byte) 0xc2;

    public static final byte MULTIANEWARRAY = (byte) 0xc5;

    public static final byte NEW = (byte) 0xbb;

    public static final byte NEWARRAY = (byte) 0xbc;

    public static final byte NOP = 0x00;

    public static final byte POP = 0x57;

    public static final byte POP2 = 0x58;

    public static final byte PUTFIELD = (byte) 0xb5;

    public static final byte PUTSTATIC = (byte) 0xb3;

    public static final byte RET = (byte) 0xa9;

    public static final byte RETURN = (byte) 0xb1;

    public static final byte SALOAD = 0x35;

    public static final byte SASTORE = 0x56;

    public static final byte SIPUSH = 0x11;

    public static final byte SWAP = 0x5f;

    public static final byte TABLESWITCH = (byte) 0xaa;

    public static final byte WIDE = (byte) 0xc4;

    public static final byte BREAKPOINT = (byte) 0xca;

    public static final byte IMPDEP1 = (byte) 0xfe;

    public static final byte IMPDEP2 = (byte) 0xff;

    /**
     * Get instruction copy by name.
     *
     * @param name the name of the instruction.
     * @return copy of instruction.
     * @throws NoSuchElementException if no such instruction.
     */
    @SuppressWarnings("SameParameterValue")
    public static Instruction forName (String name) {
        final var ret = jvm_instructions_map.get(name.toUpperCase(Locale.ROOT));
        if (ret != null) {
            return ret.copy();
        }
        return error(name);
    }

    static Instruction error (String any) {
        throw new NoSuchElementException("There is no instruction called " + any);
    }

    /**
     * Get instruction copy by its code.
     *
     * @param instruction_code the code of the instruction.
     * @return copy of instruction.
     */
    public static Instruction forInstruction (byte instruction_code) {
        return jvm_instructions_array[instruction_code & 0xff];
    }

    public static int getJvm_instructions_count () {
        return (int) Arrays.stream(jvm_instructions_array).filter(Objects::nonNull).count();
    }

    public static Instruction[] getJvm_instructions_array () {
        return Arrays.stream(jvm_instructions_array).filter(Objects::nonNull).map(Instruction::copy).toArray(Instruction[]::new);
    }

    public static List<Instruction> getJvm_instructions_list () {
        return Arrays.stream(jvm_instructions_array).filter(Objects::nonNull).map(Instruction::copy).toList();
    }

    public static List<String> getJvm_instructions_nameList() {
        return Arrays.stream(jvm_instructions_array).filter(Objects::nonNull).map(Instruction::name).collect(Collectors.toList());
    }

    public static String[] getJvm_instructions_names() {
        return Arrays.stream(jvm_instructions_array).filter(Objects::nonNull).map(Instruction::name).toArray(String[]::new);
    }

    /**
     * Parse instructions to byte array_list.
     *
     * @param names instructions without other_bytes.
     * @return bytes.
     */
    public static ArrayList<Byte> parseInstructions (String... names) {
        final ArrayList<Byte> res = new ArrayList<>(names.length);
        Arrays.stream(names).map(Bytecodes::forName).map(Instruction::instruction).forEach(res::add);
        return res;
    }

    /**
     * Parse instruction and byte to byte array_list.
     *
     * @param code instructions.
     * @return bytes.
     */
    @SuppressWarnings("EnhancedSwitchMigration")
    public static ArrayList<Byte> parseByteInstructions (String... code) {
        final ArrayList<Byte> res = new ArrayList<>(code.length);
        final var iterator = Arrays.stream(code)
                .map(String::toUpperCase)
                .map(s -> s.replaceAll("^0X", ""))
                .iterator();
        Instruction instruction = null;
        while (iterator.hasNext()) {
            final var c = iterator.next();
            if (contains(c)) {
                instruction = forName(c);
                res.add(instruction.instruction);
                if (instruction.other_bytes > 0) {
                    for (int i = 0; i < instruction.other_bytes; i++) {
                        res.add(Byte.parseByte(iterator.next()));
                    }
                } else if (instruction.other_bytes < 0) {
                    int count;
                    final var temp = new byte[4];
                    // Calculate the bytes count of special instructions which
                    // have variable other_bytes count.
                    // The tableswitch and lookupswitch instruction should contain
                    // 0~3 bytes padding to make sure the start indexes of default_index,
                    // other indexes and offsets are the multiple of four.
                    //
                    // Tableswitch: default(u4, the default brance jump offset),
                    // high(u4), low(u4), offsets[high -low + 1]. Every offset
                    // contains jump_offset(u4).
                    //
                    // Lookupswitch: default(u4, the default brance jump offset),
                    // npairs_count(u4), npair[npairs_count]. Every npair contains
                    // switch_value and jump_offset(all in u4).
                    //
                    // Wide: opcode(u1) + other_bytes.
                    // opcode=iinc->4 other_bytes
                    // iload, fload, aload, lload, dload, istore, fstore, astore,
                    // lstore, dstore, or ret: 2 other_bytes
                    switch (instruction.instruction) {
                        case TABLESWITCH: {
                            count = 4 - res.size() % 4 + 4;
                            for (int i = 0; i < count; i++) {
                                res.add(Byte.parseByte(iterator.next()));
                            }
                            // Get high and low bits.
                            for (int i = 0; i < 4; i++) {
                                res.add(temp[i] = Byte.parseByte(iterator.next()));
                            }
                            count = toInt(temp) + 1;
                            for (int i = 0; i < 4; i++) {
                                res.add(temp[i] = Byte.parseByte(iterator.next()));
                            }
                            count = (count - toInt(temp)) * 4;
                            for (int i = 0; i < count; i++) {
                                res.add(Byte.parseByte(iterator.next()));
                            }
                            break;
                        }
                        case LOOKUPSWITCH: {
                            count = 4 - res.size() % 4 + 4;
                            for (int i = 0; i < count; i++) {
                                res.add(Byte.parseByte(iterator.next()));
                            }
                            // Get the npairs count
                            for (int i = 0; i < 4; i++) {
                                res.add(temp[i] = Byte.parseByte(iterator.next()));
                            }
                            count = toInt(temp) * 8;
                            for (int i = 0; i < count; i++) {
                                res.add(Byte.parseByte(iterator.next()));
                            }
                            break;
                        }
                        case WIDE: {
                            final var opcode = Byte.parseByte(iterator.next());
                            res.add(opcode);
                            count = opcode == IINC ? 4 : 2;
                            for (int i = 0; i < count; i++) {
                                res.add(Byte.parseByte(iterator.next()));
                            }
                            break;
                        }
                        default: throw new RuntimeException("This should not happen.");
                    }
                }
            } else {
                if (instruction == null) {
                    throw new IllegalArgumentException("The extended bytes should has its instruction.");
                } else {
                    try {
                        final int count = instruction.other_bytes;
                        for (int i = 0; i < count; i++) {
                            final var b = Integer.parseInt(iterator.next());
                            res.add((byte) b);
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("The input string must be instruction or index byte.");
                    }
                }
            }
        }
        return res;
    }

    public static boolean contains (String name) {
        return jvm_instructions_map.containsKey(name.toUpperCase(Locale.ROOT));
    }

    static {
        // Class init.
        // Init a temp-array to store other_bytes value.
        final byte B1 = 0x01;
        final byte B2 = 0x02;
        final byte B4 = 0x04;
        final var temp = new byte[256];
        setCount(temp,
                ALOAD, B1, ASTORE, B1, ANEWARRAY, B2, BIPUSH, B1, CHECKCAST, B2, DLOAD, B1, DSTORE, B1,
                FLOAD, B1, FSTORE, B2, GETFIELD, B2, GETSTATIC, B2, GOTO, B2, GOTO_W, B4, IF_ACMPEQ, B2,
                IF_ACMPNE, B2, IF_ICMPEQ, B2, IF_ICMPGE, B2, IF_ICMPGT, B2, IF_ICMPLE, B2, IF_ICMPLT, B2,
                IF_ICMPNE, B2, IFEQ, B2, IFGE, B2, IFGT, B2, IFLE, B2, IFLT, B2, IFNE, B2, IFNONNULL, B2,
                IFNULL, B2, IINC, B2, IALOAD, B1, INSTANCEOF, B2, INVOKEDYNAMIC, B4, INVOKEINTERFACE, B4,
                INVOKESPECIAL, B2, INVOKESTATIC, B2, INVOKEVIRTUAL, B2, ISTORE, B1, JSR, B2, JSR_W, B4,
                LDC, B1, LDC2_W, B2, LDC_W, B2, LLOAD, B1, LOOKUPSWITCH, (byte) -1, ISTORE, B1,
                MULTIANEWARRAY, (byte) 0x03, NEW, B2, NEWARRAY, B1, PUTFIELD, B2, PUTSTATIC, B2, RET, B1,
                SIPUSH, B2, TABLESWITCH, (byte) -1, WIDE, (byte) -1);
        // Use reflection to get all the "static byte" and
        // invoke Instruction::<init> method.
        try {
            for (final var f : Bytecodes.class.getDeclaredFields()) {
                // Check if the field is static.
                if ((f.getModifiers() & 0x08) != 0x00) {
                    // Check if type of the field is byte.
                    if (f.getType() == byte.class) {
                        // Use sun.misc.Unsafe to get value faster,
                        // then add the instruction.
                        final var val = (byte) f.get(null);
                        final var inst = new Instruction(val, f.getName(), temp[val & 0xff]);
                        jvm_instructions_array[val & 0xff] = inst;
                        jvm_instructions_map.put(inst.name, inst);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Can not init the class instructions in <cinit> method.\n"
                    + e.getLocalizedMessage());
            System.exit(114514);
        }
    }

    private static void setCount (byte[] arr, byte... instAndValues) {
        final var len = instAndValues.length;
        for (int i = 0; i < len; i++) {
            arr[instAndValues[i++] & 0xff] = instAndValues[i];
        }
    }

    private Bytecodes () {
        throw new IllegalArgumentException("This class should not be initialized.");
    }

    public static final class Instruction {
        final byte instruction;

        final String name;

        final int other_bytes;

        private Instruction (byte instruction, String name, int otherBytes) {
            if (jvm_instructions_array[instruction & 0xff] != null) {
                throw new ExceptionInInitializerError("The instruction" + jvm_instructions_array[instruction & 0xff] + "has existed.");
            }
            this.instruction = instruction;
            this.name = name;
            this.other_bytes = otherBytes;
        }

        public byte instruction () {
            return instruction;
        }

        public String name () {
            return name;
        }

        public int otherBytes () {
            return other_bytes;
        }

        private Instruction copy () {
            try {
                final var cpy = (Instruction) U.allocateInstance(Instruction.class);
                for (final var f : Instruction.class.getDeclaredFields()) {
                    U.getAndSetObject(cpy, U.objectFieldOffset(f), f.get(this));
                }
                return cpy;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new ExceptionInInitializerError();
            }
        }

        @Override
        public boolean equals (Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof final Instruction that)) {
                return false;
            }
            return instruction == that.instruction && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode () {
            return Objects.hash(instruction, name);
        }

        @Override
        public String toString () {
            return "Instruction{" +
                    "instruction=" + instruction +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
