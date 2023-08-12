package io.immortal.spicemustflow.domain.ingredient

data class IngredientEventDto(
    val id: IngredientId,
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