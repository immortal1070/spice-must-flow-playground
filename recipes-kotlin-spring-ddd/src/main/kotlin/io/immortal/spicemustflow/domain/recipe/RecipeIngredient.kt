package io.immortal.spicemustflow.domain.recipe

import io.immortal.spicemustflow.domain.ingredient.IngredientId
import java.math.BigDecimal

data class RecipeIngredient(
    val ingredientId: IngredientId,
    val amount: BigDecimal
)