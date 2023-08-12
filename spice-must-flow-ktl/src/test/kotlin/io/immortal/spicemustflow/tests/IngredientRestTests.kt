package io.immortal.spicemustflow.tests

import io.immortal.spicemustflow.clients.ingredient.INGREDIENT_PARAM_NAME
import io.immortal.spicemustflow.clients.ingredient.IngredientDataCreator
import io.immortal.spicemustflow.clients.ingredient.IngredientTestClient
import io.immortal.spicemustflow.clients.ingredient.IngredientTestValidator
import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.TestResponse
import io.immortal.spicemustflow.common.TestResponseWithBody
import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE
import io.immortal.spicemustflow.common.restassured.RestAssuredTest
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.common.validation.WebValidator
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.testcontainers.WithDatabase
import io.immortal.spicemustflow.web.resources.ingredient.INGREDIENT_PATH
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import kotlin.test.assertNotNull

//Comment @SpringBootTest and @WithDatabase to run tests over your local server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithDatabase
class IngredientRestTests : RestAssuredTest() {
    private val client: IngredientTestClient = IngredientTestClient()
    private val dataCreator: IngredientDataCreator = IngredientDataCreator()
    private val ingredientValidator: IngredientTestValidator = IngredientTestValidator()
    private val webValidator: WebValidator = WebValidator()

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
        ingredientValidator.validate(found.body, ingredientSaveCommand)
        found.response.printAsPrettyString()
    }

    @Test
    fun `test ingredient creation with wrong parameter`() {
        val invalidName = newInvalidName()
        val ingredientSaveCommand = IngredientRestSaveCommand(invalidName)
        val createdResponse: TestResponse = client.createRequest(ingredientSaveCommand)

        webValidator.validateDefaultMaxSizeError(
            response = createdResponse,
            path = INGREDIENT_PATH,
            invalidValue = invalidName)
    }

    @Test
    fun `test ingredient update`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(randomString())
        val created: TestResponseWithBody<IngredientId> = client.validCreate(ingredientSaveCommand)

        client.findById(created.body) // caching the result

        val ingredientUpdateDto = IngredientRestSaveCommand(randomString())

        client.validUpdate(created.body, ingredientUpdateDto)

        val found: TestResponseWithBody<IngredientDto> = client.findById(created.body)

        ingredientValidator.validate(found.body, ingredientUpdateDto)
    }

    @Test
    fun `test ingredient update with wrong parameter`() {
        val ingredientSaveCommand = IngredientRestSaveCommand(randomString())
        val created: TestResponseWithBody<IngredientId> = client.validCreate(ingredientSaveCommand)
        val createdId: IngredientId = created.body

        val invalidName = newInvalidName()
        val ingredientUpdateDto = IngredientRestSaveCommand(invalidName)
        val updatedResponse = client.updateRequest(createdId, ingredientUpdateDto)

        webValidator.validateDefaultMaxSizeError(
            response = updatedResponse,
            path = "$INGREDIENT_PATH/$createdId",
            invalidValue = invalidName)
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

    private fun newInvalidName(): String = randomString(DEFAULT_STRING_SIZE + 1)
}