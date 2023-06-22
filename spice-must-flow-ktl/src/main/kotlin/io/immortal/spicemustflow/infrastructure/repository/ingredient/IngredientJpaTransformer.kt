package io.immortal.spicemustflow.infrastructure.repository.ingredient

import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientId

@ApplicationScoped
class IngredientJpaTransformer {
    fun toJpaEntity(ingredient: Ingredient): IngredientJpaEntity = ingredient.run {
        val entity = IngredientJpaEntity(ingredient.name)
        entity.id = ingredient.id.uuid
        entity
    }

//    fun toJpaEntity(id: IngredientId, saveCommand: IngredientRepoSaveCommand): IngredientJpaEntity {
//        val entity = IngredientJpaEntity(saveCommand.name)
//        entity.id = id.uuid
//        return entity
//    }
}

fun IngredientJpaEntity.toDomainObject(): Ingredient  {
    val entityId = id
    if (entityId == null) {
        throw IllegalStateException("Provide entity id before converting to aggregate!")
    }
    return Ingredient(
        id = IngredientId(entityId),
        name = name
    )
}