package io.immortal.spicemustflow.common.stereotype

import io.immortal.spicemustflow.common.logging.MethodLogging
import org.springframework.stereotype.Component

/**
 * Annotation for Singleton (application scoped) beans
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
@MethodLogging
annotation class ApplicationScoped
