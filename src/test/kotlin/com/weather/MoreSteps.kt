package com.weather

import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.java8.En
import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.springframework.beans.factory.annotation.Autowired

class MoreSteps @Autowired constructor(
    private val weatherCheck: WeatherCheck
) : En {

    private lateinit var scenario: Scenario
    private lateinit var response: Response


    @Before
    fun initialise(scenario: Scenario) {
        this.scenario = scenario
    }

    @Given("demo use of spring for DI")
    fun `demo use of spring for DI`(){
        val woeid = "66667"
        weatherCheck.`a woeid is retrieved`(woeid)  //call a step
        weatherCheck.getWoeidInformation(woeid) //call a fun
    }

}