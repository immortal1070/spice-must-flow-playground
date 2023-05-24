package io.immortal.spicemustflow.infrastructure.ingredient

import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.ingredient.IngredientQuery
import io.immortal.spicemustflow.util.get
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

fun findIngredientsSpec(query: IngredientQuery): Specification<IngredientJpaEntity> {
    return Specification<IngredientJpaEntity> { root, _, builder ->
        with(builder) {
            val predicates: MutableList<Predicate> = mutableListOf()
            query.names?.let { predicates.add(root.get(IngredientJpaEntity::name).`in`(query.names)) }
            query.ids?.let {
                predicates.add(root.get(IngredientJpaEntity::id).`in`(query.ids.map { it.uuid }))
            }
            and(*predicates.toTypedArray())
        }
    }
}

fun findByIdsSpec(ids: List<IngredientId>?): Specification<IngredientJpaEntity> {
    return Specification<IngredientJpaEntity> { root, _, _ ->
        root.get(IngredientJpaEntity::id).`in`(ids?.map { it.uuid })
    }
}