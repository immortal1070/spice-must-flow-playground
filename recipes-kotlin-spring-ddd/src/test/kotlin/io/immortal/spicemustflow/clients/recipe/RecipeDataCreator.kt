package io.immortal.spicemustflow.clients.recipe

import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomInt
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeIngredientDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestId
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand
import kotlin.random.Random

class RecipeDataCreator {
    private val recipeClient: RecipeTestClient = RecipeTestClient()
    private val createdIds: MutableList<RecipeRestId> = mutableListOf()

    fun createRandomRecipes(): List<CreatedData<RecipeRestSaveCommand, RecipeRestId>> {
        return createRecipe(newRandomSaveDtos())
    }

    fun createRecipe(saveDtos: List<RecipeRestSaveCommand>): List<CreatedData<RecipeRestSaveCommand, RecipeRestId>> {
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
        val idsToDelete = createdIds.toList()
        if (idsToDelete.isNotEmpty()) {
            println("deleting $idsToDelete")
            recipeClient.deleteRequest(idsToDelete)
            createdIds.removeAll(idsToDelete)
        }
    }

    fun createRecipe(saveDto: RecipeRestSaveCommand): CreatedData<RecipeRestSaveCommand, RecipeRestId> {
        return createRecipe(listOf(saveDto))[0]
    }
}