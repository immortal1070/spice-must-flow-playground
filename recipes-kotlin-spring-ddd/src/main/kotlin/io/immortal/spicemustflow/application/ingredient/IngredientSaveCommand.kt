package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class IngredientSaveCommand(
    @field:NotBlank @field:Size(max = DEFAULT_STRING_SIZE) val name: String
)