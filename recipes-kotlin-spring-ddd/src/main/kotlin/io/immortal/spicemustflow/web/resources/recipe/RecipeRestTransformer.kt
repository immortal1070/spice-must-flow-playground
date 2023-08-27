package io.immortal.spicemustflow.web.resources.recipe

import io.immortal.spicemustflow.application.recipe.RecipeSaveCommand
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.recipe.Recipe
import io.immortal.spicemustflow.domain.recipe.RecipeIngredient
import io.immortal.spicemustflow.domain.recipe.RecipeQuery
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestId
import io.immortal.spicemustflow.web.resources.recipe.dto.*

@ApplicationScoped
class RecipeRestTransformer {
    fun toDto(recipe: Recipe): RecipeDto = recipe.run {
        RecipeDto(
            id = RecipeRestId(id),
            name = name,
            content = content,
            cookingMinutes = cookingMinutes,
            ingredients = toRecipeIngredients(recipe.ingredients)
        )
    }

    fun toSaveCommand(saveCommand: RecipeRestSaveCommand): RecipeSaveCommand = saveCommand.run {
        RecipeSaveCommand(
            name = name,
            content = content,
            cookingMinutes = cookingMinutes,
            ingredients = toRecipeIngredientsCommand(ingredients)
        )
    }

    fun toQuery(query: RecipeRestQuery): RecipeQuery = query.run {
        RecipeQuery(
            names = names,
            content = content,
            cookingMinutes = cookingMinutes,
            ids = ids?.map { it.toRecipeId() },
            ingredientNames = ingredientNames
        )
    }

    private fun toRecipeIngredientsCommand(ingredients: List<RecipeIngredientDto>): List<RecipeIngredient> =
        ingredients.map { RecipeIngredient(it.ingredientId.toIngredientId(), it.amount) }

    private fun toRecipeIngredients(ingredients: List<RecipeIngredient>): List<RecipeIngredientDto> =
        ingredients.map { RecipeIngredientDto(IngredientRestId(it.ingredientId), it.amount) }
}