package io.immortal.spicemustflow.clients

import io.immortal.spicemustflow.application.ingredient.dto.IngredientDto
import io.immortal.spicemustflow.application.ingredient.dto.IngredientSaveDto
import io.immortal.spicemustflow.util.As
import io.immortal.spicemustflow.util.TestResponse
import io.restassured.module.kotlin.extensions.*
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.springframework.http.HttpStatus
import java.util.*

const val INGREDIENT_PARAM_ID = "ids"
const val INGREDIENT_PARAM_NAME = "names"

class IngredientTestClient {
    private val resourceRoot = "/v1/ingredients"
    private val requestMaxTime = lessThan(5000L)

    fun findById(id: UUID): TestResponse<IngredientDto> {
        val foundResult = find(INGREDIENT_PARAM_ID to id)
        assertThat(foundResult.body.size).isEqualTo(1)
        return TestResponse(foundResult.response, foundResult.body[0])
    }

    fun find(queryParams: Pair<String, Any?>): TestResponse<List<IngredientDto>> {
        return find(mapOf(queryParams))
    }

    fun find(queryParams: Map<String, Any?>): TestResponse<List<IngredientDto>> {
        val response: ValidatableResponse =
            Given {
                queryParams(queryParams)
            } When {
                get(resourceRoot)
            } Then {
                time(requestMaxTime)
                statusCode(HttpStatus.OK.value())
            }

        val dto: List<IngredientDto> = response Extract {
            As(Array<IngredientDto>::class.java).asList()
        }
        return TestResponse(response, dto)
    }

    fun findNone(queryParams: Map<String, Any?>) {
        Given {
            queryParams(queryParams)
        } When {
            get(resourceRoot)
        } Then {
            time(requestMaxTime)
            statusCode(HttpStatus.OK.value())
            body("", hasSize<String>(0))
        }
    }

    fun createRequest(saveDto: IngredientSaveDto): ValidatableResponse {
        return Given {
            contentType("application/json; charset=UTF-16")
            body(saveDto)
        } When {
            post(resourceRoot)
        } Then {
            time(requestMaxTime)
        }
    }

    fun validCreate(saveDto: IngredientSaveDto): TestResponse<IngredientDto> {
        val response: ValidatableResponse = createRequest(saveDto)
            .statusCode(HttpStatus.CREATED.value())

        val dto = response Extract {
            As(IngredientDto::class.java)
        }

        return TestResponse(response, dto)
    }

    fun updateRequest(id: UUID, saveDto: IngredientSaveDto): ValidatableResponse {
        return Given {
            contentType("application/json; charset=UTF-16")
            body(saveDto)
        } When {
            put("$resourceRoot/$id")
        } Then {
            time(requestMaxTime)
        }
    }

    fun validUpdate(id: UUID, saveDto: IngredientSaveDto): TestResponse<IngredientDto> {
        val response: ValidatableResponse = updateRequest(id, saveDto)
            .statusCode(HttpStatus.OK.value())

        val dto = response Extract {
            As(IngredientDto::class.java)
        }
        return TestResponse(response, dto)
    }

    fun deleteRequest(id: UUID): ValidatableResponse {
        return When {
            delete("$resourceRoot/$id")
        } Then {
            time(requestMaxTime)
        }
    }

    fun deleteRequest(ids: List<UUID>): ValidatableResponse {
        return Given {
            queryParams(mapOf(INGREDIENT_PARAM_ID to ids))
        } When {
            delete(resourceRoot)
        } Then {
            time(requestMaxTime)
        }
    }
}