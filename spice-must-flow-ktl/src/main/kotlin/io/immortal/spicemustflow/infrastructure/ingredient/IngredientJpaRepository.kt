package io.immortal.spicemustflow.infrastructure.ingredient

import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.ingredient.IngredientQuery
import io.immortal.spicemustflow.domain.ingredient.IngredientRepository
import io.immortal.spicemustflow.infrastructure.exception.ObjectNotFoundException
import io.immortal.spicemustflow.util.generators.IdGenerator
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JpaIngredientRepository(
    private val baseRepository: IngredientBaseRepository,
    private val transformer: IngredientJpaTransformer,
    private val idGenerator: IdGenerator
) : IngredientRepository {
    override fun findById(id: IngredientId): Ingredient? =
        baseRepository.findByIdOrNull(id.uuid)?.let {
            println("createdAt=${it.createdAt}")
            transformer.toIngredient(it)
        }

    override fun find(query: IngredientQuery): List<Ingredient> {
        return baseRepository.findAll(findIngredientsSpec(query)).map {
            transformer.toIngredient(it)
        }
    }

    override fun save(ingredient: Ingredient): Ingredient {
        //TODO remove after the date test is done
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"))

        return baseRepository.save(
            transformer.toJpaEntity(ingredient)
        ).let {
            transformer.toIngredient(it)
        }
    }

    override fun delete(id: IngredientId) {
        val existing: IngredientJpaEntity = getById(id)
        baseRepository.delete(existing)
    }

    override fun delete(ids: List<IngredientId>) {
        /* TODO check why delete by spec is not working -
                java.lang.NullPointerException: Parameter specified as non-null is null: method io.immortal.spicemustflow.infrastructure.ingredient.IngredientJpaRepositoryKt.findByIdsSpec$lambda$4, parameter query
                at io.immortal.spicemustflow.infrastructure.ingredient.IngredientJpaRepositoryKt.findByIdsSpec$lambda$4(IngredientJpaRepository.kt)
                at org.springframework.data.jpa.repository.support.SimpleJpaRepository.delete(SimpleJpaRepository.java:527)
        */
//        baseRepository.delete(findByIdsSpec(ids))
        baseRepository.deleteAll(baseRepository.findAll(findByIdsSpec(ids)))
    }

    override fun generateId(): IngredientId {
        return IngredientId(idGenerator.generateUuid())
    }

    private fun getById(id: IngredientId): IngredientJpaEntity {
        return baseRepository.findById(id.uuid).orElseThrow {
            ObjectNotFoundException(
                name = "Ingredient",
                id = id
            )
        }
    }
}

interface IngredientBaseRepository : CrudRepository<IngredientJpaEntity, UUID>,
    JpaSpecificationExecutor<IngredientJpaEntity> {
    fun findByName(name: String?): IngredientJpaEntity?
}