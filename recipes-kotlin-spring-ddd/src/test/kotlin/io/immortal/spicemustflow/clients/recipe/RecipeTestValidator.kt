package io.immortal.spicemustflow.clients.recipe

import RecipeIngredientDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand
import org.assertj.core.api.Assertions.assertThat

class RecipeTestValidator {
    fun validate(actual: RecipeDto, saveDto: RecipeRestSaveCommand) {
        assertThat(actual).isNotNull
        assertThat(actual.name).isEqualTo(saveDto.name)
        assertThat(actual.content).isEqualTo(saveDto.content)
        assertThat(actual.cookingMinutes).isEqualTo(saveDto.cookingMinutes)
        assertThat(actual.id).isNotNull
        assertThat(actual.ingredients.size).isEqualTo(saveDto.ingredients.size)
        actual.ingredients.forEach {
            val saveDtoIngredient: RecipeIngredientDto? = saveDto.ingredients.find { saveDtoIngredient ->
                saveDtoIngredient.ingredientId == it.ingredientId }
            assertThat(saveDtoIngredient).isNotNull()
            assertThat(saveDtoIngredient?.amount).isEqualTo(it.amount)
        }
    }

    fun validate(actual: RecipeDto, expected: RecipeDto) {
        assertThat(actual).isEqualTo(expected)
    }
}