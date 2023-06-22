package io.immortal.spicemustflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableCaching
@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableTransactionManagement
class SpiceMustFlowApplication

fun main(args: Array<String>) {
    runApplication<SpiceMustFlowApplication>(*args)
}