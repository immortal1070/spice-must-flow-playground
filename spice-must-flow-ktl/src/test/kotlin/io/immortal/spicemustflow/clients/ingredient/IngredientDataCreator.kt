package io.immortal.spicemustflow.clients.ingredient

import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.utils.TestRandom
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import kotlin.random.Random

class IngredientDataCreator {
    private val ingredientClient: IngredientTestClient = IngredientTestClient()
    private val createdIds: MutableList<IngredientId> = mutableListOf()

    fun createRandomIngredients(): List<CreatedData<IngredientRestSaveCommand, IngredientId>> {
        return createIngredients(randomSaveDtos())
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

    fun saveDto(): IngredientRestSaveCommand = IngredientRestSaveCommand(TestRandom.randomString())

    fun randomSaveDtos(): List<IngredientRestSaveCommand> = (1..Random.nextInt(3, 10)).map {
        saveDto()
    }

    fun cleanup() {
        //TODO check parallel test run behaviour
        val idsToDelete = createdIds.toList()
        ingredientClient.deleteRequest(idsToDelete)
        createdIds.removeAll(idsToDelete)
    }
}