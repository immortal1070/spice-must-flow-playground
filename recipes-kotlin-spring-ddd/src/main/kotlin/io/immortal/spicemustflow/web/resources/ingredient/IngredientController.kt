package io.immortal.spicemustflow.web.resources.ingredient

import io.immortal.spicemustflow.application.ingredient.IngredientService
import io.immortal.spicemustflow.common.stereotype.WebRestController
import io.immortal.spicemustflow.web.UUID_PATH
import io.immortal.spicemustflow.web.configuration.INGREDIENTS_TAG
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestQuery
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

const val INGREDIENT_PATH = "/v1/ingredients"

@RequestMapping(INGREDIENT_PATH)
@WebRestController
@Tag(name = INGREDIENTS_TAG)
class IngredientController(
    private val ingredientService: IngredientService,
    private val transformer: IngredientRestTransformer
) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "findIngredients",
        summary = "search ingredients by parameters. Parameters are joined with AND. Is some are not present - parameters are ignored",
        description = "Collections are joined with OR and parameters are joined with AND. " +
                "Is some are not present - parameters are ignored. Example: ?id=1,2&name=potato,cucumber parameters " +
                "will be transformed to (id = 1 OR id = 2) AND (name = potato OR name = cucumber)"
    )
    fun find(params: IngredientRestQuery): List<IngredientDto> {
        return ingredientService.find(transformer.toQuery(params)).map { transformer.toDto(it) }
    }

    @GetMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "getIngredientById",
        summary = "get an ingredient by id"
    )
    fun get(@PathVariable id: IngredientRestId): IngredientDto? {
        return ingredientService.findById(id.toIngredientId())?.let { transformer.toDto(it) }
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "createIngredient",
        summary = "create an ingredient"
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody saveCommand: IngredientRestSaveCommand): IngredientRestId {
        return IngredientRestId(ingredientService.create(transformer.toSaveCommand(saveCommand)))
    }

    @PutMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "updateIngredient",
        summary = "update an ingredient"
    )
    fun update(@PathVariable id: IngredientRestId, @RequestBody saveCommand: IngredientRestSaveCommand) {
        ingredientService.update(id.toIngredientId(), transformer.toSaveCommand(saveCommand))
    }

    @DeleteMapping(UUID_PATH)
    @Operation(
        operationId = "deleteIngredientById",
        summary = "delete an ingredient by id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: IngredientRestId) {
        ingredientService.delete(listOf(id).map { it.toIngredientId() })
    }

    @DeleteMapping
    @Operation(
        operationId = "deleteIngredients",
        summary = "delete a list of ingredients"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@RequestParam ids: List<IngredientRestId>) {
        ingredientService.delete(ids.map { it.toIngredientId() })
    }
}