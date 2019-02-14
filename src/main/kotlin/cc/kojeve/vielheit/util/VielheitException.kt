package cc.kojeve.vielheit.util

import java.lang.RuntimeException

open class VielheitException(override val message: String) : RuntimeException()