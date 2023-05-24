package io.immortal.spicemustflow.clients

import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.resource.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.resource.ingredient.dto.IngredientRestSaveCommand
import io.immortal.spicemustflow.util.As
import io.immortal.spicemustflow.util.TestResponse
import io.immortal.spicemustflow.util.TestResponseWithBody
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
    
    fun findById(id: IngredientId): TestResponseWithBody<IngredientDto> {
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

    fun findNone(id: IngredientId) {
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

    fun createRequest(saveDto: IngredientRestSaveCommand): TestResponse {
        return TestResponse(Given {
            contentType(CONTENT_TYPE)
            body(saveDto)
        } When {
            post(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun validCreate(saveDto: IngredientRestSaveCommand): TestResponseWithBody<IngredientId> {
        val response: TestResponse = createRequest(saveDto)
            .statusCode(HttpStatus.CREATED.value())

        val dto = response.extract(IngredientId::class.java)

        return TestResponseWithBody(response, dto)
    }

    fun updateRequest(id: IngredientId, saveDto: IngredientRestSaveCommand): TestResponse {
        return TestResponse(Given {
            contentType(CONTENT_TYPE)
            body(saveDto)
        } When {
            put("${RESOURCE_ROOT}/$id")
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun validUpdate(id: IngredientId, saveDto: IngredientRestSaveCommand): TestResponse {
        return updateRequest(id, saveDto).statusCode(HttpStatus.OK.value())
    }

    fun deleteRequest(id: IngredientId): TestResponse {
        return TestResponse(When {
            delete("${RESOURCE_ROOT}/$id")
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun deleteRequest(ids: List<IngredientId>): TestResponse {
        return TestResponse(Given {
            queryParams(mapOf(INGREDIENT_PARAM_ID to ids))
        } When {
            delete(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun generateId(): IngredientId = IngredientId(UUID.randomUUID())
}