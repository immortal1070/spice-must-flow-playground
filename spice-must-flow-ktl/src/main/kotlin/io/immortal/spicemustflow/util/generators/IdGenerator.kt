package io.immortal.spicemustflow.util.generators

import org.springframework.stereotype.Component
import java.util.*

@Component
class IdGenerator {
    fun generateUuid(): UUID = UUID.randomUUID()
}