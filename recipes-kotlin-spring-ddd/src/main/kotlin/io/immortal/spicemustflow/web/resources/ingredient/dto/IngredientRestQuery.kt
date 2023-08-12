package io.immortal.spicemustflow.web.resources.ingredient.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import org.springdoc.core.annotations.ParameterObject

@ParameterObject
class IngredientRestQuery(
    @JsonProperty("id")
    val ids: List<IngredientId>? = null,
    @JsonProperty("name")
    val names: List<String>? = null
)