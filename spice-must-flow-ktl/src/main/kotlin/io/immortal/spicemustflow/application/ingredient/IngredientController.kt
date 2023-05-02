package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.IngredientFindParamsJava
import io.immortal.spicemustflow.application.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.application.ingredient.dto.IngredientSaveDto
import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientFindParams
import io.immortal.spicemustflow.domain.ingredient.IngredientService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"
const val ID_PARAM = "id"
const val UUID_PATH =
    "/{" + ID_PARAM + ":" + UUID_REGEX + "}"

@RequestMapping("/v1/ingredients")
@RestController
@Validated
@Tag(name = "Ingredients")
class IngredientController(private val ingredientService: IngredientService,
                           private val transformer: IngredientTransformer) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "search ingredients by parameters. Parameters are joined with AND. Is some are not present - parameters are ignored",
        operationId = "findIngredients",
        description = "search by parameters"
    )
    fun find(params: IngredientFindParams): List<IngredientDto> {
        return ingredientService.find(params).map { transformer.toDto(it) }
    }
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "createIngredient"
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid ingredientDto: IngredientSaveDto): IngredientDto {
        return ingredientService.create(ingredientDto.name).let { transformer.toDto(it) }
    }

    @PutMapping(UUID_PATH, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "updateIngredient"
    )
    fun update(@PathVariable id: UUID, @RequestBody @Valid ingredientDto: IngredientSaveDto): IngredientDto {
        return ingredientService.update(id, ingredientDto.name).let { transformer.toDto(it) }
    }

    @DeleteMapping(UUID_PATH)
    @Operation(
        operationId = "deleteIngredient"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        ingredientService.delete(listOf(id))
    }

    @DeleteMapping
    @Operation(
        operationId = "deleteIngredients"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // TODO rename param to "id" together with one from IngredientFindParams
    fun delete(@RequestParam ids: List<UUID>) {
        ingredientService.delete(ids)
    }
}

@Component
class IngredientTransformer {
    fun toDto(entity: Ingredient): IngredientDto = entity.run { IngredientDto(
        id = id,
        name = name,
    )}
}