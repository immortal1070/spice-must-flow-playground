package io.immortal.spicemustflow.application.ingredient.cache

import io.immortal.spicemustflow.domain.ingredient.IngredientCreated
import io.immortal.spicemustflow.domain.ingredient.IngredientDeleted
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.ingredient.IngredientUpdated
import io.immortal.spicemustflow.util.logging.MethodLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
@MethodLogging
class IngredientCacheService {
    @CacheEvict(value = [INGREDIENT_BY_ID_CACHE])
    fun evictCacheById(id: IngredientId) {}

    @CacheEvict(value = [INGREDIENT_SEARCH_CACHE], allEntries = true)
    fun evictSearchCache() {}
}

// in separate class because of CacheEvict
// TODO check if internal methods can be used in the same class to evict cache
@Component
@MethodLogging
class IngredientCacheListeners(
    private val ingredientCacheService: IngredientCacheService
) {
    @TransactionalEventListener
    internal fun createdEvent(event: IngredientCreated) {
        evictCaches(event.ingredient.id)
    }

    @TransactionalEventListener
    internal fun updatedEvent(event: IngredientUpdated) {
        evictCaches(event.ingredient.id)
    }

    @TransactionalEventListener
    internal fun deletedEvent(event: IngredientDeleted) {
        evictCaches(event.ingredient.id)
    }

    internal fun evictCaches(id: IngredientId) {
        ingredientCacheService.evictCacheById(id)
        ingredientCacheService.evictSearchCache()
    }
}