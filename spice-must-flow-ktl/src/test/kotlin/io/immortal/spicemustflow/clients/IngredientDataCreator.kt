package io.immortal.spicemustflow.clients

import io.immortal.spicemustflow.application.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.application.ingredient.dto.IngredientSaveDto
import io.immortal.spicemustflow.util.CreatedData
import io.immortal.spicemustflow.util.TestRandom
import java.util.*
import kotlin.random.Random

class IngredientDataCreator {
    private val ingredientClient: IngredientTestClient = IngredientTestClient()
    private val createdIds: MutableList<UUID> = mutableListOf()

    fun createRandomIngredients(): List<CreatedData<IngredientSaveDto, IngredientDto>> {
        return createIngredients(randomSaveDtos())
    }

    fun createIngredients(saveDtos: List<IngredientSaveDto>): List<CreatedData<IngredientSaveDto, IngredientDto>> {
        return saveDtos.map {
            CreatedData(
                inputDto = it,
                createdResponse = ingredientClient.validCreate(it)
            )
        }.toList()
            .also {
                createdIds.addAll(it.map { createdData -> createdData.createdResponse.body.id })
            }
    }

    fun saveDto(): IngredientSaveDto = IngredientSaveDto(TestRandom.randomString())

    fun randomSaveDtos(): List<IngredientSaveDto> = (1..Random.nextInt(3, 10)).map {
        saveDto()
    }

    fun cleanup() {
        ingredientClient.deleteRequest(createdIds)
    }
}