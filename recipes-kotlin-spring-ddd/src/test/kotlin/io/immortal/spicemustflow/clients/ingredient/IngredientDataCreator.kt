package io.immortal.spicemustflow.clients.ingredient

import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import java.util.Collections.synchronizedList
import kotlin.random.Random

class IngredientDataCreator {
    private val ingredientClient: IngredientTestClient = IngredientTestClient()
    private val createdIds: MutableList<IngredientRestId> = synchronizedList(mutableListOf())

    fun createRandomIngredients(): List<CreatedData<IngredientRestSaveCommand, IngredientRestId>> {
        return createIngredients(newRandomSaveCommands())
    }

    fun createIngredients(saveDtos: List<IngredientRestSaveCommand>): List<CreatedData<IngredientRestSaveCommand, IngredientRestId>> {
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
        val idsToDelete = createdIds.toList()
        if (idsToDelete.isNotEmpty()) {
            ingredientClient.deleteRequest(idsToDelete)
            createdIds.removeAll(idsToDelete)
        }
    }
}