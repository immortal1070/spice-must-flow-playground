package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.common.exception.ObjectNotFoundException
import io.immortal.spicemustflow.common.stereotype.InfrastructureRepository
import io.immortal.spicemustflow.domain.recipe.*
import io.immortal.spicemustflow.infrastructure.common.persistence.generators.IdGenerator
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@InfrastructureRepository
class JpaRecipeRepository(
    private val baseRepository: RecipeBaseRepository,
    private val baseRecipeIngredientRepository: RecipeIngredientBaseRepository,
    private val transformer: RecipeJpaTransformer,
    private val idGenerator: IdGenerator
) : RecipeRepository {
    override fun findById(id: RecipeId): Recipe? =
        baseRepository.findByIdOrNull(id.uuid)?.toDomainObject()

    override fun find(query: RecipeQuery): List<Recipe> {
        return baseRepository.findAll(findRecipesSpec(query)).map {
            it.toDomainObject()
        }
    }

    override fun create(recipe: Recipe): Recipe {
        val savedRecipe: RecipeJpaEntity = baseRepository.save(transformer.toJpaEntity(recipe))
        if (recipe.ingredients.isNotEmpty()) {
            val createdRecipeId: UUID = getRecipeId(savedRecipe)
            createRecipeIngredients(createdRecipeId, recipe.ingredients)
        }
        return savedRecipe.toDomainObject()
    }

    override fun update(recipe: Recipe): Recipe {
        val savedRecipe: RecipeJpaEntity = baseRepository.save(transformer.toJpaEntity(recipe))
        if (recipe.ingredients.isNotEmpty()) {
            mergeRecipeIngredients(recipe)
        }
        return savedRecipe.toDomainObject()
    }

    override fun delete(id: RecipeId) {
        val existing: RecipeJpaEntity = getOrThrow(id)
        baseRepository.delete(existing)
    }

    override fun delete(ids: List<RecipeId>) {
        baseRepository.deleteAll(baseRepository.findAll(findByIdsSpec(ids)))
    }

    override fun generateId(): RecipeId {
        return RecipeId(idGenerator.generateUuid())
    }

    private fun getOrThrow(id: RecipeId): RecipeJpaEntity {
        return baseRepository.findById(id.uuid).orElseThrow {
            ObjectNotFoundException(
                name = "Recipe",
                id = id
            )
        }
    }

    private fun mergeRecipeIngredients(recipe: Recipe) {
        val toCreate: List<RecipeIngredient>
        val recipeJpaId: UUID = recipe.id.uuid
        val existingRecipeIngredients: List<RecipeIngredientJpaEntity> =
            baseRecipeIngredientRepository.findAllByIdRecipeId(recipeJpaId)
        if (existingRecipeIngredients.isNotEmpty()) {
            val toDelete: MutableList<UUID> = mutableListOf()
            val existingIds: MutableSet<UUID> = mutableSetOf()
            existingRecipeIngredients.forEach {
                val existingId: UUID = it.id.ingredientId
                val foundNewIngredient = recipe.ingredients.find { newRecipeIngredient ->
                    newRecipeIngredient.ingredientId.uuid == it.id.ingredientId
                }
                if (foundNewIngredient == null) {
                    toDelete.add(existingId)
                } else {
                    it.amount = foundNewIngredient.amount
                }
                existingIds.add(existingId)
            }
            toCreate = recipe.ingredients.filter {
                !existingIds.contains(it.ingredientId.uuid)
            }.toList()

            baseRecipeIngredientRepository.deleteAllById(toDelete.map {
                RecipeIngredientId(ingredientId = it, recipeId = recipeJpaId)
            })
        } else {
            toCreate = recipe.ingredients
        }

        if (toCreate.isNotEmpty()) {
            createRecipeIngredients(recipeJpaId, toCreate)
        }
    }

    private fun createRecipeIngredients(recipeId: UUID, recipeIngredients: List<RecipeIngredient>) {
        baseRecipeIngredientRepository.saveAll(
            recipeIngredients.map {
                RecipeIngredientJpaEntity(
                    RecipeIngredientId(
                        ingredientId = it.ingredientId.uuid,
                        recipeId = recipeId
                    ),
                    it.amount
                )
            }
        )
    }

    private fun getRecipeId(recipe: RecipeJpaEntity): UUID {
        return recipe.id ?: throw IllegalStateException(
            "UUID must be generated during entity creation"
        )
    }
}

interface RecipeBaseRepository : CrudRepository<RecipeJpaEntity, UUID>,
    JpaSpecificationExecutor<RecipeJpaEntity> {
}


interface RecipeIngredientBaseRepository : CrudRepository<RecipeIngredientJpaEntity, RecipeIngredientId>,
    JpaSpecificationExecutor<RecipeJpaEntity> {
    fun findAllByIdRecipeId(recipeId: UUID?): List<RecipeIngredientJpaEntity>
}