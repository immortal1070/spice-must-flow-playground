package io.immortal.spicemustflow.domain.ingredient

import org.springdoc.core.annotations.ParameterObject
import java.util.*

//class IngredientFindParams(
//    ids: List<UUID>? = null,
//    names: List<String>? = null
//) {
//    // TODO rename param to "id"
//    var ids: List<UUID>? = ids
//    var names: List<String>? = names
//}
@ParameterObject
class IngredientFindParams (
    // TODO rename param to "id"
    val ids: List<UUID>? = null,
    val names: List<String>? = null
)