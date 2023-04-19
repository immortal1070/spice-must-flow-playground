package io.immortal.spicemustflow.domain.ingredient.listener

import io.immortal.spicemustflow.domain.ingredient.IngredientCaches.Companion.INGREDIENT_CACHE
import io.immortal.spicemustflow.domain.ingredient.event.IngredientCreated
import io.immortal.spicemustflow.domain.ingredient.event.IngredientDeleted
import io.immortal.spicemustflow.domain.ingredient.event.IngredientUpdated
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

private val logger = KotlinLogging.logger {}

@Component
class IngredientCacheListeners {
    @TransactionalEventListener
    @CacheEvict(value = [INGREDIENT_CACHE], allEntries = true)
    fun createdEvent(createdEvent: IngredientCreated) {
        logger.debug { "createdEvent clean cache" }
    }

    @TransactionalEventListener
    @CacheEvict(value = [INGREDIENT_CACHE], allEntries = true)
    fun updatedEvent(createdEvent: IngredientUpdated) {
        logger.debug { "updatedEvent clean cache" }
    }

    @TransactionalEventListener
    @CacheEvict(value = [INGREDIENT_CACHE], allEntries = true)
    fun deletedEvent(createdEvent: IngredientDeleted) {
        logger.debug { "deletedEvent clean cache" }
    }
}