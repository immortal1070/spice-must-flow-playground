package io.immortal.spicemustflow.web.configuration

import io.immortal.spicemustflow.common.exception.ObjectNotFoundException
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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

const val ERROR_DETAIL = "Validation errors. Please check your request and correct it"

class ViolationError(
    val message: String? = null,
    val path: String? = null,
    val invalidValue: Any? = null
)

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
    @ApiResponse(
        responseCode = "400",
        description = "Bad Request",
        content = [Content(
                mediaType = "application/problem+json",
                schema = Schema(implementation = ProblemDetail::class),
                examples = [ExampleObject(
                        value = """{
                      "type": "errortype",
                      "title": "Validation exception",
                      "status": 400,
                      "detail": "Validation exception detail",
                      "instance": "/ingredients/",
                      "errors": {
                        "message": "failed",
                        "path": "...",
                        "invalidValue": "123"
                      }
                    }"""
                )]
        )]
    )

    fun onConstraintValidationException(e: ConstraintViolationException): ResponseEntity<ProblemDetail> {
        return buildErrorResponseEntity(e, buildValidationErrors(e)) { body -> body }
    }

    override fun handleMethodArgumentNotValid(
            e: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatusCode,
            request: WebRequest
    ): ResponseEntity<Any>? {
        return buildErrorResponseEntity(e, buildValidationErrors(e)) { body -> body }
    }

    private fun <T> buildErrorResponseEntity(
            e: Throwable,
            validationErrors: List<ViolationError>,
        //problemTypeConverter is used to convert a type of body to the needed subtype
            problemTypeConverter: (ProblemDetail) -> T
    ): ResponseEntity<T> {
        val errorResponse: ErrorResponse = buildErrorResponse(e, validationErrors)
        return ResponseEntity<T>(
                problemTypeConverter(errorResponse.body),
                errorResponse.headers,
                errorResponse.statusCode
        )
    }

    private fun buildErrorResponse(e: Throwable, errors: List<ViolationError>): ErrorResponse = ErrorResponse.builder(
            e,
            HttpStatus.BAD_REQUEST,
            ERROR_DETAIL
    ).property("errors", errors)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

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