package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.application.recipe.validation.ValidIngredientId
import io.immortal.spicemustflow.common.stereotype.ApplicationService
import io.immortal.spicemustflow.domain.ingredient.*
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher

@ApplicationService
@CacheConfig(cacheNames = [INGREDIENT_SEARCH_CACHE, INGREDIENT_BY_ID_CACHE])
class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val publisher: ApplicationEventPublisher
) {
    @Cacheable(INGREDIENT_BY_ID_CACHE)
    fun findById(id: IngredientId): Ingredient? = ingredientRepository.findById(id)

    @Cacheable(INGREDIENT_SEARCH_CACHE)
    fun find(findParams: IngredientQuery): List<Ingredient> = ingredientRepository.find(findParams)

    fun create(@Valid saveCommand: IngredientSaveCommand): IngredientId {
        val newId: IngredientId = ingredientRepository.generateId()
        val ingredient: Ingredient = toIngredient(
            newId, saveCommand
        )
        ingredientRepository.create(ingredient).also {
            publisher.publishEvent(IngredientCreated(IngredientEventDto(it)))
        }
        return newId
    }

    fun update(@ValidIngredientId id: IngredientId, @Valid saveCommand: IngredientSaveCommand) {
        val ingredient: Ingredient = toIngredient(id, saveCommand)
        ingredientRepository.update(ingredient).also {
            publisher.publishEvent(IngredientUpdated(IngredientEventDto(it)))
        }
    }

    fun delete(ids: List<IngredientId>) {
        ingredientRepository.find(IngredientQuery(ids = ids))
            .forEach {
                publisher.publishEvent(IngredientDeleted(IngredientEventDto(it)))
            }
        ingredientRepository.delete(ids)
    }

    private fun toIngredient(id: IngredientId, saveCommand: IngredientSaveCommand): Ingredient = saveCommand.run {
        Ingredient(
            id = id,
            name = name
        )
    }
}