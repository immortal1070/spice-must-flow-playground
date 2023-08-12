package io.immortal.spicemustflow.application.ingredient.cache

import io.immortal.spicemustflow.common.logging.MethodLogging
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.ingredient.IngredientCreated
import io.immortal.spicemustflow.domain.ingredient.IngredientDeleted
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.ingredient.IngredientUpdated
import org.springframework.cache.annotation.CacheEvict
import org.springframework.transaction.event.TransactionalEventListener

@ApplicationScoped
@MethodLogging
class IngredientCacheService {
    @CacheEvict(value = [INGREDIENT_BY_ID_CACHE])
    fun evictCacheById(id: IngredientId) {
    }

    @CacheEvict(value = [INGREDIENT_SEARCH_CACHE], allEntries = true)
    fun evictSearchCache() {
    }
}

// in separate class because of CacheEvict
@ApplicationScoped
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