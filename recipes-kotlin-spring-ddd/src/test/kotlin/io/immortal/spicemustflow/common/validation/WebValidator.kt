package io.immortal.spicemustflow.common.validation

import io.immortal.spicemustflow.common.TestResponse
import io.immortal.spicemustflow.common.constants.DEFAULT_STRING_SIZE
import io.immortal.spicemustflow.web.configuration.ERROR_DETAIL
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import kotlin.test.assertTrue

const val MAX_SIZE_ERROR = "size must be between 0 and "

class WebValidator {

    fun validateDefaultMaxSizeError(response: TestResponse, path: String, invalidValue: String) {
        validateMaxSizeError(response, path, DEFAULT_STRING_SIZE, invalidValue)
    }

    fun validateMaxSizeError(response: TestResponse, path: String, maxSize: Int, invalidValue: String) {
        validateBadRequest(response, path, MAX_SIZE_ERROR + maxSize, invalidValue)
    }

    fun validateBadRequest(response: TestResponse, path: String, errorMessage: String, invalidValueSubstring: String) {
        val errorDto = response.extract(WebErrorDto::class.java)
        assertThat(errorDto.title).isEqualTo(HttpStatus.BAD_REQUEST.reasonPhrase)
        assertThat(errorDto.detail).isEqualTo(ERROR_DETAIL)
        assertThat(errorDto.instance.toString()).isEqualTo(path)

        println(errorDto.errors)
        assertThat(errorDto.errors?.size).isEqualTo(1)
        val errorDetail = errorDto.errors?.get(0)

        assertThat(errorDetail?.message).isEqualTo(errorMessage)
        assertThat(errorDetail?.path).isNotEmpty()
        assertTrue(errorDetail?.invalidValue.toString().contains(invalidValueSubstring))
    }
}