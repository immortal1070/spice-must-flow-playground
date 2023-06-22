package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.application.ingredient.cache.INGREDIENT_BY_ID_CACHE
import io.immortal.spicemustflow.application.ingredient.cache.INGREDIENT_SEARCH_CACHE
import io.immortal.spicemustflow.common.stereotype.ApplicationService
import io.immortal.spicemustflow.domain.ingredient.*
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import java.util.*

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

//    fun create(@Valid saveCommand: IngredientSaveCommand): IngredientId {
//        val created: Ingredient = ingredientRepository.create(toRepoCommand(saveCommand)).also {
//            publisher.publishEvent(IngredientCreated(IngredientEventDto(it)))
//        }
//        return created.id
//    }
//
//    //   TODO add @IngredientId validation
//    fun update( id: IngredientId, @Valid saveCommand: IngredientSaveCommand) {
//        ingredientRepository.update(id, toRepoCommand(saveCommand)).also {
//            publisher.publishEvent(IngredientUpdated(IngredientEventDto(it)))
//        }
//    }

    fun create(@Valid saveCommand: IngredientSaveCommand): IngredientId {
        val ingredient: Ingredient = toIngredient(
            ingredientRepository.generateId(), saveCommand
        )
        val saved: Ingredient = ingredientRepository.save(ingredient).also {
            publisher.publishEvent(IngredientCreated(IngredientEventDto(it)))
        }
        return saved.id
    }
//   TODO add @IngredientId validation
    fun update( id: IngredientId, @Valid saveCommand: IngredientSaveCommand) {
        val ingredient: Ingredient = toIngredient(id, saveCommand)
        ingredientRepository.save(ingredient).also {
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

//    private fun toRepoCommand(saveCommand: IngredientSaveCommand): IngredientRepoSaveCommand = saveCommand.run {
//        IngredientRepoSaveCommand(
//            name = name
//        )
//    }
}