package io.immortal.spicemustflow.application.ingredient.dto

import io.immortal.spicemustflow.util.constants.SizeConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class IngredientSaveDto(
    // TODO read about @field
    @field:NotBlank @field:Size(max = SizeConstants.DEFAULT_STRING_SIZE) var name: String
)