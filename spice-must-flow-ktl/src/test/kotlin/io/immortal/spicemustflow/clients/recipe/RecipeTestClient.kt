package io.immortal.spicemustflow.clients.recipe

import io.immortal.spicemustflow.common.TestResponse
import io.immortal.spicemustflow.common.TestResponseWithBody
import io.immortal.spicemustflow.common.restassured.As
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeDto
import io.immortal.spicemustflow.web.resources.recipe.dto.RecipeRestSaveCommand
import io.restassured.module.kotlin.extensions.*
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.springframework.http.HttpStatus
import java.util.*

const val RECIPE_PARAM_ID = "ids"
const val RECIPE_PARAM_NAME = "names"
private const val RESOURCE_ROOT = "/v1/recipes"
private const val CONTENT_TYPE = "application/json; charset=UTF-16"
private val REQUEST_MAX_TIME = lessThan(5000L)

class RecipeTestClient {
    
    fun findById(id: RecipeId): TestResponseWithBody<RecipeDto> { // use get by id here
        val foundResult = find(RECIPE_PARAM_ID to id.uuid)
        assertThat(foundResult.body.size).isEqualTo(1)
        return TestResponseWithBody(foundResult.response, foundResult.body[0])
    }

    fun find(queryParams: Pair<String, Any?>): TestResponseWithBody<List<RecipeDto>> {
        return find(mapOf(queryParams))
    }

    fun find(queryParams: Map<String, Any?>): TestResponseWithBody<List<RecipeDto>> {
        val response: ValidatableResponse =
            Given {
                queryParams(queryParams)
            } When {
                get(RESOURCE_ROOT)
            } Then {
                time(REQUEST_MAX_TIME)
                statusCode(HttpStatus.OK.value())
            }

        val dto: List<RecipeDto> = response Extract {
            As(Array<RecipeDto>::class.java).asList()
        }
        return TestResponseWithBody(TestResponse(response), dto)
    }

    fun findNone(id: RecipeId) {
        findNone(mapOf(RECIPE_PARAM_ID to id.uuid))
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

    fun createRequest(saveDto: RecipeRestSaveCommand): TestResponse {
        return TestResponse(Given {
            contentType(CONTENT_TYPE)
            body(saveDto)
        } When {
            post(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun validCreate(saveDto: RecipeRestSaveCommand): TestResponseWithBody<RecipeId> {
        val response: TestResponse = createRequest(saveDto)
            .statusCode(HttpStatus.CREATED.value())

        val dto = response.extract(RecipeId::class.java)

        return TestResponseWithBody(response, dto)
    }

    fun updateRequest(id: RecipeId, saveDto: RecipeRestSaveCommand): TestResponse {
        return TestResponse(Given {
            contentType(CONTENT_TYPE)
            body(saveDto)
        } When {
                 put("$RESOURCE_ROOT/$id")
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun validUpdate(id: RecipeId, saveDto: RecipeRestSaveCommand): TestResponse {
        return updateRequest(id, saveDto).statusCode(HttpStatus.OK.value())
    }

    fun deleteRequest(id: RecipeId): TestResponse {
        return TestResponse(When {
                 delete("$RESOURCE_ROOT/$id")
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun deleteRequest(ids: List<RecipeId>): TestResponse {
        return TestResponse(Given {
            queryParams(mapOf(RECIPE_PARAM_ID to ids))
        } When {
            delete(RESOURCE_ROOT)
        } Then {
            time(REQUEST_MAX_TIME)
        })
    }

    fun generateId(): RecipeId = RecipeId(UUID.randomUUID())
}