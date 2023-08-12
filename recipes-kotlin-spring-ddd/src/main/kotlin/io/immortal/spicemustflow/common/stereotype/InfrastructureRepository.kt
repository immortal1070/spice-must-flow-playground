package io.immortal.spicemustflow.common.stereotype

import io.immortal.spicemustflow.common.logging.MethodLogging
import org.springframework.stereotype.Repository

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Repository
@MethodLogging
annotation class InfrastructureRepository
