package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.domain.recipe.Recipe
import io.immortal.spicemustflow.domain.recipe.RecipeRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class RecipeJpaRepository(
    private val recipeBaseRepository: RecipeBaseRepository,
    private val recipeIngredientBaseRepository: RecipeIngredientBaseRepository,
    private val transformer: RecipeModelTransformer
) : RecipeRepository {
    override fun findById(id: UUID): Recipe? =
        recipeBaseRepository.findByIdOrNull(id)?.let {
            println("createdAt=${it.createdAt}")
            transformer.toDomainObject(it)
        }

    override fun findByName(name: String?): Recipe? =
        recipeBaseRepository.findByName(name)?.let {
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

            println("createdAt=${it.createdAt}")
            transformer.toDomainObject(it) }

    override fun create(name: String): Recipe {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

        return transformer.toDomainObject(
//            null
            recipeBaseRepository.save(
                RecipeModel(name = name)
            )
        )
    }
}

interface RecipeBaseRepository : CrudRepository<RecipeModel, UUID>
{
    fun findByName(name: String?): RecipeModel?
}

interface RecipeIngredientBaseRepository : CrudRepository<RecipeIngredientModel, UUID>
{
}

@Component
class RecipeModelTransformer {
    fun toDomainObject(entity: RecipeModel): Recipe = entity.run { Recipe(
        id = id!!, //TODO find better way
        name = name,
    )}
}