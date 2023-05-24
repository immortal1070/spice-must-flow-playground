package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import org.springframework.stereotype.Component
import java.util.*

@Component
class IngredientTransformer {
    fun toIngredient(id: IngredientId, saveCommand: IngredientSaveCommand): Ingredient = saveCommand.run {
        Ingredient(
            id = id,
            name = name
        )
    }
}