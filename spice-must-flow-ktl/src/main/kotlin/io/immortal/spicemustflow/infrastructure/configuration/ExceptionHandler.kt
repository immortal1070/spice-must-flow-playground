package io.immortal.spicemustflow.infrastructure.configuration

import io.immortal.spicemustflow.infrastructure.exception.ObjectNotFoundException
import jakarta.validation.ConstraintViolationException
import mu.KotlinLogging
import org.springframework.http.*
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private val log = KotlinLogging.logger {}

private class ViolationError(
    val message: String? = null,
    val path: String? = null,
    val invalidValue: Any? = null
)

private const val ERROR_TITLE = "Validation errors"
//TODO infrastucture is a correct place?
/**
 * Holds exception handlers for different types of exceptions
 */
@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [ObjectNotFoundException::class])
    protected fun handleNotFoundException(e: ObjectNotFoundException?): ResponseEntity<String> {
        log.debug("ObjectNotFoundException!", e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e?.message)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onConstraintValidationException(e: ConstraintViolationException): ResponseEntity<Any> {
        return errorResponse(e, buildValidationErrors(e))
    }

    override fun handleMethodArgumentNotValid(
        e: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return errorResponse(e, buildValidationErrors(e))
    }

    private fun errorResponse(e: Throwable, errors: List<ViolationError>): ResponseEntity<Any> {
        val errorResponse = ErrorResponse.builder(
            e,
            HttpStatus.BAD_REQUEST,
            ERROR_TITLE
        )
            .property("errors", errors).build()
        return ResponseEntity<Any>(
            errorResponse.body,
            errorResponse.headers,
            errorResponse.statusCode
        )
    }

    private fun buildValidationErrors(e: MethodArgumentNotValidException): List<ViolationError> {
        val fieldErrors: List<ViolationError> = e.bindingResult.fieldErrors.map {
            ViolationError(
                message = it.defaultMessage,
                path = it.field,
                invalidValue = it.rejectedValue
            )
        }

        val globalErrors: List<ViolationError> = e.bindingResult.globalErrors.map {
            ViolationError(
                message = it.defaultMessage,
                path = it.objectName
            )
        }
        return fieldErrors + globalErrors
    }

    private fun buildValidationErrors(
        e: ConstraintViolationException
    ): List<ViolationError> {
        return e.constraintViolations.map { violation ->
            ViolationError(
                message = violation.message,
                path = violation.propertyPath.toString(),
                invalidValue = violation.invalidValue
            )
        }.toList()
    }

}