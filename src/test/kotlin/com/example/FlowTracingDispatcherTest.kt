package com.example

import io.kotest.core.spec.style.StringSpec
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@MicronautTest
class StrangeFlowBehaviorTest(testClient: TestClient) : StringSpec() {

    init {
        "Flow emission and collection in controller works" {
            testClient.test()
        }
    }

    @Client("/test")
    interface TestClient {
        @Get
        suspend fun test(): HttpResponse<List<String>>
    }

    @Controller("/test")
    class TestController {
        @Get
        suspend fun test() {
            flow { emit("Test") }.collect()
        }
    }
}
