package io.immortal.spicemustflow.domain.ingredient

interface IngredientRepository {
//    fun create(saveCommand: IngredientSaveCommand): Ingredient
//    fun update(id: UUID, saveCommand: IngredientSaveCommand): Ingredient
    fun save(ingredient: Ingredient): Ingredient
    fun delete(id: IngredientId)
    fun delete(ids: List<IngredientId>)
    fun findById(id: IngredientId): Ingredient?
    fun find(query: IngredientQuery): List<Ingredient>
    fun generateId(): IngredientId
}