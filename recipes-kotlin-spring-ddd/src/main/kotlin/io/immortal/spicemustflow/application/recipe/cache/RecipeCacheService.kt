package io.immortal.spicemustflow.application.recipe.cache

import io.immortal.spicemustflow.common.logging.MethodLogging
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.recipe.RecipeCreated
import io.immortal.spicemustflow.domain.recipe.RecipeDeleted
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.domain.recipe.RecipeUpdated
import org.springframework.cache.annotation.CacheEvict
import org.springframework.transaction.event.TransactionalEventListener

@ApplicationScoped
@MethodLogging
class RecipeCacheService {
    @CacheEvict(value = [RECIPE_BY_ID_CACHE])
    fun evictCacheById(id: RecipeId) {
    }

    @CacheEvict(value = [RECIPE_SEARCH_CACHE], allEntries = true)
    fun evictSearchCache() {
    }
}

// in separate class because of CacheEvict
@ApplicationScoped
@MethodLogging
class RecipeCacheListeners(
    private val recipeCacheService: RecipeCacheService
) {
    @TransactionalEventListener
    internal fun createdEvent(event: RecipeCreated) {
        evictCaches(event.recipe.id)
    }

    @TransactionalEventListener
    internal fun updatedEvent(event: RecipeUpdated) {
        evictCaches(event.recipe.id)
    }

    @TransactionalEventListener
    internal fun deletedEvent(event: RecipeDeleted) {
        evictCaches(event.recipe.id)
    }

    internal fun evictCaches(id: RecipeId) {
        recipeCacheService.evictCacheById(id)
        recipeCacheService.evictSearchCache()
    }
}