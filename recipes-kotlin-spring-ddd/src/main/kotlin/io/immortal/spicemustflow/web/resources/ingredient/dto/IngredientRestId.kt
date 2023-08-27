package io.immortal.spicemustflow.web.resources.ingredient.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import org.springframework.core.convert.converter.Converter
import java.util.*

data class IngredientRestId(
    @JsonValue
    val uuid: UUID
) {
    @JsonCreator
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    constructor(ingredientId: IngredientId) : this(ingredientId.uuid)

    override fun toString(): String {
        return uuid.toString()
    }

    fun toIngredientId(): IngredientId {
        return IngredientId(uuid)
    }
}

@ApplicationScoped
class IngredientIdConverter : Converter<String?, IngredientId?> {
    override fun convert(source: String): IngredientId? {
        return IngredientId(source)
    }
}