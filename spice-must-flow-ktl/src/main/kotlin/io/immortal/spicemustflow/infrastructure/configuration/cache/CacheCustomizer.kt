//package io.immortal.spicemustflow.infrastructure.configuration.cache
//
//import io.immortal.spicemustflow.domain.ingredient.IngredientCaches
//import io.immortal.spicemustflow.domain.ingredient.IngredientFindParams
//import org.ehcache.config.builders.CacheConfigurationBuilder
//import org.ehcache.config.builders.ResourcePoolsBuilder
//import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer
//import org.springframework.stereotype.Component
//import javax.cache.CacheManager
//import javax.cache.configuration.MutableConfiguration
//
//@Component
//class CacheCustomizer : JCacheManagerCustomizer {
//    override fun customize(cacheManager: CacheManager?) {
////        cacheManager?.cacheNames.add(IngredientCaches.INGREDIENT_CACHE)
////        cacheManager?.cacheNames.add(IngredientCaches.INGREDIENT_CACHE)
//
//
////        cacheManager?.createCache(
////            IngredientCaches.INGREDIENT_CACHE, CacheConfigurationBuilder
////                .newCacheConfigurationBuilder(
////                    IngredientFindParams::class.java, List::class.java,
////                    ResourcePoolsBuilder.heap(10)).build())
//
//    }
//}