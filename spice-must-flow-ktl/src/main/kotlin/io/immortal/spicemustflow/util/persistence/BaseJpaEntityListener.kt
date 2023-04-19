package io.immortal.spicemustflow.util.persistence

import io.immortal.spicemustflow.util.nowOffsetUtc
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.stereotype.Component

@Component
class BaseJpaEntityListener {
    @PrePersist
    fun onPrePersist(model: BaseJpaEntity) {
        model.createdAt = nowOffsetUtc()
    }

    @PreUpdate
    fun onPreUpdate(model: BaseJpaEntity) {
        model.updatedAt = nowOffsetUtc()
    }
}