package io.immortal.spicemustflow.infrastructure.common.persistence

interface BaseNotUpdatableJpaEntity {
    var createInfo: JpaCreateInfo?
}