package io.immortal.spicemustflow.web.resources.ingredient.dto

import io.swagger.v3.oas.annotations.media.Schema

data class IngredientDto(
    val id: IngredientRestId,

    @Schema(example = "potato")
    val name: String
)