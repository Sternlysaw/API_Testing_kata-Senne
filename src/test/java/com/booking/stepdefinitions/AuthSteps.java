package com.booking.stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class AuthSteps {
    public static String token;

    @Given("I am authenticated")
    public void iAmAuthenticated() {
        Response response = given()
                .contentType("application/json")
                .body("""
                      {
                        "username": "admin",
                        "password": "password"
                      }
                      """)
                .when()
                .post("/auth/login");

        token = response.then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
