package io.immortal.spicemustflow.application.ingredient.dto

import io.immortal.spicemustflow.util.constants.SizeConstants
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

//TODO validate for uniqueness
@Schema(description = "DTO for ingredient which can be used for both create and update operations")
class IngredientSaveDto(
    @Schema(description = "Unique name of the ingredient", example = "potato")
    // TODO read about @field
    @field:NotBlank @field:Size(max = SizeConstants.DEFAULT_STRING_SIZE) var name: String
)