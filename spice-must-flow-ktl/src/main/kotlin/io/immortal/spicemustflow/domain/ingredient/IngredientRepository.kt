package io.immortal.spicemustflow.domain.ingredient

interface IngredientRepository {
//    fun create(saveCommand: IngredientRepoSaveCommand): Ingredient
//    fun update(id: IngredientId, saveCommand: IngredientRepoSaveCommand): Ingredient
    fun save(ingredient: Ingredient): Ingredient
    fun delete(id: IngredientId)
    fun delete(ids: List<IngredientId>)
    fun findById(id: IngredientId): Ingredient?
    fun find(query: IngredientQuery): List<Ingredient>
    fun generateId(): IngredientId
}