package io.immortal.spicemustflow.web.resources.recipe

import io.immortal.spicemustflow.application.recipe.RecipeService
import io.immortal.spicemustflow.common.constants.UUID_PATH
import io.immortal.spicemustflow.common.stereotype.WebRestController
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestQuery
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

const val RECIPE_PATH = "/v1/recipes"

@RequestMapping(RECIPE_PATH)
@WebRestController
@Tag(name = RECIPES_TAG)
class RecipeController(
    private val recipeService: RecipeService,
    private val transformer: RecipeRestTransformer
) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "findRecipes",
        summary = "search recipes by parameters. Parameters are joined with AND. Is some are not present - parameters are ignored",
        description = "search by parameters"
    )
    fun find(params: RecipeRestQuery): List<RecipeDto> {
        return recipeService.find(transformer.toQuery(params)).map { transformer.toDto(it) }
    }

    @GetMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "getRecipeById",
        summary = "get recipe by id"
    )
    fun get(@PathVariable id: RecipeId): RecipeDto? {
        return recipeService.findById(id)?.let { transformer.toDto(it) }
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "createRecipe",
        summary = "create a new recipe"
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody saveCommand: RecipeRestSaveCommand): RecipeId {
        return recipeService.create(transformer.toSaveCommand(saveCommand))
    }

    @PutMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "updateRecipe",
        summary = "update a recipe - fully replace it contents and the list of ingredients"
    )
    fun update(@PathVariable id: RecipeId, @RequestBody saveCommand: RecipeRestSaveCommand) {
        recipeService.update(id, transformer.toSaveCommand(saveCommand))
    }

    @DeleteMapping(UUID_PATH)
    @Operation(
        operationId = "deleteRecipeById",
        summary = "delete a recipe by id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: RecipeId) {
        recipeService.delete(listOf(id))
    }

    @DeleteMapping
    @Operation(
        operationId = "deleteRecipes",
        summary = "delete a list of recipes"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@RequestParam(name = "id") ids: List<RecipeId>) {
        recipeService.delete(ids)
    }
}