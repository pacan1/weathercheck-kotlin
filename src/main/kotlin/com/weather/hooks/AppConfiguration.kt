package com.weather.hooks

import com.weather.HTTPS_PROTOCOL
import com.weather.infrastructure.restassured.StepWrapperAllureRestAssured
import com.weather.requestLogger
import com.weather.responseLogger
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.restassured.RestAssured
import io.restassured.config.*
import org.springframework.context.annotation.Bean

//@ComponentScan("com.weather.*")
class AppConfiguration {

//    any class that you want to use as a bean that doesn't have a Given, When or Then in it will need to be created here'

    @Bean
    fun configureRestAssure() {
        RestAssured.filters(
            requestLogger, responseLogger, StepWrapperAllureRestAssured()
                .setRequestTemplate("http-request.ftl")
                .setResponseTemplate("http-response.ftl")
        )
        RestAssured.config = RestAssuredConfig.config()
            .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation(HTTPS_PROTOCOL))
            .decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"))
            .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"))
            .httpClient(
                HttpClientConfig.httpClientConfig()
            )
    }
}
