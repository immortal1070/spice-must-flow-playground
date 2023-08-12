package io.immortal.spicemustflow.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgreSQLContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=" + sqlContainer.jdbcUrl,
            "spring.datasource.username=" + sqlContainer.username,
            "spring.datasource.password=" + sqlContainer.password
        ).applyTo(configurableApplicationContext.environment)
    }

    companion object {
        private val sqlContainer: PostgreSQLContainer<*> = PostgreSQLContainer(
            DockerImageName.parse("postgres")
        )
            .withDatabaseName("spice_must_flow")
            .withUsername("postgres")
            .withPassword("postgres")

        init {
            sqlContainer.start()
        }
    }
}