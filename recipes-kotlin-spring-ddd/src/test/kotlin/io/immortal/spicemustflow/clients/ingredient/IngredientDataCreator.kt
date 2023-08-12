package io.immortal.spicemustflow.clients.ingredient

import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import kotlin.random.Random

class IngredientDataCreator {
    private val ingredientClient: IngredientTestClient = IngredientTestClient()
    private val createdIds: MutableList<IngredientId> = mutableListOf()

    fun createRandomIngredients(): List<CreatedData<IngredientRestSaveCommand, IngredientId>> {
        return createIngredients(newRandomSaveCommands())
    }

    fun createIngredients(saveDtos: List<IngredientRestSaveCommand>): List<CreatedData<IngredientRestSaveCommand, IngredientId>> {
        return saveDtos.map {
            CreatedData(
                inputDto = it,
                createdResponse = ingredientClient.validCreate(it)
            )
        }.toList()
            .also {
                createdIds.addAll(it.map { createdData -> createdData.createdResponse.body })
            }
    }

    fun newSaveCommand(): IngredientRestSaveCommand = IngredientRestSaveCommand(randomString())

    fun newRandomSaveCommands(): List<IngredientRestSaveCommand> = (1..Random.nextInt(3, 10)).map {
        newSaveCommand()
    }

    fun cleanup() {
        //TODO check parallel test run behaviour
        val idsToDelete = createdIds.toList()
        ingredientClient.deleteRequest(idsToDelete)
        createdIds.removeAll(idsToDelete)
    }
}