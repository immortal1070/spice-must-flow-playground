package io.immortal.spicemustflow.testcontainers

import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ContextConfiguration(initializers = [PostgreSQLContainerInitializer::class])
annotation class WithDatabase