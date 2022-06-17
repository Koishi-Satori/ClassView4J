package top.kkoishi.cv4j;

public class DecompilerException extends Exception {
    public DecompilerException () {
    }

    public DecompilerException (String message) {
        super(message);
    }

    public DecompilerException (String message, Throwable cause) {
        super(message, cause);
    }

    public DecompilerException (Throwable cause) {
        super(cause);
    }
}
