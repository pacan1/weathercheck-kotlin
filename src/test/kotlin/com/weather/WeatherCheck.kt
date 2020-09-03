package com.weather

import com.weather.hooks.AppConfiguration
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
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [AppConfiguration::class])
class WeatherCheck() : En {

    private lateinit var response: Response
    private lateinit var scenario: Scenario

    @Before
    fun initialise(scenario: Scenario) {
        this.scenario = scenario
    }

    @Step
    fun getWoeidInformation(woeid: String): Response {
        return RestAssured.given()
            .baseUri(META_WEATHER_API + WOEID_API)
            .get(woeid).thenReturn()
    }

    @Given("^a woeid (.*) is retrieved$")
    fun `a woeid is retrieved`(woeid: String){
        response = getWoeidInformation(woeid)
    }

    @When("the API returns successfully")
    fun `the API returns successfully`(){
        assertThat(
            "HTTP Status Code Unexpected",
            response.statusCode,
            equalTo(HttpStatus.SC_OK)
        )
    }

    @When("the API returns Not Found")
    fun `the API returns Not Found`(){
        assertThat(
            "HTTP Status Code Unexpected",
            response.statusCode,
            equalTo(HttpStatus.SC_NOT_FOUND)
        )
    }

    @Then("^the woeid location is (.*)$")
    fun `the woeid location is`(expectedLocation: String) {
        val actualLocation = response.body().jsonPath().getString("title")
        assertThat("The location is unexpected",
            actualLocation,
            equalTo(expectedLocation))
    }

    @Then("^the response contains \"(.*)\"$")
    fun `the response contains`(expectedErrorText: String) {
        println("Paula")
        println(response.body().prettyPeek())
        val responseText = response.body().jsonPath().getString("detail")
        assertThat("The error in unexpected",
            responseText,
            equalTo(expectedErrorText))
    }
}