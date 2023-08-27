package io.immortal.spicemustflow.clients.ingredient

import io.immortal.spicemustflow.common.TestResponse
import io.immortal.spicemustflow.common.TestResponseWithBody
import io.immortal.spicemustflow.common.restassured.As
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestId
import io.immortal.spicemustflow.web.resources.ingredient.dto.IngredientRestSaveCommand
import io.restassured.module.kotlin.extensions.*
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.springframework.http.HttpStatus
import java.util.*

const val INGREDIENT_PARAM_ID = "ids"
const val INGREDIENT_PARAM_NAME = "names"

private const val RESOURCE_ROOT = "/v1/ingredients"
private const val CONTENT_TYPE = "application/json; charset=UTF-16"
private val REQUEST_MAX_TIME = lessThan(5000L)

class IngredientTestClient {

    fun findById(id: IngredientRestId): TestResponseWithBody<IngredientDto> {
        val foundResult = find(INGREDIENT_PARAM_ID to id.uuid)
        assertThat(foundResult.body.size).isEqualTo(1)
        return TestResponseWithBody(foundResult.response, foundResult.body[0])
    }

    fun find(queryParams: Pair<String, Any?>): TestResponseWithBody<List<IngredientDto>> {
        return find(mapOf(queryParams))
    }

    fun find(queryParams: Map<String, Any?>): TestResponseWithBody<List<IngredientDto>> {
        val response: ValidatableResponse =
            Given {
                queryParams(queryParams)
            } When {
                get(RESOURCE_ROOT)
            } Then {
                time(REQUEST_MAX_TIME)
                statusCode(HttpStatus.OK.value())
            }

        val dto: List<IngredientDto> = response Extract {
            As(Array<IngredientDto>::class.java).asList()
        }
        return TestResponseWithBody(TestResponse(response), dto)
    }

    fun findNone(id: IngredientRestId) {
        findNone(mapOf(INGREDIENT_PARAM_ID to id.uuid))
    }

    fun findNone(queryParams: Map<String, Any?>) {
        Given {
            queryParams(queryParams)
        } When {
            get(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
            statusCode(HttpStatus.OK.value())
            body("", hasSize<String>(0))
        }
    }

    fun createRequest(
        saveDto: IngredientRestSaveCommand,
        expectedStatusCode: HttpStatus = HttpStatus.CREATED
    ): TestResponse {
        return TestResponse(Given {
            contentType(CONTENT_TYPE)
            body(saveDto)
        } When {
            post(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
            statusCode(expectedStatusCode.value())
        })
    }

    fun validCreate(saveDto: IngredientRestSaveCommand): TestResponseWithBody<IngredientRestId> {
        val response: TestResponse = createRequest(saveDto)

        val dto = response.extract(IngredientRestId::class.java)

        return TestResponseWithBody(response, dto)
    }

    fun updateRequest(
        id: IngredientRestId, saveDto: IngredientRestSaveCommand,
        expectedStatusCode: HttpStatus = HttpStatus.OK
    ): TestResponse {
        return TestResponse(Given {
            contentType(CONTENT_TYPE)
            body(saveDto)
        } When {
            put("$RESOURCE_ROOT/$id")
        } Then {
            time(REQUEST_MAX_TIME)
            statusCode(expectedStatusCode.value())
        })
    }

    fun validUpdate(id: IngredientRestId, saveDto: IngredientRestSaveCommand): TestResponse {
        return updateRequest(id, saveDto)
    }

    fun deleteRequest(id: IngredientRestId, expectedStatusCode: HttpStatus = HttpStatus.NO_CONTENT): TestResponse {
        return TestResponse(When {
            delete("$RESOURCE_ROOT/$id")
        } Then {
            time(REQUEST_MAX_TIME)
            statusCode(expectedStatusCode.value())
        })
    }

    fun deleteRequest(
        ids: List<IngredientRestId>,
        expectedStatusCode: HttpStatus = HttpStatus.NO_CONTENT
    ): TestResponse {
        return TestResponse(Given {
            queryParams(mapOf(INGREDIENT_PARAM_ID to ids.joinToString(",")))
        } When {
            delete(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
            statusCode(expectedStatusCode.value())
        })
    }

    fun generateId(): IngredientRestId = IngredientRestId(UUID.randomUUID())
}