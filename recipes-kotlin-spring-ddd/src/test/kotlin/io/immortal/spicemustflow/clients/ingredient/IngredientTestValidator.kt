package io.immortal.spicemustflow.clients.ingredient

import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import org.assertj.core.api.Assertions.assertThat

class IngredientTestValidator {
    fun validate(actual: IngredientDto, saveDto: IngredientRestSaveCommand) {
        assertThat(actual).isNotNull
        assertThat(actual.name).isEqualTo(saveDto.name)
        assertThat(actual.id).isNotNull
    }

    fun validate(actual: IngredientDto, expected: IngredientDto) {
        assertThat(actual).isEqualTo(expected)
    }
}