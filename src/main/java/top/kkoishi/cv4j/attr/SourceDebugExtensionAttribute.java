package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class SourceDebugExtensionAttribute extends Attribute_info {
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
