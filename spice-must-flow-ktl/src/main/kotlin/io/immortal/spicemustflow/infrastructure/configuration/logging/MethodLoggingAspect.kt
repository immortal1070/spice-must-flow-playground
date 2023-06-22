package io.immortal.spicemustflow.infrastructure.configuration.logging

import io.immortal.spicemustflow.common.logging.MethodLogging
import io.immortal.spicemustflow.common.logging.MethodLoggingLevel
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.*
private val logger = KotlinLogging.logger ("MethodLoggingAspect")

@Aspect
@ApplicationScoped
class MethodLoggingAspect {

    @Pointcut("@within(methodLogging) || @annotation(methodLogging)")
    fun myHandlerFunc(methodLogging: MethodLogging?) {}

    @Around("myHandlerFunc(methodLogging)")
    fun loggingAdvice(joinPoint: ProceedingJoinPoint, methodLogging: MethodLogging?): Any? {
        val annotation = getLoggerAnnotation(joinPoint, methodLogging)
        val methodLogger = MethodLogger(
            logger = logger,
            level = annotation?.value ?: MethodLoggingLevel.DEBUG,
            joinPoint = joinPoint
        )

        try {
            methodLogger.logMethod()
            val obj = joinPoint.proceed()
            methodLogger.logResult(obj)
            return obj
        } catch (e: Throwable) {
            methodLogger.logError(e)
            throw e
        }
    }

    private fun getLoggerAnnotation(joinPoint: ProceedingJoinPoint,
                                    methodLogging: MethodLogging?): MethodLogging? {
        var loggedAnnotation: MethodLogging? = methodLogging
        if (loggedAnnotation == null) {
            loggedAnnotation = joinPoint.target.javaClass.getAnnotation(MethodLogging::class.java)
        }
        if (loggedAnnotation == null) {
            loggedAnnotation = joinPoint.target.javaClass.superclass.getAnnotation(MethodLogging::class.java)
        }
        return loggedAnnotation
    }
}