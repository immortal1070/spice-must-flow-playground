package io.immortal.spicemustflow.application.recipe

import io.immortal.spicemustflow.application.recipe.cache.RECIPE_BY_ID_CACHE
import io.immortal.spicemustflow.application.recipe.cache.RECIPE_SEARCH_CACHE
import io.immortal.spicemustflow.common.stereotype.ApplicationService
import io.immortal.spicemustflow.domain.recipe.*
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher

@ApplicationService
@CacheConfig(cacheNames = [RECIPE_SEARCH_CACHE, RECIPE_BY_ID_CACHE])
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val publisher: ApplicationEventPublisher
) {
    @Cacheable(RECIPE_BY_ID_CACHE)
    fun findById(id: RecipeId): Recipe? = recipeRepository.findById(id)

    @Cacheable(RECIPE_SEARCH_CACHE)
    fun find(findParams: RecipeQuery): List<Recipe> = recipeRepository.find(findParams)

    fun create(@Valid saveCommand: RecipeSaveCommand): RecipeId {
        val recipe: Recipe = toRecipe(
            recipeRepository.generateId(), saveCommand
        )
        val saved: Recipe = recipeRepository.create(recipe).also {
            publisher.publishEvent(RecipeCreated(RecipeEventDto(it)))
        }
        return saved.id
    }

    //   TODO add @RecipeId validation
    fun update(id: RecipeId, @Valid saveCommand: RecipeSaveCommand) {
        val recipe: Recipe = toRecipe(id, saveCommand)
        recipeRepository.update(recipe).also {
            publisher.publishEvent(RecipeUpdated(RecipeEventDto(it)))
        }
    }

    fun delete(ids: List<RecipeId>) {
        recipeRepository.find(RecipeQuery(ids = ids))
            .forEach {
                publisher.publishEvent(RecipeDeleted(RecipeEventDto(it)))
            }
        recipeRepository.delete(ids)
    }

    private fun toRecipe(id: RecipeId, saveCommand: RecipeSaveCommand): Recipe = saveCommand.run {
        Recipe(
            id = id,
            name = name,
            content = content,
            cookingMinutes = cookingMinutes,
            ingredients = ingredients
        )
    }
}
