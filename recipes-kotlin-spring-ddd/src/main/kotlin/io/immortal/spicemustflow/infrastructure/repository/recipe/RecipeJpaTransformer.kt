package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.recipe.Recipe
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.domain.recipe.RecipeIngredient

@ApplicationScoped
class RecipeJpaTransformer {
    fun toJpaEntity(recipe: Recipe): RecipeJpaEntity = recipe.run {
        val entity = RecipeJpaEntity(
            recipe.name,
            recipe.content,
            recipe.cookingMinutes
        )
        entity.id = recipe.id.uuid
        entity
    }
}

fun RecipeJpaEntity.toDomainObject(): Recipe {
    val entityId = id
    if (entityId == null) {
        throw IllegalStateException("Provide entity id before converting to aggregate!")
    }
    return Recipe(
        id = RecipeId(entityId),
        name = name,
        content = content,
        cookingMinutes = cookingMinutes,
        //TODO lazy initialization - fix with a separate call for list results
        ingredients = ingredients?.map { RecipeIngredient(IngredientId(it.id.ingredientId), it.amount) } ?: listOf()
    )
}