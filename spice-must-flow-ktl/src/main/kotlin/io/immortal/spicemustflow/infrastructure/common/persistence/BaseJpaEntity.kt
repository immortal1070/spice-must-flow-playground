package io.immortal.spicemustflow.infrastructure.common.persistence

interface BaseJpaEntity : BaseNotUpdatableJpaEntity {
    var updateInfo: JpaUpdateInfo?
}