package io.immortal.spicemustflow.common.stereotype

import io.immortal.spicemustflow.common.logging.MethodLogging
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
@MethodLogging
annotation class WebRestController
