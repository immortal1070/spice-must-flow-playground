package io.immortal.spicemustflow.common.stereotype

import io.immortal.spicemustflow.common.logging.MethodLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Service
@MethodLogging
@Validated
@Transactional
annotation class ApplicationService
