package io.immortal.spicemustflow.infrastructure.ingredient

import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import org.springframework.stereotype.Component

@Component
class IngredientJpaTransformer {
    fun toIngredient(entity: IngredientJpaEntity): Ingredient = entity.run {
        val entityId = id
        if (entityId == null) {
            throw IllegalStateException("Provide entity id before converting to aggregate!")
        }
        Ingredient(
            id = IngredientId(entityId),
            name = name
        )
    }

    fun toJpaEntity(ingredient: Ingredient): IngredientJpaEntity = ingredient.run {
        val entity = IngredientJpaEntity(ingredient.name)
        entity.id = ingredient.id.uuid
        entity
    }
}