package io.immortal.spicemustflow.application.ingredient

import io.immortal.spicemustflow.application.ingredient.cache.INGREDIENT_BY_ID_CACHE
import io.immortal.spicemustflow.application.ingredient.cache.INGREDIENT_SEARCH_CACHE
import io.immortal.spicemustflow.domain.ingredient.*
import io.immortal.spicemustflow.util.logging.MethodLogging
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@MethodLogging
@Validated
@Transactional
@CacheConfig(cacheNames = [INGREDIENT_SEARCH_CACHE, INGREDIENT_BY_ID_CACHE])
class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val transformer: IngredientTransformer,
    private val publisher: ApplicationEventPublisher
) {
    @Cacheable(INGREDIENT_BY_ID_CACHE)
    fun findById(id: IngredientId): Ingredient? = ingredientRepository.findById(id)

    @Cacheable(INGREDIENT_SEARCH_CACHE)
    fun find(findParams: IngredientQuery): List<Ingredient> = ingredientRepository.find(findParams)

    fun create(@Valid saveCommand: IngredientSaveCommand): IngredientId {
        val ingredient: Ingredient = transformer.toIngredient(
            ingredientRepository.generateId(), saveCommand
        )
        val saved: Ingredient = ingredientRepository.save(ingredient).also {
            publisher.publishEvent(IngredientCreated(IngredientEventDto(it)))
        }
        return saved.id
    }

    fun update(id: IngredientId, @Valid saveCommand: IngredientSaveCommand) {
        val ingredient: Ingredient = transformer.toIngredient(id, saveCommand)
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
}