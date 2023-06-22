package io.immortal.spicemustflow

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpiceMustFlowApplicationTests() {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun contextLoads() {
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}
