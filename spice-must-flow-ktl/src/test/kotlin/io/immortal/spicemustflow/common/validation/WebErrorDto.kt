package io.immortal.spicemustflow.common.validation

import io.immortal.spicemustflow.web.configuration.ViolationError
import org.springframework.http.ProblemDetail

// web error which is by REST
class WebErrorDto : ProblemDetail() {
    val errors: List<ViolationError>? = null
}