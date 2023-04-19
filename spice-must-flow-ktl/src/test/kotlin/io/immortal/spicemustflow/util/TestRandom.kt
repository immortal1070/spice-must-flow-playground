package io.immortal.spicemustflow.util

private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
private val defaultVarcharColumnSize = 200
class TestRandom {
    companion object {
        fun randomString(length: Int = defaultVarcharColumnSize): String {
            return List(length) { charPool.random() }.joinToString("")
        }
    }
}