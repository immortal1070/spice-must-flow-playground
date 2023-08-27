package io.immortal.spicemustflow.web.resources.recipe.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO for recipe which can be used for both create and update operations")
class RecipeRestSaveCommand(
    @Schema(description = "Unique name of the recipe", example = "Potato cream soup")
    val name: String,

    @Schema(description = "Unique name of the recipe", example = "Boil water...")
    val content: String,

    @Schema(description = "Unique name of the recipe", example = "30")
    val cookingMinutes: Int = 0,

    val ingredients: List<RecipeIngredientDto> = listOf()
)
