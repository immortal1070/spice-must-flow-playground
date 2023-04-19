package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.application.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.application.ingredient.dto.IngredientSaveDto
import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientFindParams
import io.immortal.spicemustflow.domain.ingredient.IngredientService
import jakarta.validation.Valid
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
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
class IngredientController(private val ingredientService: IngredientService,
                           private val transformer: IngredientTransformer) {

    @GetMapping
    fun find(params: IngredientFindParams): List<IngredientDto> {
        return ingredientService.find(params).map { transformer.toDto(it) }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid ingredientDto: IngredientSaveDto): IngredientDto {
        return ingredientService.create(ingredientDto.name).let { transformer.toDto(it) }
    }

    @PutMapping(UUID_PATH)
    fun update(@PathVariable id: UUID, @RequestBody @Valid ingredientDto: IngredientSaveDto): IngredientDto {
        return ingredientService.update(id, ingredientDto.name).let { transformer.toDto(it) }
    }

    @DeleteMapping(UUID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        ingredientService.delete(listOf(id))
    }
// TODO rename param to "id" together with one from IngredientFindParams
    @ResponseStatus(HttpStatus.NO_CONTENT)
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