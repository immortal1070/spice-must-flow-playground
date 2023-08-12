package io.immortal.spicemustflow.domain.recipe

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Id of the recipe", example = "2e2c2edd-c274-4eb4-9510-25f05185b68e")
data class RecipeId(
    val uuid: UUID
) {
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    override fun toString(): String {
        return uuid.toString()
    }
}