package top.kkoishi.d4j;

public final class Bytecodes {
    private Bytecodes () {
        throw new IllegalArgumentException("FUCK YOU!");
    }

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
    public static final byte ALODA_0 = 0x2a;

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
    public static final byte ASTORE_2 = 0x0d;

    /**
     * Store a reference into local variable 3
     */
    public static final byte ASTORE_3 = 0x0e;

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
    public static final byte D2I = (byte) 0x8c;

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
    public static final byte DUML = 0x6b;

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
}
