package io.immortal.spicemustflow.util

import io.restassured.response.ValidatableResponse

data class TestResponse<T>(val response: ValidatableResponse, val body: T)

data class CreatedData<I, T>(val inputDto: I, val createdResponse: TestResponse<T>)