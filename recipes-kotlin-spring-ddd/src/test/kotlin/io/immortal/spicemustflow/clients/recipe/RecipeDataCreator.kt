package io.immortal.spicemustflow.clients.recipe

import RecipeIngredientDto
import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomInt
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand

import kotlin.random.Random

class RecipeDataCreator {
    private val recipeClient: RecipeTestClient = RecipeTestClient()
    private val createdIds: MutableList<RecipeId> = mutableListOf()

    fun createRandomRecipes(): List<CreatedData<RecipeRestSaveCommand, RecipeId>> {
        return createRecipes(newRandomSaveDtos())
    }

    fun createRecipes(saveDtos: List<RecipeRestSaveCommand>): List<CreatedData<RecipeRestSaveCommand, RecipeId>> {
        return saveDtos.map {
            CreatedData(
                inputDto = it,
                createdResponse = recipeClient.validCreate(it)
            )
        }.toList()
            .also {
                createdIds.addAll(it.map { createdData -> createdData.createdResponse.body })
            }
    }

    fun newSaveCommand(name: String = randomString(),
                       content: String = randomString(),
                       cookingMinutes: Int = randomInt(),
                       ingredients: List<RecipeIngredientDto> = emptyList()): RecipeRestSaveCommand
    = RecipeRestSaveCommand(
        name,
        content,
        cookingMinutes,
        ingredients)

    fun newRandomSaveDtos(): List<RecipeRestSaveCommand> = (1..Random.nextInt(3, 10)).map {
        newSaveCommand()
    }

    fun cleanup() {
        //TODO check parallel test run behaviour
        val idsToDelete = createdIds.toList()
        recipeClient.deleteRequest(idsToDelete)
        createdIds.removeAll(idsToDelete)
    }
}