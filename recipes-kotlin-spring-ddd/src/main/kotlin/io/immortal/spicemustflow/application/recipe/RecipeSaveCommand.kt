package io.immortal.spicemustflow.application.recipe

import io.immortal.spicemustflow.application.validation.ValidRecipeIngredientsList
import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE
import io.immortal.spicemustflow.domain.recipe.RecipeIngredient
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class RecipeSaveCommand(
    @field:NotBlank
    @field:Size(max = DEFAULT_STRING_SIZE)
    val name: String = "",

    @field:NotBlank
    @field:Size(max = DEFAULT_STRING_SIZE)
    val content: String = "",

    @field:Min(0)
    val cookingMinutes: Int = 0,

    @field:ValidRecipeIngredientsList
    val ingredients: List<RecipeIngredient>
)