package io.immortal.spicemustflow.domain.recipe

data class RecipeEventDto(
    val id: RecipeId,
    var name: String,
    var content: String,
    var cookingMinutes: Int,
    var ingredients: List<RecipeIngredient>
) {
    constructor(recipe: Recipe) : this(
        recipe.id,
        recipe.name,
        recipe.content,
        recipe.cookingMinutes,
        recipe.ingredients
    )
}

data class RecipeCreated(
    val recipe: RecipeEventDto
)

data class RecipeUpdated(
    val recipe: RecipeEventDto
)

data class RecipeDeleted(
    val recipe: RecipeEventDto
)