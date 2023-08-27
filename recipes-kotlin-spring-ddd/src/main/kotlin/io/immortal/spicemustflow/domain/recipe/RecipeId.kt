package io.immortal.spicemustflow.domain.recipe

import java.util.*

data class RecipeId(
    val uuid: UUID
) {
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    override fun toString(): String {
        return uuid.toString()
    }
}