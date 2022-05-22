package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public final class SourceDebugExtensionAttribute extends AttributeInfo {
    /**
     * The length must be equal to attribute length!
     */
    private final byte[] debugExtensionTable;

    public SourceDebugExtensionAttribute (int attributeNameIndex, int attributeLength, byte[] debugExtensionTable) {
        super(attributeNameIndex, attributeLength);
        this.debugExtensionTable = debugExtensionTable;
    }

    public byte[] getDebugExtensionTable () {
        return debugExtensionTable;
    }

    @Override
    public String toString () {
        return "SourceDebugExtensionAttribute{" +
                "debugExtensionTable=" + Arrays.toString(debugExtensionTable) +
                '}';
    }
}
