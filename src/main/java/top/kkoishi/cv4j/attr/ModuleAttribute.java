package top.kkoishi.cv4j.attr;

import top.kkoishi.cv4j.Attribute_info;

import java.util.Arrays;

public final class ModuleAttribute extends Attribute_info {
    private final int moduleNameIndex;
    private final int moduleFlags;
    private final int moduleVersionIndex;

    private final int requiresCount;
    private final Require[] requires;
    private final int exportsCount;
    private final Export[] exports;
    private final int opensCount;
    private final Open[] opens;
    private final int usesCount;
    private final int[] uses;
    private final int providesCount;
    private final Provide[] provides;

    public ModuleAttribute (int attributeNameIndex,
                            int attributeLength,
                            int moduleNameIndex,
                            int moduleFlags,
                            int moduleVersionIndex,
                            int requiresCount,
                            Require[] requires,
                            int exportsCount,
                            Export[] exports,
                            int opensCount,
                            Open[] opens,
                            int usesCount,
                            int[] uses,
                            int providesCount,
                            Provide[] provides) {
        super(attributeNameIndex, attributeLength);
        this.moduleNameIndex = moduleNameIndex;
        this.moduleFlags = moduleFlags;
        this.moduleVersionIndex = moduleVersionIndex;
        this.requiresCount = requiresCount;
        this.requires = requires;
        this.exportsCount = exportsCount;
        this.exports = exports;
        this.opensCount = opensCount;
        this.opens = opens;
        this.usesCount = usesCount;
        this.uses = uses;
        this.providesCount = providesCount;
        this.provides = provides;
    }

    public int getModuleNameIndex () {
        return moduleNameIndex;
    }

    public int getModuleFlags () {
        return moduleFlags;
    }

    public int getModuleVersionIndex () {
        return moduleVersionIndex;
    }

    public int getRequiresCount () {
        return requiresCount;
    }

    public Require[] getRequires () {
        return requires;
    }

    public int getExportsCount () {
        return exportsCount;
    }

    public Export[] getExports () {
        return exports;
    }

    public int getOpensCount () {
        return opensCount;
    }

    public Open[] getOpens () {
        return opens;
    }

    public int getUsesCount () {
        return usesCount;
    }

    public int[] getUses () {
        return uses;
    }

    public int getProvidesCount () {
        return providesCount;
    }

    public Provide[] getProvides () {
        return provides;
    }

    @Override
    public String toString () {
        return "ModuleAttribute{" +
                "attributeNameIndex=" + attributeNameIndex +
                ", attributeLength=" + attributeLength +
                ", moduleNameIndex=" + moduleNameIndex +
                ", moduleFlags=" + moduleFlags +
                ", moduleVersionIndex=" + moduleVersionIndex +
                ", requiresCount=" + requiresCount +
                ", requires=" + Arrays.toString(requires) +
                ", exportsCount=" + exportsCount +
                ", exports=" + Arrays.deepToString(exports) +
                ", opensCount=" + opensCount +
                ", opens=" + Arrays.deepToString(opens) +
                ", usesCount=" + usesCount +
                ", uses=" + Arrays.toString(uses) +
                ", providesCount=" + providesCount +
                ", provides=" + Arrays.deepToString(provides) +
                '}';
    }

    public static final class Require {
        private final int requiresIndex;
        private final int requiresFlags;
        private final int requiresVersionIndex;

        public Require (int requiresIndex, int requiresFlags, int requiresVersionIndex) {
            this.requiresIndex = requiresIndex;
            this.requiresFlags = requiresFlags;
            this.requiresVersionIndex = requiresVersionIndex;
        }

        public int getRequiresIndex () {
            return requiresIndex;
        }

        public int getRequiresFlags () {
            return requiresFlags;
        }

        public int getRequiresVersionIndex () {
            return requiresVersionIndex;
        }

        @Override
        public String toString () {
            return "Require{" +
                    "requiresIndex=" + requiresIndex +
                    ", requiresFlags=" + requiresFlags +
                    ", requiresVersionIndex=" + requiresVersionIndex +
                    '}';
        }
    }

    public static final class Export {
        private final int exportsIndex;
        private final int exportsFlags;
        private final int exportsToCount;
        private final int[] exportsToIndexes;

        public Export (int exportsIndex, int exportsFlags, int exportsToCount, int[] exportsToIndexes) {
            this.exportsIndex = exportsIndex;
            this.exportsFlags = exportsFlags;
            this.exportsToCount = exportsToCount;
            this.exportsToIndexes = exportsToIndexes;
        }

        public int getExportsIndex () {
            return exportsIndex;
        }

        public int getExportsFlags () {
            return exportsFlags;
        }

        public int getExportsToCount () {
            return exportsToCount;
        }

        public int[] getExportsToIndexes () {
            return exportsToIndexes;
        }

        @Override
        public String toString () {
            return "Export{" +
                    "exportsIndex=" + exportsIndex +
                    ", exportsFlags=" + exportsFlags +
                    ", exportsToCount=" + exportsToCount +
                    ", exportsToIndexes=" + Arrays.toString(exportsToIndexes) +
                    '}';
        }
    }

    public static final class Open {
        private final int opensIndex;
        private final int opensFlags;
        private final int opensToCount;
        private final int[] opensToIndexes;

        public Open (int opensIndex, int opensFlags, int opensToCount, int[] opensToIndexes) {
            this.opensIndex = opensIndex;
            this.opensFlags = opensFlags;
            this.opensToCount = opensToCount;
            this.opensToIndexes = opensToIndexes;
        }

        public int getOpensIndex () {
            return opensIndex;
        }

        public int getOpensFlags () {
            return opensFlags;
        }

        public int getOpensToCount () {
            return opensToCount;
        }

        public int[] getOpensToIndexes () {
            return opensToIndexes;
        }

        @Override
        public String toString () {
            return "Open{" +
                    "opensIndex=" + opensIndex +
                    ", opensFlags=" + opensFlags +
                    ", opensToCount=" + opensToCount +
                    ", opensToIndexes=" + Arrays.toString(opensToIndexes) +
                    '}';
        }
    }

    public static final class Provide {
        private final int providesIndex;
        private final int providesCount;
        private final int[] provides;

        public Provide (int providesIndex, int providesCount, int[] provides) {
            this.providesIndex = providesIndex;
            this.providesCount = providesCount;
            this.provides = provides;
        }

        public int getProvidesIndex () {
            return providesIndex;
        }

        public int getProvidesCount () {
            return providesCount;
        }

        public int[] getProvides () {
            return provides;
        }

        @Override
        public String toString () {
            return "Provide{" +
                    "providesIndex=" + providesIndex +
                    ", providesCount=" + providesCount +
                    ", provides=" + Arrays.toString(provides) +
                    '}';
        }
    }
}
