package io.immortal.spicemustflow.web.resources.recipe.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import io.immortal.spicemustflow.domain.recipe.RecipeId
import java.util.*

data class RecipeRestId(
    @JsonValue
    val uuid: UUID
) {
    @JsonCreator
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    constructor(recipeId: RecipeId) : this(recipeId.uuid)

    override fun toString(): String {
        return uuid.toString()
    }

    fun toRecipeId(): RecipeId {
        return RecipeId(uuid)
    }
}