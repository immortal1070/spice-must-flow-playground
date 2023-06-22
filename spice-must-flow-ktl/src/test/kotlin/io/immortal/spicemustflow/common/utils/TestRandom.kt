package io.immortal.spicemustflow.common.utils

import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE


private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
private val defaultVarcharColumnSize = DEFAULT_STRING_SIZE

class TestRandom {
    companion object {
        fun randomString(length: Int = defaultVarcharColumnSize): String {
            return List(length) { charPool.random() }.joinToString("")
        }
    }
}