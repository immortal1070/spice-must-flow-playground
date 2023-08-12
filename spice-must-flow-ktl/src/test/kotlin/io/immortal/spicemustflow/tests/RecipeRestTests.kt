package io.immortal.spicemustflow.tests

import RecipeIngredientDto
import io.immortal.spicemustflow.application.validation.VALIDATION_RECIPE_INGREDIENT_LIST_ERROR
import io.immortal.spicemustflow.clients.ingredient.IngredientDataCreator
import io.immortal.spicemustflow.clients.recipe.RECIPE_PARAM_NAME
import io.immortal.spicemustflow.clients.recipe.RecipeDataCreator
import io.immortal.spicemustflow.clients.recipe.RecipeTestClient
import io.immortal.spicemustflow.clients.recipe.RecipeTestValidator
import io.immortal.spicemustflow.common.CreatedData
import io.immortal.spicemustflow.common.TestResponse
import io.immortal.spicemustflow.common.TestResponseWithBody
import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE
import io.immortal.spicemustflow.common.restassured.RestAssuredTest
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomDouble
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.common.validation.WebValidator
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.testcontainers.WithDatabase
import io.immortal.spicemustflow.web.resources.recipe.RECIPE_PATH
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import kotlin.test.assertNotNull

//Comment @SpringBootTest and @WithDatabase to run tests over your local server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithDatabase
class RecipeRestTests : RestAssuredTest() {
    private val client: RecipeTestClient = RecipeTestClient()
    private val recipeDataCreator: RecipeDataCreator = RecipeDataCreator()
    private val ingredientDataCreator: IngredientDataCreator = IngredientDataCreator()
    private val recipeValidator: RecipeTestValidator = RecipeTestValidator()
    private val webValidator: WebValidator = WebValidator()

    @AfterAll
    fun cleanup() {
        recipeDataCreator.cleanup()
        ingredientDataCreator.cleanup()
    }

    @Test
    fun `test recipe creation`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val createdResult: TestResponseWithBody<RecipeId> = client.validCreate(recipeSaveCommand)
        val createdId: RecipeId = createdResult.body

        val found: TestResponseWithBody<RecipeDto> = client.findById(createdId)

        assertThat(found.body.id).isEqualTo(createdId)
        recipeValidator.validate(found.body, recipeSaveCommand)
    }

    @Test
    fun `test recipe creation with ingredients`() {
        ingredientDataCreator.createRandomIngredients()

        val someCreatedIngredientIds: List<IngredientId> =
            ingredientDataCreator.createRandomIngredients().map { it.createdResponse.body }

        val recipeIngredients = someCreatedIngredientIds.map {
            RecipeIngredientDto(ingredientId = it, amount = randomDouble())
        }

        val recipeSaveCommand = recipeDataCreator.newSaveCommand(ingredients = recipeIngredients)
        val createdResult: TestResponseWithBody<RecipeId> = client.validCreate(recipeSaveCommand)
        val createdId: RecipeId = createdResult.body

        val found: TestResponseWithBody<RecipeDto> = client.findById(createdId)

        assertThat(found.body.id).isEqualTo(createdId)
        recipeValidator.validate(found.body, recipeSaveCommand)
    }


    @Test
    fun `test recipe creation with duplicated ingredients`() {
        val newIngredientId = ingredientDataCreator.createIngredients(
            listOf(
                ingredientDataCreator.newSaveCommand()
            )
        )[0].createdResponse.body

        val recipeIngredients = listOf(
            RecipeIngredientDto(ingredientId = newIngredientId,
                amount = 0.0),
            RecipeIngredientDto(ingredientId = newIngredientId,
                amount = 1.0)
        )
//
        val recipeSaveCommand = recipeDataCreator.newSaveCommand(ingredients = recipeIngredients)
        val createdResponse: TestResponse = client.createRequest(recipeSaveCommand)

        webValidator.validateBadRequest(
            response = createdResponse,
            path = RECIPE_PATH,
            errorMessage = VALIDATION_RECIPE_INGREDIENT_LIST_ERROR,
            invalidValueSubstring = newIngredientId.toString()
        )
    }

    @Test
    fun `test recipe creation with wrong parameter`() {
        val invalidName = newInvalidName()
        val recipeSaveCommand = recipeDataCreator.newSaveCommand(invalidName)
        val createdResponse: TestResponse = client.createRequest(recipeSaveCommand)

        webValidator.validateDefaultMaxSizeError(
            response = createdResponse,
            path = RECIPE_PATH,
            invalidValue = invalidName
        )
    }

    @Test
    fun `test recipe update`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val created: TestResponseWithBody<RecipeId> = client.validCreate(recipeSaveCommand)

        client.findById(created.body) // caching the result

        val recipeUpdateDto = recipeDataCreator.newSaveCommand()

        client.validUpdate(created.body, recipeUpdateDto)

        val found: TestResponseWithBody<RecipeDto> = client.findById(created.body)

        recipeValidator.validate(found.body, recipeUpdateDto)
    }

    @Test
    fun `test recipe update ingredients collection`() {
        val someCreatedIngredientIds: List<IngredientId> =
            ingredientDataCreator.createRandomIngredients().map { it.createdResponse.body }
        val recipeIngredients = someCreatedIngredientIds.map {
            RecipeIngredientDto(ingredientId = it, amount = randomDouble())
        }

        val recipeSaveCommand = recipeDataCreator.newSaveCommand(ingredients = recipeIngredients)
        val created: TestResponseWithBody<RecipeId> = client.validCreate(recipeSaveCommand)

        client.findById(created.body) // caching the result

        val anotherCreatedIngredientIds: List<IngredientId> =
            ingredientDataCreator.createRandomIngredients().map { it.createdResponse.body }
        val recipeIngredientsForUpdate = anotherCreatedIngredientIds.map {
            RecipeIngredientDto(ingredientId = it, amount = randomDouble())
        }

        val recipeUpdateDto = recipeDataCreator.newSaveCommand(ingredients = recipeIngredientsForUpdate)

        client.validUpdate(created.body, recipeUpdateDto)

        val found: TestResponseWithBody<RecipeDto> = client.findById(created.body)

        recipeValidator.validate(found.body, recipeUpdateDto)
    }

    @Test
    fun `test recipe update with wrong parameter`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val created: TestResponseWithBody<RecipeId> = client.validCreate(recipeSaveCommand)
        val createdId: RecipeId = created.body

        val invalidName = newInvalidName()
        val recipeUpdateDto = recipeDataCreator.newSaveCommand(invalidName)
        val updatedResponse = client.updateRequest(createdId, recipeUpdateDto)

        webValidator.validateDefaultMaxSizeError(
            response = updatedResponse,
            path = "$RECIPE_PATH/$createdId",
            invalidValue = invalidName
        )
    }

    @Test
    fun `test recipe deletion`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val created: TestResponseWithBody<RecipeId> = client.validCreate(recipeSaveCommand)
        val createdId: RecipeId = created.body
        assertNotNull(client.findById(createdId))

        val deletedResponse = client.deleteRequest(createdId)
        deletedResponse.statusCode(HttpStatus.NO_CONTENT.value())

        client.findNone(createdId)
    }

    @Test
    fun `test recipe deletion with missing id`() {
        recipeDataCreator.createRandomRecipes()

        val deletedResponse = client.deleteRequest(client.generateId())
        deletedResponse.statusCode(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `test find by name`() {
        createRandomAndFindOneByName()
    }

    @Test
    fun `test that cache is dropped after deletion`() {
        val oneOfCreated: RecipeDto = createRandomAndFindOneByName()

        client.deleteRequest(oneOfCreated.id)

        val found: TestResponseWithBody<List<RecipeDto>> =
            client.find(RECIPE_PARAM_NAME to oneOfCreated.name)

        assertThat(found.body.size).isEqualTo(0)
    }

    @Test
    fun `test find by name when missing`() {
        recipeDataCreator.createRandomRecipes()

        client.findNone(mapOf(RECIPE_PARAM_NAME to "missing name"))
    }

    @Test
    fun `test find by id`() {
        val preparedData = recipeDataCreator.createRandomRecipes()
        val anyCreatedId = preparedData.random().createdResponse.body

        val found: TestResponseWithBody<RecipeDto> = client.findById(anyCreatedId)

        assertThat(found.body.id).isEqualTo(anyCreatedId)
    }

    @Test
    fun `test find by id when no data`() {
        client.findNone(client.generateId())
    }

//    @Test
//    fun `test find by id when no data`() {
//        client.findNone(client.generateId())
//    }

    private fun createRandomAndFindOneByName(): RecipeDto {
        val createdData: List<CreatedData<RecipeRestSaveCommand, RecipeId>> =
            recipeDataCreator.createRandomRecipes()
        val anyInputDto: RecipeRestSaveCommand = createdData.random().inputDto

        val found: TestResponseWithBody<List<RecipeDto>> =
            client.find(RECIPE_PARAM_NAME to anyInputDto.name)

        assertThat(found.body.size).isEqualTo(1)

        val foundEntry: RecipeDto = found.body[0]
        assertThat(foundEntry.name).isEqualTo(anyInputDto.name)

        return foundEntry
    }

    private fun newInvalidName(): String = randomString(DEFAULT_STRING_SIZE + 1)
}
