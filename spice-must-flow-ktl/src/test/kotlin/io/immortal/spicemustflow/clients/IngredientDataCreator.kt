package io.immortal.spicemustflow.clients

import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.resource.ingredient.dto.IngredientRestSaveCommand
import io.immortal.spicemustflow.util.CreatedData
import io.immortal.spicemustflow.util.TestRandom
import java.util.*
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
        ingredientClient.deleteRequest(createdIds)
    }
}