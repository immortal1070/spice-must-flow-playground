package io.immortal.spicemustflow.infrastructure.repository.recipe

import io.immortal.spicemustflow.infrastructure.common.persistence.BaseJpaEntity
import io.immortal.spicemustflow.infrastructure.common.persistence.BaseJpaEntityListener
import io.immortal.spicemustflow.infrastructure.common.persistence.JpaCreateInfo
import io.immortal.spicemustflow.infrastructure.common.persistence.JpaUpdateInfo
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "recipe")
@EntityListeners(BaseJpaEntityListener::class)
class RecipeJpaEntity(
    var name: String,

    var content: String,

    var cookingMinutes: Int
) : BaseJpaEntity {

    @OneToMany(
        cascade = [CascadeType.REMOVE],
        mappedBy = "recipe",
        targetEntity = RecipeIngredientJpaEntity::class
    )
    var ingredients: List<RecipeIngredientJpaEntity>? = null

    @Id
    var id: UUID? = null

    @Embedded
    override var createInfo: JpaCreateInfo? = null

    @Embedded
    override var updateInfo: JpaUpdateInfo? = null
}

