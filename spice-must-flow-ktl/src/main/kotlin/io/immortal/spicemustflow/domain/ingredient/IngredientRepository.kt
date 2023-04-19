package io.immortal.spicemustflow.domain.ingredient

import java.util.*

interface IngredientRepository {
    fun create(name: String): Ingredient
    fun update(id: UUID, name: String): Ingredient
    fun delete(id: UUID)
    fun delete(ids: List<UUID>)
    fun findById(id: UUID): Ingredient?
    fun find(params: IngredientFindParams): List<Ingredient>
}