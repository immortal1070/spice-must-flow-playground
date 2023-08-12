package io.immortal.spicemustflow.web.resources.recipe

import RecipeIngredientDto
import io.immortal.spicemustflow.application.recipe.RecipeSaveCommand
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.recipe.Recipe
import io.immortal.spicemustflow.domain.recipe.RecipeIngredient
import io.immortal.spicemustflow.domain.recipe.RecipeQuery
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestQuery
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand

@ApplicationScoped
class RecipeRestTransformer {
    fun toDto(recipe: Recipe): RecipeDto = recipe.run {
        RecipeDto(
            id = id,
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
            contents = contents,
            cookingMinutes = cookingMinutes,
            ids = ids,
            ingredientNames = ingredientNames
        )
    }

    private fun toRecipeIngredientsCommand(ingredients: List<RecipeIngredientDto>): List<RecipeIngredient> =
        ingredients.map { RecipeIngredient(it.ingredientId, it.amount) }

    private fun toRecipeIngredients(ingredients: List<RecipeIngredient>): List<RecipeIngredientDto> =
        ingredients.map { RecipeIngredientDto(it.ingredientId, it.amount) }
}