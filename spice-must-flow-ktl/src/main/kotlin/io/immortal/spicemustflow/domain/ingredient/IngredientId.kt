package io.immortal.spicemustflow.domain.ingredient

import java.util.*

data class IngredientId(
    val uuid: UUID
) {
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    override fun toString(): String {
        return uuid.toString()
    }
}