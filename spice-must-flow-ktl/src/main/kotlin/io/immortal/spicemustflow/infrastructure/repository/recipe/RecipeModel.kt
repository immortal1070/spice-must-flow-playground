package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.infrastructure.common.persistence.BaseJpaEntity
import io.immortal.spicemustflow.infrastructure.common.persistence.BaseJpaEntityListener
import io.immortal.spicemustflow.infrastructure.common.persistence.ColumnConstants
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "recipe")
@EntityListeners(BaseJpaEntityListener::class)
class RecipeModel (
    @Column(name = "name", nullable = false, unique = true)
    val name: String,

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    private var recipeIngredients: MutableList<RecipeIngredientModel>? = null

) : BaseJpaEntity {

    @Id @GeneratedValue
    val id: UUID? = null

    @Column(name = ColumnConstants.CREATOR, updatable = false)
    override var creator: String? = null

    @Column(name = ColumnConstants.CREATED_AT, updatable = false)
    override var createdAt: OffsetDateTime? = null

    @Column(name = ColumnConstants.UPDATER)
    override var updater: String? = null

    @Column(name = ColumnConstants.UPDATED_AT)
    override var updatedAt: OffsetDateTime? = null

    // getter is private to avoid performance issues with loading lazy collections in the loop
    fun setRecipeIngredients(recipeIngredients: MutableList<RecipeIngredientModel>?) {
        this.recipeIngredients = recipeIngredients
    }
}