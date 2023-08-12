package io.immortal.spicemustflow.domain.recipe

interface RecipeRepository {
    fun create(recipe: Recipe): Recipe
    fun update(recipe: Recipe): Recipe
    fun delete(id: RecipeId)
    fun delete(ids: List<RecipeId>)
    fun findById(id: RecipeId): Recipe?
    fun find(query: RecipeQuery): List<Recipe>
    fun generateId(): RecipeId
}