package io.immortal.spicemustflow.tests

import io.immortal.spicemustflow.application.recipe.validation.VALIDATION_RECIPE_INGREDIENT_LIST_ERROR
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
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomBigDecimal
import io.immortal.spicemustflow.common.utils.TestRandom.Companion.randomString
import io.immortal.spicemustflow.common.validation.WebValidator
import io.immortal.spicemustflow.testcontainers.WithDatabase
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestId
import io.immortal.spicemustflow.web.resources.recipe.RECIPE_PATH
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeIngredientDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestId
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*
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
        val createdResult: CreatedData<RecipeRestSaveCommand, RecipeRestId> =
            recipeDataCreator.createRecipe(recipeSaveCommand)
        val createdId: RecipeRestId = createdResult.createdResponse.body

        val found: TestResponseWithBody<RecipeDto> = client.findById(createdId)

        assertThat(found.body.id).isEqualTo(createdId)
        recipeValidator.validate(found.body, recipeSaveCommand)
    }

    @Test
    fun `test recipe creation with ingredients`() {
        ingredientDataCreator.createRandomIngredients()

        val someCreatedIngredientRestIds: List<IngredientRestId> =
            ingredientDataCreator.createRandomIngredients().map { it.createdResponse.body }

        val recipeIngredients = someCreatedIngredientRestIds.map {
            RecipeIngredientDto(ingredientId = it, amount = randomBigDecimal())
        }

        val recipeSaveCommand = recipeDataCreator.newSaveCommand(ingredients = recipeIngredients)
        val createdResult: CreatedData<RecipeRestSaveCommand, RecipeRestId> =
            recipeDataCreator.createRecipe(recipeSaveCommand)
        val createdId: RecipeRestId = createdResult.createdResponse.body

        val found: TestResponseWithBody<RecipeDto> = client.findById(createdId)

        assertThat(found.body.id).isEqualTo(createdId)
        recipeValidator.validate(found.body, recipeSaveCommand)
    }

    @Test
    fun `test recipe creation with duplicated ingredients`() {
        val newIngredientRestId = ingredientDataCreator.createIngredients(
            listOf(
                ingredientDataCreator.newSaveCommand()
            )
        )[0].createdResponse.body

        val recipeIngredients = listOf(
            RecipeIngredientDto(
                ingredientId = newIngredientRestId,
                amount = BigDecimal.ZERO
            ),
            RecipeIngredientDto(
                ingredientId = newIngredientRestId,
                BigDecimal.ONE
            )
        )

        val recipeSaveCommand = recipeDataCreator.newSaveCommand(ingredients = recipeIngredients)
        val createdResponse: TestResponse = client.createRequest(recipeSaveCommand, HttpStatus.BAD_REQUEST)

        webValidator.validateBadRequest(
            response = createdResponse,
            path = RECIPE_PATH,
            errorMessage = VALIDATION_RECIPE_INGREDIENT_LIST_ERROR,
            invalidValueSubstring = newIngredientRestId.toString()
        )
    }

    @Test
    fun `test recipe creation with wrong parameter`() {
        val invalidName = newInvalidName()
        val recipeSaveCommand = recipeDataCreator.newSaveCommand(invalidName)
        val createdResponse: TestResponse = client.createRequest(recipeSaveCommand, HttpStatus.BAD_REQUEST)

        webValidator.validateDefaultMaxSizeError(
            response = createdResponse,
            path = RECIPE_PATH,
            invalidValue = invalidName
        )
    }

    @Test
    fun `test recipe update`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val createdResult: CreatedData<RecipeRestSaveCommand, RecipeRestId> =
            recipeDataCreator.createRecipe(recipeSaveCommand)
        val createdId: RecipeRestId = createdResult.createdResponse.body

        client.findById(createdId) // caching the result

        val recipeUpdateDto = recipeDataCreator.newSaveCommand()

        client.validUpdate(createdId, recipeUpdateDto)

        val found: TestResponseWithBody<RecipeDto> = client.findById(createdId)

        recipeValidator.validate(found.body, recipeUpdateDto)
    }

    @Test
    fun `test recipe update ingredients collection`() {
        val someCreatedIngredientRestIds: List<IngredientRestId> =
            ingredientDataCreator.createRandomIngredients().map { it.createdResponse.body }
        val recipeIngredients = someCreatedIngredientRestIds.map {
            RecipeIngredientDto(ingredientId = it, amount = randomBigDecimal())
        }

        val recipeSaveCommand = recipeDataCreator.newSaveCommand(ingredients = recipeIngredients)
        val createdResult: CreatedData<RecipeRestSaveCommand, RecipeRestId> =
            recipeDataCreator.createRecipe(recipeSaveCommand)
        val createdId: RecipeRestId = createdResult.createdResponse.body

        client.findById(createdId) // caching the result

        val anotherCreatedIngredientRestIds: List<IngredientRestId> =
            ingredientDataCreator.createRandomIngredients().map { it.createdResponse.body }
        val recipeIngredientsForUpdate = anotherCreatedIngredientRestIds.map {
            RecipeIngredientDto(ingredientId = it, amount = randomBigDecimal())
        }

        val recipeUpdateDto = recipeDataCreator.newSaveCommand(ingredients = recipeIngredientsForUpdate)

        client.validUpdate(createdId, recipeUpdateDto)

        val found: TestResponseWithBody<RecipeDto> = client.findById(createdId)

        recipeValidator.validate(found.body, recipeUpdateDto)
    }

    @Test
    fun `test recipe update with wrong parameter`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val createdResult: CreatedData<RecipeRestSaveCommand, RecipeRestId> =
            recipeDataCreator.createRecipe(recipeSaveCommand)
        val createdId: RecipeRestId = createdResult.createdResponse.body

        val invalidName = newInvalidName()
        val recipeUpdateDto = recipeDataCreator.newSaveCommand(invalidName)
        val updatedResponse = client.updateRequest(createdId, recipeUpdateDto, HttpStatus.BAD_REQUEST)

        webValidator.validateDefaultMaxSizeError(
            response = updatedResponse,
            path = "$RECIPE_PATH/$createdId",
            invalidValue = invalidName
        )
    }

    @Test
    fun `test recipe deletion`() {
        val recipeSaveCommand = recipeDataCreator.newSaveCommand()
        val created: TestResponseWithBody<RecipeRestId> = client.validCreate(recipeSaveCommand)
        val createdId: RecipeRestId = created.body
        assertNotNull(client.findById(createdId))

        client.deleteRequest(createdId)

        client.findNone(createdId)
    }

    @Test
    fun `test recipe deletion with missing id`() {
        recipeDataCreator.createRandomRecipes()

        client.deleteRequest(client.generateId(), HttpStatus.NOT_FOUND)
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

    @Test
    fun `test update with missing id`() {
        val missingId = RecipeRestId(UUID.randomUUID())

        client.updateRequest(
            missingId, RecipeRestSaveCommand(
                name = "missing id",
                content = "new content"
            ), HttpStatus.NOT_FOUND
        )
    }

    private fun createRandomAndFindOneByName(): RecipeDto {
        val createdData: List<CreatedData<RecipeRestSaveCommand, RecipeRestId>> =
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
