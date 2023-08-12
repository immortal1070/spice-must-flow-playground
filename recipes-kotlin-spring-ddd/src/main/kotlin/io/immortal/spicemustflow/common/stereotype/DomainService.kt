package io.immortal.spicemustflow.common.stereotype

import io.immortal.spicemustflow.common.logging.MethodLogging
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
@MethodLogging
annotation class DomainService
