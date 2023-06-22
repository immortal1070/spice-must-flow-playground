package io.immortal.spicemustflow.infrastructure.repository.ingredient

import io.immortal.spicemustflow.common.exception.ObjectNotFoundException
import io.immortal.spicemustflow.common.stereotype.InfrastructureRepository
import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.ingredient.IngredientQuery
import io.immortal.spicemustflow.domain.ingredient.IngredientRepository
import io.immortal.spicemustflow.infrastructure.common.persistence.generators.IdGenerator
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@InfrastructureRepository
class JpaIngredientRepository(
    private val baseRepository: IngredientBaseRepository,
    private val transformer: IngredientJpaTransformer,
    private val idGenerator: IdGenerator
) : IngredientRepository {
    override fun findById(id: IngredientId): Ingredient? =
        baseRepository.findByIdOrNull(id.uuid)?.let {
            println("createdAt=${it.createdAt}")
            it.toDomainObject()
        }

    override fun find(query: IngredientQuery): List<Ingredient> {
        return baseRepository.findAll(findIngredientsSpec(query)).map {
            it.toDomainObject()
        }
    }

//    override fun create(saveCommand: IngredientRepoSaveCommand): Ingredient {
//        return save(generateId(), saveCommand)
//    }
//
//    override fun update(id: IngredientId, saveCommand: IngredientRepoSaveCommand): Ingredient {
//        return save(id, saveCommand)
//    }

    override fun save(ingredient: Ingredient): Ingredient {
        //TODO remove after the date test is done
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"))

        return baseRepository.save(transformer.toJpaEntity(ingredient)).toDomainObject()
    }
//
//    fun save(id: IngredientId, saveCommand: IngredientRepoSaveCommand): Ingredient {
//        return baseRepository.save(transformer.toJpaEntity(id, saveCommand)).toDomainObject()
//    }

    override fun delete(id: IngredientId) {
        val existing: IngredientJpaEntity = getOrThrow(id)
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

    private fun getOrThrow(id: IngredientId): IngredientJpaEntity {
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

//private fun toIngredient(id: IngredientId, saveCommand: IngredientRepoSaveCommand): Ingredient = saveCommand.run {
//    Ingredient(
//        id = id,
//        name = name
//    )
//}
//
private fun Ingredient.toJpaEntity(): IngredientJpaEntity {
    val entity = IngredientJpaEntity(name)
    entity.id = id.uuid
    return entity
}