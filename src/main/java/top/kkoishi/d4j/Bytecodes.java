package top.kkoishi.d4j;

import java.util.Scanner;

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
}
