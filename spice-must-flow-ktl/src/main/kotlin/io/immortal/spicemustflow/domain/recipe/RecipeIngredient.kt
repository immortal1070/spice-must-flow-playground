package io.immortal.spicemustflow.domain.recipe

import io.immortal.spicemustflow.domain.ingredient.IngredientId

data class RecipeIngredient(
    val ingredientId: IngredientId,
    val amount: Double
)