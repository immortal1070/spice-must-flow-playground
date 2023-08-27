package io.immortal.spicemustflow.infrastructure.common.persistence

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.OffsetDateTime

const val UPDATED_AT = "updated_at"
const val UPDATER = "updater"

@Embeddable
class JpaUpdateInfo(
    @Column(name = UPDATER)
    var updater: String? = null,

    @Column(name = UPDATED_AT)
    var updatedAt: OffsetDateTime? = null
)