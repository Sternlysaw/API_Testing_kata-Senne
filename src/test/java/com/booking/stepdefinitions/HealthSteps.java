package com.booking.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthSteps {
    private Response response;

    @When("I check the API health")
    public void iCheckTheApiHealth() {
        response =
                given()
                        .log().all()
                        .when()
                        .get("https://automationintesting.online/api/booking/actuator/health");
    }

    @Then("the API status should be UP")
    public void theApiStatusShouldBeUp() {
        response.then()
                .statusCode(200)
                .body("status", equalTo("UP"))
                .log().all();
    }
}
