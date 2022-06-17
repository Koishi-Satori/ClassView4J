package top.kkoishi.cw4j

import top.kkoishi.cv4j.ConstPoolInfo

open class ClassWriter() {
    protected val constant_pool: ArrayDeque<ConstPoolInfo> = ArrayDeque(32)

}