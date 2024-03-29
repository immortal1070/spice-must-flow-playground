package io.immortal.spicemustflow.infrastructure.configuration

import io.immortal.spicemustflow.application.ingredient.INGREDIENT_BY_ID_CACHE
import io.immortal.spicemustflow.application.ingredient.INGREDIENT_SEARCH_CACHE
import io.immortal.spicemustflow.application.recipe.RECIPE_BY_ID_CACHE
import io.immortal.spicemustflow.application.recipe.RECIPE_SEARCH_CACHE
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
                ConcurrentMapCache(INGREDIENT_BY_ID_CACHE),
                ConcurrentMapCache(INGREDIENT_SEARCH_CACHE),
                ConcurrentMapCache(RECIPE_SEARCH_CACHE),
                ConcurrentMapCache(RECIPE_BY_ID_CACHE)
            )
        )
        return cacheManager
    }
}