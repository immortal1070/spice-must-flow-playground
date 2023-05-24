package io.immortal.spicemustflow.domain.ingredient

data class IngredientQuery (
    val ids: List<IngredientId>? = null,
    val names: List<String>? = null
)