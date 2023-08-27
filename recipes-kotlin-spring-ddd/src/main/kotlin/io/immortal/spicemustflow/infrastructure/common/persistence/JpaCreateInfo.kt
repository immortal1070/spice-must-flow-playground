package io.immortal.spicemustflow.infrastructure.common.persistence

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.OffsetDateTime

const val CREATED_AT = "created_at"
const val CREATOR = "creator"

@Embeddable
class JpaCreateInfo(
    @Column(name = CREATOR, updatable = false)
    var creator: String? = null,

    @Column(name = CREATED_AT, updatable = false, nullable = false)
    var createdAt: OffsetDateTime? = null
)