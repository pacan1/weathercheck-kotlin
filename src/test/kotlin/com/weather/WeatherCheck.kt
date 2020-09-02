package com.weather

import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.java8.En
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

class WeatherCheck() : En {

    private lateinit var scenario: Scenario
    private lateinit var response: Response

    private val metaWeatherApi = "https://www.metaweather.com/api/"
    private val locationApi = "location/search/?query="
    private val latlongApi = "location/search/?lattlong="
    private val woeidApi = "location/"

    @Before
    fun initialise(scenario: Scenario) {
        this.scenario = scenario
    }

    private fun getWoeidInformation(woeid: String): Response {

        return RestAssured.given()
            .baseUri(metaWeatherApi + woeidApi)
            .get(woeid).thenReturn()
    }

    @Given("^a valid woeid (.*) is retrieved$")
    fun `a valid woeid is retrieved`(woeid: String){
        response = getWoeidInformation(woeid)
        assertThat(
            "HTTP Status Code Unexpected",
            response.statusCode,
            equalTo(HttpStatus.SC_OK)
        )

    }

    @When("the API returns successfully")
    fun `the API returns successfully`(){
        assertThat(
            "HTTP Status Code Unexpected",
            response.statusCode,
            equalTo(HttpStatus.SC_OK)
        )
    }

    @Then("^the woeid location is (.*)$")
    fun `the woeid location is`(expectedLocation: String) {
        val actualLocation = response.body().jsonPath().getString("title")
        assertThat("The location is unexpected",
            actualLocation,
            equalTo(expectedLocation))
    }
}