package io.immortal.spicemustflow.domain.ingredient.event

import io.immortal.spicemustflow.domain.ingredient.Ingredient
import java.util.*

data class IngredientEventDto(
    val id: UUID,
    var name: String
) {
    constructor(ingredient: Ingredient) : this(ingredient.id, ingredient.name)
}

data class IngredientCreated(
    val ingredient: IngredientEventDto
)

data class IngredientUpdated(
    val ingredient: IngredientEventDto
)

data class IngredientDeleted(
    val ingredient: IngredientEventDto
)