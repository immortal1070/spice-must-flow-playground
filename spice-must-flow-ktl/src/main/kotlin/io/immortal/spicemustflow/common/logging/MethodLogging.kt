package io.immortal.spicemustflow.common.logging

/**
 * Marks that class or method should be logged.
 */
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class MethodLogging(val value: MethodLoggingLevel = MethodLoggingLevel.DEBUG)