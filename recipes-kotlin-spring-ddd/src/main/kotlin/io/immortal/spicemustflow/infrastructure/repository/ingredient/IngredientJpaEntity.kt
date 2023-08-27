package io.immortal.spicemustflow.infrastructure.repository.ingredient

import io.immortal.spicemustflow.infrastructure.common.persistence.BaseJpaEntity
import io.immortal.spicemustflow.infrastructure.common.persistence.BaseJpaEntityListener
import io.immortal.spicemustflow.infrastructure.common.persistence.JpaCreateInfo
import io.immortal.spicemustflow.infrastructure.common.persistence.JpaUpdateInfo
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "ingredient")
@EntityListeners(BaseJpaEntityListener::class)
class IngredientJpaEntity(var name: String) : BaseJpaEntity {
    @Id
    var id: UUID? = null

    @Embedded
    override var createInfo: JpaCreateInfo? = null

    @Embedded
    override var updateInfo: JpaUpdateInfo? = null
}

