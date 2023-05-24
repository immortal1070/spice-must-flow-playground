package io.immortal.spicemustflow.util

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.response.ValidatableResponse

data class CreatedData<I, T>(val inputDto: I, val createdResponse: TestResponseWithBody<T>)

data class TestResponseWithBody<T>(val response: TestResponse, val body: T)

class TestResponse(private val response: ValidatableResponse) {
    fun statusCode(expectedStatusCode: Int): TestResponse {
        response.statusCode(expectedStatusCode)
        return this
    }

    fun <T> extract(asClass: Class<T>): T = response Extract {
        As(asClass)
    }

    fun printAsPrettyString() = println(response.extract().asPrettyString())
}

