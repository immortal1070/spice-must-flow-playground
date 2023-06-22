package io.immortal.spicemustflow.web.configuration

import io.immortal.spicemustflow.web.resources.ingredient.INGREDIENTS_TAG
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Configuration

// TODO finalize
//@SecuritySchemes(
//    [SecurityScheme(
//        securitySchemeName = BEARER_SECURITY_SCHEME,
//        type = SecuritySchemeType.HTTP,
//        `in` = SecuritySchemeIn.HEADER,
//        description = BEARER_DESCRIPTION,
//        bearerFormat = BEARER_FORMAT,
//        scheme = BEARER_SCHEME,
//        apiKeyName = HttpHeaders.AUTHORIZATION
//    ), SecurityScheme(
//        securitySchemeName = TENANT_SECURITY_SCHEME,
//        type = SecuritySchemeType.APIKEY,
//        `in` = SecuritySchemeIn.HEADER,
//        apiKeyName = RestConstants.TENANT_ID_HEADER,
//        description = TENANT_OPENAPI_DESCRIPTION
//    ), SecurityScheme(
//        securitySchemeName = CLIENT_CREDENTIALS_SECURITY_SCHEME,
//        type = SecuritySchemeType.OAUTH2,
//        `in` = SecuritySchemeIn.HEADER,
//        description = CLIENT_CREDENTIALS_DESCRIPTION,
//        flows = OAuthFlows(
//            clientCredentials = OAuthFlow(
//                authorizationUrl = CLIENT_CREDENTIALS_AUTH_URL,
//                tokenUrl = CLIENT_CREDENTIALS_TOKEN_URL,
//                scopes = [OAuthScope(name = CLIENT_CREDENTIALS_TOKEN_SCOPE)]
//            )
//        )
//    )]
//)
@OpenAPIDefinition(
    info = Info(
            title = "Spice Must Flow API",
            version = "0.1.0",
            description = "Spice Must Flow is a project for storing and managing recipes from different ingredients"
    ),
    tags = [
        Tag(name = INGREDIENTS_TAG, description = "Ingredients are used in recipes")
    ],
    servers = [Server(url = "http://localhost:8080")]
//    security = [SecurityRequirement(name = BEARER_SECURITY_SCHEME), SecurityRequirement(name = TENANT_SECURITY_SCHEME), SecurityRequirement(
//        name = CLIENT_CREDENTIALS_SECURITY_SCHEME
//    )]
)
@Configuration
class OpenApiConfiguration