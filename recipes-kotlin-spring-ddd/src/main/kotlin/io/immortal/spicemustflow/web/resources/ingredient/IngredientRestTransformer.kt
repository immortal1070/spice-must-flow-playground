package io.immortal.spicemustflow.web.resources.ingredient

import io.immortal.spicemustflow.application.ingredient.IngredientSaveCommand
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientQuery
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestQuery
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand

@ApplicationScoped
class IngredientRestTransformer {
    fun toDto(ingredient: Ingredient): IngredientDto = ingredient.run {
        IngredientDto(
            id = id,
            name = name,
        )
    }

    fun toSaveCommand(saveCommand: IngredientRestSaveCommand): IngredientSaveCommand = saveCommand.run {
        IngredientSaveCommand(
            name = name,
        )
    }

    fun toQuery(query: IngredientRestQuery): IngredientQuery = query.run {
        IngredientQuery(
            names = names,
            ids = ids,
        )
    }
}