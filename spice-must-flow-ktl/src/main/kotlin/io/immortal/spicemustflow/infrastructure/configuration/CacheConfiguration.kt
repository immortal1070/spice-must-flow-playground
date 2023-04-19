package io.immortal.spicemustflow.infrastructure.configuration

import io.immortal.spicemustflow.domain.ingredient.IngredientCaches
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheConfiguration {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(
            listOf(
                ConcurrentMapCache(IngredientCaches.INGREDIENT_CACHE)
            )
        )
        return cacheManager
    }
}