package io.immortal.spicemustflow.domain.ingredient

import io.immortal.spicemustflow.domain.ingredient.IngredientCaches.Companion.INGREDIENT_CACHE
import io.immortal.spicemustflow.domain.ingredient.event.IngredientCreated
import io.immortal.spicemustflow.domain.ingredient.event.IngredientDeleted
import io.immortal.spicemustflow.domain.ingredient.event.IngredientEventDto
import io.immortal.spicemustflow.domain.ingredient.event.IngredientUpdated
import io.immortal.spicemustflow.util.logging.MethodLogging
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@MethodLogging
@Validated
@Transactional
@CacheConfig(cacheNames = [INGREDIENT_CACHE])
class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val publisher: ApplicationEventPublisher
) {
    @Cacheable
    fun findById(id: UUID): Ingredient? = ingredientRepository.findById(id)

    @Cacheable
    fun find(findParams: IngredientFindParams): List<Ingredient> = ingredientRepository.find(findParams)

    // TODO add input dto
    fun create(name: String): Ingredient = ingredientRepository.create(name).also {
        publisher.publishEvent(IngredientCreated(IngredientEventDto(it)))
    }

    fun update(id: UUID, name: String): Ingredient = ingredientRepository.update(id, name).also {
        publisher.publishEvent(IngredientUpdated(IngredientEventDto(it)))
    }

    fun delete(ids: List<UUID>) {
        ingredientRepository.find(IngredientFindParams(ids = ids))
            .forEach {
                publisher.publishEvent(IngredientDeleted(IngredientEventDto(it)))
            }
        ingredientRepository.delete(ids)
    }
}