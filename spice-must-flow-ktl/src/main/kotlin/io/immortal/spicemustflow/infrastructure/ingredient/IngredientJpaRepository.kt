package io.immortal.spicemustflow.infrastructure.ingredient

import io.immortal.spicemustflow.domain.ingredient.Ingredient
import io.immortal.spicemustflow.domain.ingredient.IngredientFindParams
import io.immortal.spicemustflow.domain.ingredient.IngredientRepository
import io.immortal.spicemustflow.infrastructure.exception.ObjectNotFoundException
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.reflect.KProperty1

fun <T, V> Root<T>.get(prop: KProperty1<T, V>): Path<V> = this.get(prop.name)

//fun MutableList<Predicate>.where(predicate: Predicate, addWhen: Boolean) {
//    if (addWhen) {
//        this.add(predicate)
//    }
//    value?.let {
//        this.add(predicate)
//    }
//}
// TODO move
fun findIngredientsSpec(params: IngredientFindParams): Specification<IngredientModel> {
    return Specification<IngredientModel> { root, _, builder ->
        with(builder) {
            val predicates: MutableList<Predicate> = mutableListOf()
            params.names?.let { predicates.add(root.get(IngredientModel::name).`in`(params.names)) }
            params.ids?.let { predicates.add(root.get(IngredientModel::id).`in`(params.ids)) }
            and(*predicates.toTypedArray())
        }
    }
}

fun findByIdsSpec(ids: List<UUID>?): Specification<IngredientModel> {
    return Specification<IngredientModel> { root, _, _ ->
        root.get(IngredientModel::id).`in`(ids)
    }
}


@Repository
class JpaIngredientRepository(
    private val baseRepository: IngredientBaseRepository,
    private val transformer: IngredientModelTransformer
) : IngredientRepository {
    override fun findById(id: UUID): Ingredient? =
        baseRepository.findByIdOrNull(id)?.let {
            println("createdAt=${it.createdAt}")
            transformer.toIngredient(it)
        }

    override fun find(params: IngredientFindParams): List<Ingredient> {
        return baseRepository.findAll(findIngredientsSpec(params)).map {
            transformer.toIngredient(it)
        }
    }

//    override fun findByName(name: String?): Ingredient? =
//        baseRepository.findByName(name)?.let {
//            TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
//
//            println("createdAt=${it.createdAt}")
//            transformer.toIngredient(it)
//        }

    override fun create(name: String): Ingredient {
        //TODO remove after the date test is done
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"))

        return baseRepository.save(
            IngredientModel(name = name)
        ).let {
            transformer.toIngredient(it)
        }
    }

    override fun delete(id: UUID) {
        val existing: IngredientModel = getById(id)
        baseRepository.delete(existing)
    }

    override fun delete(ids: List<UUID>) {
/* TODO check why delete by spec is not working -
        java.lang.NullPointerException: Parameter specified as non-null is null: method io.immortal.spicemustflow.infrastructure.ingredient.IngredientJpaRepositoryKt.findByIdsSpec$lambda$4, parameter query
        at io.immortal.spicemustflow.infrastructure.ingredient.IngredientJpaRepositoryKt.findByIdsSpec$lambda$4(IngredientJpaRepository.kt)
        at org.springframework.data.jpa.repository.support.SimpleJpaRepository.delete(SimpleJpaRepository.java:527)
*/
//        baseRepository.delete(findByIdsSpec(ids))
        baseRepository.deleteAll(baseRepository.findAll(findByIdsSpec(ids)))
    }

    override fun update(id: UUID, name: String): Ingredient {
        val existing: IngredientModel = getById(id)
        existing.name = name
        return baseRepository.save(existing).let {
            transformer.toIngredient(it)
        }
    }

    private fun getById(id: UUID): IngredientModel {
        return baseRepository.findById(id).orElseThrow {
            ObjectNotFoundException(
                name = "Ingredient",
                id = id
            )
        }
    }
}

interface IngredientBaseRepository : CrudRepository<IngredientModel, UUID>,
    JpaSpecificationExecutor<IngredientModel> {
    fun findByName(name: String?): IngredientModel?
}

@Component
class IngredientModelTransformer {
    fun toIngredient(entity: IngredientModel): Ingredient = entity.run {
        Ingredient(
            id = id!!, //TODO find better way
            name = name,
        )
    }
}