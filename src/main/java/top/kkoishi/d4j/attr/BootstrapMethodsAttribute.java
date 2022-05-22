package top.kkoishi.d4j.attr;

import top.kkoishi.d4j.AttributeInfo;

import java.util.Arrays;

public final class BootstrapMethodsAttribute extends AttributeInfo {
    private final int numBootstrapMethods;
    private final BootstrapMethod[] bootstrapMethods;

    public BootstrapMethodsAttribute (int attributeNameIndex, int attributeLength, int numBootstrapMethods, BootstrapMethod[] bootstrapMethods) {
        super(attributeNameIndex, attributeLength);
        this.numBootstrapMethods = numBootstrapMethods;
        this.bootstrapMethods = bootstrapMethods;
    }

    public int getNumBootstrapMethods () {
        return numBootstrapMethods;
    }

    @Override
    public String toString () {
        return "BootstrapMethodsAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", numBootstrapMethods=" + numBootstrapMethods +
                ", bootstrapMethods=" + Arrays.toString(bootstrapMethods) +
                '}';
    }

    public BootstrapMethod[] getBootstrapMethods () {
        return bootstrapMethods;
    }

    public static final class BootstrapMethod {
        /**
         * The value of the bootstrap_method_ref item must be
         * a valid index into the constant_pool table.
         * The constant_pool entry at that index must be a
         * CONSTANT_MethodHandle_info structure
         */
        private final int bootstrapMethodRef;

        /**
         * The value of the num_bootstrap_arguments item
         * gives the number of items in the bootstrap_arguments
         * array.
         */
        private final int numBootstrapArguments;

        /**
         * Each entry in the bootstrap_arguments array must be a
         * valid index into the constant_pool table. The
         * constant_pool entry at that index must be loadable.
         */
        private final int[] bootStrapArguments;

        public BootstrapMethod (int bootstrapMethodRef, int numBootstrapArguments, int[] bootStrapArguments) {
            this.bootstrapMethodRef = bootstrapMethodRef;
            this.numBootstrapArguments = numBootstrapArguments;
            this.bootStrapArguments = bootStrapArguments;
        }

        public int getBootstrapMethodRef () {
            return bootstrapMethodRef;
        }

        public int getNumBootstrapArguments () {
            return numBootstrapArguments;
        }

        public int[] getBootStrapArguments () {
            return bootStrapArguments;
        }

        @Override
        public String toString () {
            return "BootstrapMethod{" +
                    "bootstrapMethodRef=" + bootstrapMethodRef +
                    ", numBootstrapArguments=" + numBootstrapArguments +
                    ", bootStrapArguments=" + Arrays.toString(bootStrapArguments) +
                    '}';
        }
    }
}
