package io.immortal.spicemustflow.infrastructure.common.persistence.generators

import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import java.util.*

@ApplicationScoped
class IdGenerator {
    fun generateUuid(): UUID = UUID.randomUUID()
}