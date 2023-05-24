package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.util.constants.SizeConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class IngredientSaveCommand(
    @field:NotBlank @field:Size(max = SizeConstants.DEFAULT_STRING_SIZE) var name: String
)