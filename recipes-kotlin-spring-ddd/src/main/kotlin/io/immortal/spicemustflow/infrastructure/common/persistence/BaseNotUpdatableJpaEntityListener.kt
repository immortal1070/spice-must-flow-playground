package io.immortal.spicemustflow.infrastructure.common.persistence

import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.common.utils.nowOffsetUtc
import jakarta.persistence.PrePersist

@ApplicationScoped
class BaseNotUpdatableJpaEntityListener {
    @PrePersist
    fun onPrePersist(model: BaseNotUpdatableJpaEntity) {
        model.createInfo = JpaCreateInfo(createdAt = nowOffsetUtc())
    }
}