package io.immortal.spicemustflow.web.resources.recipe.dto

import org.springdoc.core.annotations.ParameterObject

@ParameterObject
class RecipeRestQuery(
    val ids: List<RecipeRestId>? = null,
    val names: List<String>? = null,
    val content: List<String>? = null,
    val cookingMinutes: List<Int>? = null,
    val ingredientNames: List<String>? = null
)