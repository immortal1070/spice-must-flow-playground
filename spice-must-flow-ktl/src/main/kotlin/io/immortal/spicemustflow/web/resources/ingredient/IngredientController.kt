package io.immortal.spicemustflow.web.resources.ingredient

import io.immortal.spicemustflow.application.ingredient.IngredientService
import io.immortal.spicemustflow.common.constants.UUID_PATH
import io.immortal.spicemustflow.common.stereotype.WebRestController
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestQuery
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

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
        description = "search by parameters"
    )
    fun find(params: IngredientRestQuery): List<IngredientDto> {
        return ingredientService.find(transformer.toQuery(params)).map { transformer.toDto(it) }
    }

    @GetMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "getIngredientById"
    )
    fun get(@PathVariable id: IngredientId): IngredientDto? {
        return ingredientService.findById(id)?.let { transformer.toDto(it) }
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "createIngredient"
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody saveCommand: IngredientRestSaveCommand): IngredientId {
        return ingredientService.create(transformer.toSaveCommand(saveCommand))
    }

    @PutMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "updateIngredient"
    )
    fun update(@PathVariable id: IngredientId, @RequestBody saveCommand: IngredientRestSaveCommand) {
        ingredientService.update(id, transformer.toSaveCommand(saveCommand))
    }

    @DeleteMapping(UUID_PATH)
    @Operation(
        operationId = "deleteIngredientById"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: IngredientId) {
        ingredientService.delete(listOf(id))
    }

    @DeleteMapping
    @Operation(
        operationId = "deleteIngredients"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@RequestParam(name = "id") ids: List<IngredientId>) {
        ingredientService.delete(ids)
    }
}