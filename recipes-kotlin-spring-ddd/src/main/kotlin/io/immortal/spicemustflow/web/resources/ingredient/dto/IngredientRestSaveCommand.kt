package io.immortal.spicemustflow.web.resources.ingredient.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO for ingredient which can be used for both create and update operations")
class IngredientRestSaveCommand(
    @Schema(description = "Unique name of the ingredient", example = "potato")
    val name: String
)