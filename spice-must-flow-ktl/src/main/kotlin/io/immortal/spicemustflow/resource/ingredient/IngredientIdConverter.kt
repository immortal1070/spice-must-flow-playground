package io.immortal.spicemustflow.resource.ingredient

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.*

@Component
class IngredientIdConverter : Converter<String?, IngredientId?> {
    override fun convert(source: String): IngredientId? {
        return IngredientId(UUID.fromString(source))
    }
}

@Component
class IngredientIdJacksonModuleProvider {
    @Bean
    fun provideIngredientIdModule(): SimpleModule {
        return IngredientIdJacksonModule()
    }
}

class IngredientIdJacksonModule : SimpleModule() {
    init {
        addDeserializer(IngredientId::class.java, IngredientIdJsonDeserializer())
        addSerializer(IngredientId::class.java, IngredientIdJsonSerializer())
    }
}


internal class IngredientIdJsonSerializer : JsonSerializer<IngredientId?>() {
    override fun serialize(value: IngredientId?, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeString(value?.uuid.toString())
    }
}

internal class IngredientIdJsonDeserializer : JsonDeserializer<IngredientId?>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): IngredientId {
        return p.getValueAsString().let {
            IngredientId(UUID.fromString(it))
        }
    }
}