package io.immortal.spicemustflow.domain.recipe

import java.util.*

interface RecipeRepository {
    fun findById(id: UUID): Recipe?
    fun findByName(name: String?): Recipe?
    fun create(name: String): Recipe
}