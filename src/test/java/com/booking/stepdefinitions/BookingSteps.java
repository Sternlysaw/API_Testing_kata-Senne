package com.booking.stepdefinitions;

import com.booking.builders.BookingTestDataBuilder;
import com.booking.models.BookingRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingSteps {

    private Response response;

    @When("I create a booking with valid data")
    public void iCreateABookingWithValidData() {

        for (int attempt = 0; attempt < 5; attempt++) {
            BookingRequest booking = BookingTestDataBuilder.validBooking();

            response = given()
                    .contentType(ContentType.JSON)
                    .body(booking)
                    .log().all()
                    .when()
                    .post("/booking");

            if (response.statusCode() == 201) {
                return; // success
            }
        }

        throw new AssertionError("Could not create booking after multiple attempts due to conflicts");
    }

    @Then("the booking is created successfully")
    public void theBookingIsCreatedSuccessfully() {
        response.then()
                .log().all()
                .statusCode(201)
                .body("bookingid", notNullValue());
    }
}
