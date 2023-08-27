package io.immortal.spicemustflow.domain.recipe

data class RecipeQuery(
    val ids: List<RecipeId>? = null,
    val names: List<String>? = null,
    val content: List<String>? = null,
    val cookingMinutes: List<Int>? = null,
    val ingredientNames: List<String>? = null
)