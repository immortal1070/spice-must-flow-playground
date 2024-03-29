package io.immortal.spicemustflow.common.utils

import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE
import java.math.BigDecimal
import kotlin.random.Random


private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
private val defaultVarcharColumnSize = DEFAULT_STRING_SIZE

class TestRandom {
    companion object {
        fun randomString(length: Int = defaultVarcharColumnSize): String {
            return List(length) { charPool.random() }.joinToString("")
        }

        fun randomInt(max: Int = 10000): Int {
            return Random.nextInt(max)
        }

        fun randomBigDecimal(max: Double = 10000.0): BigDecimal {
            return BigDecimal(Random.nextDouble(max))
        }
    }
}