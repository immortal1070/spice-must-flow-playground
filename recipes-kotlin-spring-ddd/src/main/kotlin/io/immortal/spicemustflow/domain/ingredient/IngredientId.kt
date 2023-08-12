package io.immortal.spicemustflow.domain.ingredient

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Id of the ingredient used in the recipe", example = "2e2c2edd-c274-4eb4-9510-25f05185b68e")
data class IngredientId(
    val uuid: UUID
) {
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    override fun toString(): String {
        return uuid.toString()
    }
}