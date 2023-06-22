package io.immortal.spicemustflow.infrastructure.configuration.logging

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.immortal.spicemustflow.common.logging.MethodLoggingLevel
import io.swagger.v3.core.util.ObjectMapperFactory
import mu.KLogger
import org.aspectj.lang.ProceedingJoinPoint

class MethodLogger(val logger: KLogger, val level: MethodLoggingLevel, private val joinPoint: ProceedingJoinPoint) {
    companion object {
        internal val mapper: ObjectMapper = ObjectMapperFactory.buildStrictGenericObjectMapper()
    }

    fun log(message: String) {
        when (level) {
            MethodLoggingLevel.INFO -> {
                logger.info {
                    message
                }
            }

            MethodLoggingLevel.DEBUG -> {
                logger.debug {
                    message
                }
            }
        }
    }

    fun logMethod() {
        log("[Method call]: ${joinPoint.signature} \n[Parameters]: ${objectToString(joinPoint.args)}")
    }

    private fun objectToString(parameter: Any?): String? {
        return try {
            mapper.writeValueAsString(parameter)
        } catch (e: JsonProcessingException) {
            logger.warn("can't convert parameters with object mapper", e)
            parameter.toString()
        }
    }

    fun logResult(result: Any?) {
//        log("${joinPoint.signature.name} result: $result")
        log("[Method returns]: ${joinPoint.signature} \n[Result]: $result")
    }

    fun logError(e: Throwable) {
        log("${joinPoint.signature.name} thrown exception with message: ${e.message}")
    }
}