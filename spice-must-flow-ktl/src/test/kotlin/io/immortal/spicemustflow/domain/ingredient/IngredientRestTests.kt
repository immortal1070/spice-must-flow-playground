package io.immortal.spicemustflow.domain.ingredient

import io.immortal.spicemustflow.clients.INGREDIENT_PARAM_NAME
import io.immortal.spicemustflow.clients.IngredientDataCreator
import io.immortal.spicemustflow.clients.IngredientTestClient
import io.immortal.spicemustflow.clients.IngredientTestValidator
import io.immortal.spicemustflow.resource.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.resource.ingredient.dto.IngredientRestSaveCommand
import io.immortal.spicemustflow.util.CreatedData
import io.immortal.spicemustflow.util.RestAssuredTest
import io.immortal.spicemustflow.util.TestRandom.Companion.randomString
import io.immortal.spicemustflow.util.TestResponse
import io.immortal.spicemustflow.util.TestResponseWithBody
import io.immortal.spicemustflow.util.constants.SizeConstants
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertNotNull

//Comment @SpringBootTest and @WithDatabase to run tests over your local server
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WithDatabase
class IngredientRestTests : RestAssuredTest() {
    private val client: IngredientTestClient = IngredientTestClient()
    private val dataCreator: IngredientDataCreator = IngredientDataCreator()
    private val validator: IngredientTestValidator = IngredientTestValidator()

    @AfterAll
    fun cleanup() {
        dataCreator.cleanup()
    }

    @Test
    fun `test ingredient creation`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(randomString())
        val createdResult: TestResponseWithBody<IngredientId> = client.validCreate(ingredientSaveCommand)
        val createdId: IngredientId = createdResult.body

        val found: TestResponseWithBody<IngredientDto> = client.findById(createdId)

        assertThat(found.body.id).isEqualTo(createdId)
        validator.validate(found.body, ingredientSaveCommand)
        found.response.printAsPrettyString()
    }

    @Test
    fun `test ingredient creation with wrong parameter`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(incorrectName())
        val createdResponse: TestResponse = client.createRequest(ingredientSaveCommand)
        // TODO validate exact error
        createdResponse.statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `test ingredient update`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(randomString())
        val created: TestResponseWithBody<IngredientId> = client.validCreate(ingredientSaveCommand)

        client.findById(created.body) // caching the result

        val ingredientUpdateDto = IngredientRestSaveCommand(randomString())

        client.validUpdate(created.body, ingredientUpdateDto)

        val found: TestResponseWithBody<IngredientDto> = client.findById(created.body)

        validator.validate(found.body, ingredientUpdateDto)
    }

    @Test
    fun `test ingredient update with wrong parameter`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(randomString())
        val created: TestResponseWithBody<IngredientId> = client.validCreate(ingredientSaveCommand)

        val ingredientUpdateDto = IngredientRestSaveCommand(incorrectName())
        val updatedResponse = client.updateRequest(created.body, ingredientUpdateDto)
        // TODO validate exact error
        updatedResponse.statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `test ingredient deletion`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(randomString())
        val created: TestResponseWithBody<IngredientId> = client.validCreate(ingredientSaveCommand)
        val createdId: IngredientId = created.body
        assertNotNull(client.findById(createdId))

        val deletedResponse = client.deleteRequest(createdId)
        deletedResponse.statusCode(HttpStatus.NO_CONTENT.value())

        client.findNone(createdId)
    }

    @Test
    fun `test ingredient deletion with missing id`() {
        dataCreator.createRandomIngredients()

        val deletedResponse = client.deleteRequest(client.generateId())
        deletedResponse.statusCode(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `test find by name`() {
        createRandomAndFindOneByName()
    }

    @Test
    fun `test that cache is dropped after deletion`() {
        val oneOfCreated: IngredientDto = createRandomAndFindOneByName()

        client.deleteRequest(oneOfCreated.id)

        val found: TestResponseWithBody<List<IngredientDto>> =
            client.find(INGREDIENT_PARAM_NAME to oneOfCreated.name)

        assertThat(found.body.size).isEqualTo(0)
    }

    @Test
    fun `test find by name when missing`() {
        dataCreator.createRandomIngredients()

        client.findNone(mapOf(INGREDIENT_PARAM_NAME to "missing name"))
    }

    @Test
    fun `test find by id`() {
        val preparedData = dataCreator.createRandomIngredients()
        val anyCreatedId = preparedData.random().createdResponse.body

        val found: TestResponseWithBody<IngredientDto> = client.findById(anyCreatedId)

        assertThat(found.body.id).isEqualTo(anyCreatedId)
    }

    @Test
    fun `test find by id when no data`() {
        client.findNone(client.generateId())
    }

    private fun createRandomAndFindOneByName(): IngredientDto {
        val createdData: List<CreatedData<IngredientRestSaveCommand, IngredientId>> =
            dataCreator.createRandomIngredients()
        val anyInputDto: IngredientRestSaveCommand = createdData.random().inputDto

        val found: TestResponseWithBody<List<IngredientDto>> =
            client.find(INGREDIENT_PARAM_NAME to anyInputDto.name)

        assertThat(found.body.size).isEqualTo(1)

        val foundEntry: IngredientDto = found.body[0]
        assertThat(foundEntry.name).isEqualTo(anyInputDto.name)

        return foundEntry
    }

    private fun incorrectName(): String = randomString(SizeConstants.DEFAULT_STRING_SIZE + 1)
}
