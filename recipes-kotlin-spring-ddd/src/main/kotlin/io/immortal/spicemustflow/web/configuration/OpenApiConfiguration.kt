package io.immortal.spicemustflow.web.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Configuration

const val INGREDIENTS_TAG = "Ingredients"
const val RECIPES_TAG = "Recipes"

@OpenAPIDefinition(
    info = Info(
        title = "Spice Must Flow API",
        version = "0.1.0",
        description = "Spice Must Flow is a project for storing and managing recipes from different ingredients"
    ),
    tags = [
        Tag(
            name = RECIPES_TAG,
            description = "Recipes which hold a recipe instructions and a list of ingredients and the needed amount of ingredients"
        ),
        Tag(name = INGREDIENTS_TAG, description = "Ingredients are used in recipes"),
    ],
    servers = [Server(url = "http://localhost:8080")]
)
@Configuration
class OpenApiConfiguration