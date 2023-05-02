package io.immortal.spicemustflow.application.ingredient.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class IngredientDto(
    val id: UUID,
    @Schema(example = "potato")
    val name: String
)