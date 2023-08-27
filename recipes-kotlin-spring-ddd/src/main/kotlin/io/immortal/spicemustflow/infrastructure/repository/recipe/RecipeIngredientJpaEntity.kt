package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.infrastructure.common.persistence.BaseNotUpdatableJpaEntity
import io.immortal.spicemustflow.infrastructure.common.persistence.BaseNotUpdatableJpaEntityListener
import io.immortal.spicemustflow.infrastructure.common.persistence.JpaCreateInfo
import io.immortal.spicemustflow.infrastructure.repository.ingredient.IngredientJpaEntity
import jakarta.persistence.*
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

private const val COLUMN_RECIPE_ID = "recipe_id"
private const val COLUMN_INGREDIENT_ID = "ingredient_id"

@Embeddable
class RecipeIngredientId(
    @Column(name = COLUMN_RECIPE_ID)
    val recipeId: UUID,
    @Column(name = COLUMN_INGREDIENT_ID)
    val ingredientId: UUID
) : Serializable

@Entity
@Table(name = "recipe_ingredient")
@EntityListeners(BaseNotUpdatableJpaEntityListener::class)
class RecipeIngredientJpaEntity(
    @EmbeddedId
    val id: RecipeIngredientId,

    @Column(name = "amount")
    var amount: BigDecimal
) : BaseNotUpdatableJpaEntity {

    @ManyToOne
    @JoinColumn(name = COLUMN_RECIPE_ID, insertable = false, updatable = false)
    var recipe: RecipeJpaEntity? = null
        set(_) {
            throw IllegalAccessError("this relation is set using embedded id")
        }

    @ManyToOne
    @JoinColumn(name = COLUMN_INGREDIENT_ID, insertable = false, updatable = false)
    var ingredient: IngredientJpaEntity? = null
        set(_) {
            throw IllegalAccessError("this relation is set using embedded id")
        }

    @Embedded
    override var createInfo: JpaCreateInfo? = null
}

