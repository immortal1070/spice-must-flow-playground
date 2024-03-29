package io.immortal.spicemustflow.web.resources.recipe.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO presenting the recipe")
data class RecipeDto(
    val id: RecipeRestId,

    @Schema(example = "potato")
    val name: String,

    @Schema(example = "Boil the water...")
    val content: String,

    @Schema(example = "30")
    val cookingMinutes: Int,

    val ingredients: List<RecipeIngredientDto>
)