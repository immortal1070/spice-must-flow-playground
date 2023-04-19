//package io.immortal.spicemustflow.ingredient
//
//import io.immortal.spicemustflow.application.ingredient.dto.IngredientDto
//import io.immortal.spicemustflow.application.ingredient.dto.IngredientSaveDto
//import io.immortal.spicemustflow.clients.IngredientTestClient
//import io.restassured.RestAssured.given
//import org.assertj.core.api.Assertions.assertThat
//import org.hamcrest.Matchers.*
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.Test
//
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class TestRestTests {
//    private val ingredientTestClient: IngredientTestClient = IngredientTestClient()
//
//    @BeforeAll
//    fun setup() {
//        println(">> Setup IngredientRestTests")
////        JsonSchemaValidator.settings = settings().with().jsonSchemaFactory(
////            JsonSchemaFactory.newBuilder().setValidationConfiguration(
////                ValidationConfiguration.newBuilder().setDefaultVersion(
////                    SchemaVersion.DRAFTV3).freeze()).freeze()).and().with()
////            .checkedValidation(false);
//    }
//
//    @Test
//    fun `check find by name for existing ingredient`() {
//        val name = "test3"
//        val ingredientSaveDto = IngredientSaveDto(name)
//        val createResult = ingredientTestClient.create(ingredientSaveDto)
//
//        val getResult = ingredientTestClient.get(mapOf(PARAM_NAMES to name))
//        getResult.response.statusCode(200)
//        assertThat(getResult.dto.name).isEqualTo(name)
//        assertThat(getResult.dto.id).isNotNull
//
//        assertThat(getResult.dto.name).isEqualTo(createResult.dto.name)
//        assertThat(getResult.dto.id).isEqualTo(createResult.dto.id)
//    }
//
//    @Test
//    fun `test create`() {
//        val name = "test2"
//        val ingredientSaveDto = IngredientSaveDto(name)
//        val createResult = ingredientTestClient.create(ingredientSaveDto)
//        assertThat(createResult.dto.name).isEqualTo(name)
//        assertThat(createResult.dto.id).isNotNull
//    }
//
//    @Test
//    fun `check find by name with given`() {
//        val ingredientDto =
//        given().
//            queryParam(PARAM_NAMES, "123").
//        `when`().
//                get("/v1/ingredients")
//         .then()
//            .time(lessThan(2000L))
//            .statusCode(200)
//            .body(PARAM_NAMES, equalTo("123")).extract()
//            .`as`(IngredientDto::class.java) //TODO check if it's correct
//        assertThat(ingredientDto.name).isEqualTo("123")
//        assertThat(ingredientDto.id).isNotNull
//    }
//}
