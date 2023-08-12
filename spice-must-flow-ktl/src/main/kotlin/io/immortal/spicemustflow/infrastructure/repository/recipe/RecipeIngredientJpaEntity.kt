package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.infrastructure.common.persistence.BaseNotUpdatableJpaEntity
import io.immortal.spicemustflow.infrastructure.common.persistence.BaseNotUpdatableJpaEntityListener
import io.immortal.spicemustflow.infrastructure.common.persistence.JpaCreateInfo
import io.immortal.spicemustflow.infrastructure.repository.ingredient.IngredientJpaEntity
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Embeddable
class RecipeIngredientId(
    @Column(name = "recipe_id")
    val recipeId: UUID,
    @Column(name = "ingredient_id")
    val ingredientId: UUID
) : Serializable

@Entity
@Table(name = "recipe_ingredient")
@EntityListeners(BaseNotUpdatableJpaEntityListener::class)
class RecipeIngredientJpaEntity(
    @EmbeddedId
    val id: RecipeIngredientId,

    @Column(name = "amount")
    var amount: Double
) : BaseNotUpdatableJpaEntity {

    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    var recipe: RecipeJpaEntity? = null
        set(value) {
            throw IllegalAccessError("this relation is set using embedded id")
        }

    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    var ingredient: IngredientJpaEntity? = null
        set(value) {
            throw IllegalAccessError("this relation is set using embedded id")
        }

    @Embedded
    override var createInfo: JpaCreateInfo? = null
}

