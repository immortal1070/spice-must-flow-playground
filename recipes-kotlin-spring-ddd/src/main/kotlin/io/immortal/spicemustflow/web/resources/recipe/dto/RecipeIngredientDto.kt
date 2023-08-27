package io.immortal.spicemustflow.web.resources.recipe.dto

import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestId
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "DTO for ingredients of a recipe")
class RecipeIngredientDto(
    val ingredientId: IngredientRestId,

    @Schema(description = "Amount of ingredient used in this recipe", example = "0.5")
    val amount: BigDecimal
)