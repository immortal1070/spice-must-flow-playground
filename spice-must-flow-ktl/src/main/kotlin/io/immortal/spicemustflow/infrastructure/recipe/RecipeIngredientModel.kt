package io.immortal.spicemustflow.infrastructure.recipe

import io.immortal.spicemustflow.infrastructure.ingredient.IngredientJpaEntity
import io.immortal.spicemustflow.util.persistence.BaseJpaEntity
import io.immortal.spicemustflow.util.persistence.BaseJpaEntityListener
import io.immortal.spicemustflow.util.persistence.ColumnConstants
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

//TODO find better place for it
private const val RECIPE_ID_COLUMN = "recipe_id"
private const val INGREDIENT_ID_COLUMN = "ingredient_id"

@Entity
@Table(name = "recipe_ingredient")
@EntityListeners(BaseJpaEntityListener::class)
class RecipeIngredientModel(

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = RECIPE_ID_COLUMN, nullable = false, updatable = false)
    private val recipe: RecipeModel,

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = INGREDIENT_ID_COLUMN, nullable = false, updatable = false)
    private val ingredient: IngredientJpaEntity,

        @Column(name = "amount", nullable = false)
    var amount: Float,

        @Column(name = "measurement_unit", nullable = false)
    var measurementUnit: String

    ) : BaseJpaEntity {

    @Id
    @GeneratedValue
    val id: UUID? = null

    //for getting the id in the code without fetching a related entity
    @Column(name = RECIPE_ID_COLUMN, nullable = false, updatable = false, insertable = false)
    val recipeId: UUID? = null

    //for getting the id in the code without fetching a related entity
    @Column(name = INGREDIENT_ID_COLUMN, nullable = false, updatable = false, insertable = false)
    val ingredientId: UUID? = null

    @Column(name = ColumnConstants.CREATOR, updatable = false)
    override var creator: String? = null

    @Column(name = ColumnConstants.CREATED_AT, updatable = false)
    override var createdAt: OffsetDateTime? = null

    @Column(name = ColumnConstants.UPDATER)
    override var updater: String? = null

    @Column(name = ColumnConstants.UPDATED_AT)
    override var updatedAt: OffsetDateTime? = null
}