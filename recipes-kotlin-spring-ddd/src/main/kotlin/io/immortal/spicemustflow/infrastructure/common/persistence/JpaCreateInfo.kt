package io.immortal.spicemustflow.infrastructure.common.persistence

import io.immortal.spicemustflow.infrastructure.common.persistence.ColumnConstants.Companion.CREATED_AT
import io.immortal.spicemustflow.infrastructure.common.persistence.ColumnConstants.Companion.CREATOR
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.OffsetDateTime

@Embeddable
class JpaCreateInfo(
    @Column(name = CREATOR, updatable = false)
    var creator: String? = null,

    @Column(name = CREATED_AT, updatable = false, nullable = false)
    var createdAt: OffsetDateTime? = null
)