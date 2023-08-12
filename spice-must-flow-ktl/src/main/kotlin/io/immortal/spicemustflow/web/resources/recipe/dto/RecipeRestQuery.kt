package io.immortal.spicemustflow.web.resources.recipe.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.immortal.spicemustflow.domain.recipe.RecipeId
import org.springdoc.core.annotations.ParameterObject

@ParameterObject
class RecipeRestQuery(
    @JsonProperty("id")
    val ids: List<RecipeId>? = null,

    @JsonProperty("name")
    val names: List<String>? = null,

    @JsonProperty("content")
    val contents: List<String>? = null,

    @JsonProperty("cookingMinutes")
    val cookingMinutes: List<Int>? = null,

    @JsonProperty("ingredientName")
    val ingredientNames: List<String>? = null
)