package io.immortal.spicemustflow.infrastructure.common.persistence

import io.immortal.spicemustflow.infrastructure.common.persistence.ColumnConstants.Companion.UPDATED_AT
import io.immortal.spicemustflow.infrastructure.common.persistence.ColumnConstants.Companion.UPDATER
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.OffsetDateTime

@Embeddable
class JpaUpdateInfo(
    @Column(name = UPDATER)
    var updater: String? = null,

    @Column(name = UPDATED_AT)
    var updatedAt: OffsetDateTime? = null
)