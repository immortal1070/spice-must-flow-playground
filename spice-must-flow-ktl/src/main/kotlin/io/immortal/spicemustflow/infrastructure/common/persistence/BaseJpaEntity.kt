package io.immortal.spicemustflow.infrastructure.common.persistence

import java.time.OffsetDateTime

interface BaseJpaEntity {
    var creator: String?
    var createdAt: OffsetDateTime?
    var updater: String?
    var updatedAt: OffsetDateTime?
}