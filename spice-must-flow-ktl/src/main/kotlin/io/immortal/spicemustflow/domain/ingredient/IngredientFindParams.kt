package io.immortal.spicemustflow.domain.ingredient

import com.fasterxml.jackson.annotation.JsonProperty
import org.springdoc.core.annotations.ParameterObject
import java.util.*

@ParameterObject
class IngredientFindParams (
    // TODO rename param to "id"
    @JsonProperty("id")
    val ids: List<UUID>? = null,
    @JsonProperty("name")
    val names: List<String>? = null
)