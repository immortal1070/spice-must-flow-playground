package io.immortal.spicemustflow.domain.recipe

data class Recipe(
    val id: RecipeId,
    var name: String,
    var content: String,
    var cookingMinutes: Int,
    var ingredients: List<RecipeIngredient>
)