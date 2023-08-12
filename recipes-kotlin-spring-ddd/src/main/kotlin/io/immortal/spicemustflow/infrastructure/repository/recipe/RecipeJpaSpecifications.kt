package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.domain.recipe.RecipeQuery
import io.immortal.spicemustflow.infrastructure.common.persistence.get
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

fun findRecipesSpec(query: RecipeQuery): Specification<RecipeJpaEntity> {
    return Specification<RecipeJpaEntity> { root, _, builder ->
        with(builder) {
            val predicates: MutableList<Predicate> = mutableListOf()
            query.names?.let { predicates.add(root.get(RecipeJpaEntity::name).`in`(it)) }
            query.contents?.let { predicates.add(root.get(RecipeJpaEntity::content).`in`(it)) }
            query.ids?.let {
                predicates.add(root.get(RecipeJpaEntity::id).`in`(it.map { id -> id.uuid }))
            }
            and(*predicates.toTypedArray())
        }
    }
}

fun findByIdsSpec(ids: List<RecipeId>?): Specification<RecipeJpaEntity> {
    return Specification<RecipeJpaEntity> { root, _, _ ->
        root.get(RecipeJpaEntity::id).`in`(ids?.map { it.uuid })
    }
}