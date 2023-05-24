package io.immortal.spicemustflow.infrastructure.ingredient

import io.immortal.spicemustflow.util.persistence.BaseJpaEntity
import io.immortal.spicemustflow.util.persistence.BaseJpaEntityListener
import io.immortal.spicemustflow.util.persistence.ColumnConstants.Companion.CREATED_AT
import io.immortal.spicemustflow.util.persistence.ColumnConstants.Companion.CREATOR
import io.immortal.spicemustflow.util.persistence.ColumnConstants.Companion.UPDATED_AT
import io.immortal.spicemustflow.util.persistence.ColumnConstants.Companion.UPDATER
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "ingredient")
@EntityListeners(BaseJpaEntityListener::class)
class IngredientJpaEntity(var name: String) : BaseJpaEntity {

    // TODO check hibernate version + postgres UUID generation
    @Id
    @GeneratedValue
    var id: UUID? = null

    @Column(name = CREATOR, updatable = false)
    override var creator: String? = null

    @Column(name = CREATED_AT, updatable = false, nullable = false)
    override var createdAt: OffsetDateTime? = null

    @Column(name = UPDATER)
    override var updater: String? = null

    @Column(name = UPDATED_AT)
    override var updatedAt: OffsetDateTime? = null
}

