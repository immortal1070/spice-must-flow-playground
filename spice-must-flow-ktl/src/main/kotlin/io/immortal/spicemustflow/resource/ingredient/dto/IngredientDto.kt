package io.immortal.spicemustflow.resource.ingredient.dto

import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.swagger.v3.oas.annotations.media.Schema

data class IngredientDto(
    val id: IngredientId,
    @Schema(example = "potato")
    val name: String
)