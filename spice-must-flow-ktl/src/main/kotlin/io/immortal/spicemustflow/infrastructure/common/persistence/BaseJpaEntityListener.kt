package io.immortal.spicemustflow.infrastructure.common.persistence

import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.common.utils.nowOffsetUtc
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate

@ApplicationScoped
class BaseJpaEntityListener {
    @PrePersist
    fun onPrePersist(model: BaseJpaEntity) {
        model.createInfo = JpaCreateInfo(createdAt = nowOffsetUtc())
    }

    @PreUpdate
    fun onPreUpdate(model: BaseJpaEntity) {
        model.updateInfo = JpaUpdateInfo(updatedAt = nowOffsetUtc())
    }
}