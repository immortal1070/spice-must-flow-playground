package io.immortal.spicemustflow.domain.ingredient

import java.util.*

data class IngredientId(
    val uuid: UUID
) {
//    @JsonCreator
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

//    @JsonValue
    override fun toString(): String {
        return uuid.toString()
    }
}