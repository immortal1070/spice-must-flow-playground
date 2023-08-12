package io.immortal.spicemustflow.common.restassured

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.immortal.spicemustflow.web.resources.ingredient.IngredientIdJacksonModule
import io.immortal.spicemustflow.web.resources.recipe.RecipeIdJacksonModule
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig.objectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.parsing.Parser
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.web.server.LocalServerPort
import java.lang.reflect.Type

abstract class RestAssuredTest {
    @LocalServerPort
    var port = 8080

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        RestAssured.port = port

        configureObjectMapperConfig()
    }

    private fun configureObjectMapperConfig() {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            objectMapperConfig().jackson2ObjectMapperFactory(
                object : DefaultJackson2ObjectMapperFactory() {
                    override fun create(cls: Type?, charset: String?): ObjectMapper {
                        val objectMapper = super.create(cls, charset)
                        configureObjectMapper(objectMapper)
                        return objectMapper
                    }
                }
            ))
    }

    private fun configureObjectMapper(objectMapper: ObjectMapper?) {
        objectMapper
            ?.registerModule(IngredientIdJacksonModule())
            ?.registerModule(RecipeIdJacksonModule())
    }

    fun getObjectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        configureObjectMapper(objectMapper)
        return objectMapper
    }
}
