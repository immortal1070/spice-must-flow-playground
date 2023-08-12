package io.immortal.spicemustflow.common.restassured

import io.restassured.response.ResponseBodyExtractionOptions

// naming the method As with capital letter to fit a style of RestAssured official extension
fun <T> ResponseBodyExtractionOptions.As(asClass: Class<T>): T = this.`as`(asClass)
