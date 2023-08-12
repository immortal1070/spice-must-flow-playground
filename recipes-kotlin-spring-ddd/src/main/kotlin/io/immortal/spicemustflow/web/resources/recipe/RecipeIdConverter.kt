package io.immortal.spicemustflow.web.resources.recipe

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import io.immortal.spicemustflow.domain.recipe.RecipeId
import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter

@ApplicationScoped
class RecipeIdConverter : Converter<String?, RecipeId?> {
    override fun convert(source: String): RecipeId? {
        return RecipeId(source)
    }
}

@ApplicationScoped
class RecipeIdJacksonModuleProvider {
    @Bean
    fun provideRecipeIdModule(): SimpleModule {
        return RecipeIdJacksonModule()
    }
}

class RecipeIdJacksonModule : SimpleModule() {
    init {
        addDeserializer(RecipeId::class.java, RecipeIdJsonDeserializer())
        addSerializer(RecipeId::class.java, RecipeIdJsonSerializer())
    }
}

internal class RecipeIdJsonSerializer : JsonSerializer<RecipeId?>() {
    override fun serialize(value: RecipeId?, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeString(value?.uuid.toString())
    }
}

internal class RecipeIdJsonDeserializer : JsonDeserializer<RecipeId?>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): RecipeId {
        return RecipeId(p.getValueAsString())
    }
}