package io.immortal.spicemustflow.domain.ingredient

import java.util.*

data class IngredientFindParams(
    // TODO rename param to "id"
    val ids: List<UUID>? = null,
    val names: List<String>? = null
)