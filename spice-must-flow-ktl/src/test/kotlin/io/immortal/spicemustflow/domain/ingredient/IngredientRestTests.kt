package io.immortal.spicemustflow.domain.ingredient

import io.immortal.spicemustflow.application.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.application.ingredient.dto.IngredientSaveDto
import io.immortal.spicemustflow.clients.*
import io.immortal.spicemustflow.util.TestRandom.Companion.randomString
import io.immortal.spicemustflow.util.TestResponse
import io.immortal.spicemustflow.util.constants.SizeConstants
import io.restassured.RestAssured
import io.restassured.parsing.Parser
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.util.*

//Comment @SpringBootTest and @WithDatabase to run tests over your local server
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WithDatabase
class IngredientRestTests {
    private val client: IngredientTestClient = IngredientTestClient()
    private val dataCreator: IngredientDataCreator = IngredientDataCreator()
    private val validator: IngredientTestValidator = IngredientTestValidator()

    @LocalServerPort
    var port = 8080

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        RestAssured.port = port
    }

    @AfterAll
    fun cleanup() {
        dataCreator.cleanup()
    }

    @Test
    fun `test ingredient creation`() {
        val ingredientSaveDto = IngredientSaveDto(randomString())
        val createdResult: TestResponse<IngredientDto> = client.validCreate(ingredientSaveDto)

        validator.validate(createdResult.body, ingredientSaveDto)

        val found: TestResponse<IngredientDto> = client.findById(createdResult.body.id)

        validator.validate(found.body, createdResult.body)
    }

    @Test
    fun `test ingredient creation with wrong parameter`() {
        val ingredientSaveDto = IngredientSaveDto(incorrectName())
        val createdResponse: ValidatableResponse = client.createRequest(ingredientSaveDto)
        // TODO validate exact error
        createdResponse.statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `test ingredient update with wrong parameter`() {
        val ingredientSaveDto = IngredientSaveDto(randomString())
        val created: TestResponse<IngredientDto> = client.validCreate(ingredientSaveDto)

        val ingredientUpdateDto = IngredientSaveDto(incorrectName())
        val updatedResponse = client.updateRequest(created.body.id, ingredientUpdateDto)
        println(updatedResponse.extract().asPrettyString())
        // TODO validate exact error
        updatedResponse.statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `test ingredient update`() {
        val ingredientSaveDto = IngredientSaveDto(randomString())
        val created: TestResponse<IngredientDto> = client.validCreate(ingredientSaveDto)

        val ingredientUpdateDto = IngredientSaveDto(randomString())

        val updated = client.validUpdate(created.body.id, ingredientUpdateDto)

        validator.validate(updated.body, ingredientUpdateDto)

        val found: TestResponse<IngredientDto> = client.findById(created.body.id)

        validator.validate(found.body, ingredientUpdateDto)
    }

    @Test
    fun `test ingredient deletion`() {
        val ingredientSaveDto = IngredientSaveDto(randomString())
        val created: TestResponse<IngredientDto> = client.validCreate(ingredientSaveDto)

        val deletedResponse = client.deleteRequest(created.body.id)
        deletedResponse.statusCode(HttpStatus.NO_CONTENT.value())

        client.findNone(mapOf(INGREDIENT_PARAM_ID to created.body.id))
    }

    @Test
    fun `test ingredient deletion with missing id`() {
        dataCreator.createRandomIngredients()

        val deletedResponse = client.deleteRequest(UUID.randomUUID())
        deletedResponse.statusCode(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `test find by name`() {
        val createdData = dataCreator.createRandomIngredients()
        val anyInputDto = createdData.random().inputDto

        val found: TestResponse<List<IngredientDto>> = client.find(INGREDIENT_PARAM_NAME to anyInputDto.name)

        assertThat(found.body.size).isEqualTo(1)

        validator.validate(found.body[0], anyInputDto)
    }

    @Test
    fun `test find by name when missing`() {
        dataCreator.createRandomIngredients()

        client.findNone(mapOf(INGREDIENT_PARAM_NAME to "missing name"))
    }

    @Test
    fun `test find by id`() {
        val preparedData = dataCreator.createRandomIngredients()
        val anyCreated = preparedData.random().createdResponse

        val found: TestResponse<IngredientDto> = client.findById(anyCreated.body.id)

        validator.validate(found.body, anyCreated.body)
    }

    @Test
    fun `test find by id when no data`() {
        client.findNone(mapOf(INGREDIENT_PARAM_ID to UUID.randomUUID()))
    }

    private fun incorrectName(): String = randomString(SizeConstants.DEFAULT_STRING_SIZE + 1)
}
