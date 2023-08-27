package io.immortal.spicemustflow.web.resources.ingredient.dto

import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.RequestParam

@ParameterObject
class IngredientRestQuery(
    @RequestParam
    val ids: List<IngredientRestId>? = null,

    @RequestParam
    val names: List<String>? = null
)