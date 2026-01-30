package com.booking.stepdefinitions;

import com.booking.builders.BookingTestDataBuilder;
import com.booking.models.BookingRequest;
import com.booking.factory.BookingRequestFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingSteps {

    private Response response;
    private int bookingId;
    private BookingRequest bookingRequest;

    @When("I create a booking with valid data")
    public void iCreateABookingWithValidData() {
        response = BookingRequestFactory.createBookingSuccessfully();
    }

    @Then("the booking is created successfully")
    public void theBookingIsCreatedSuccessfully() {
        response.then()
                .statusCode(201)
                .body("bookingid", notNullValue());
    }

    @Given("a booking exists")
    public void aBookingExists() {
        bookingRequest = new BookingTestDataBuilder().build();

        response = BookingRequestFactory.createBookingAndReturnResponse(bookingRequest);

        bookingId = response
                .then()
                .extract()
                .path("bookingid");
    }

    @When("I retrieve the booking by id")
    public void iRetrieveTheBookingById() {
        response = given()
                .cookie("token", AuthSteps.token)
                .when()
                .get("/booking/" + bookingId);
    }


    @Then("the booking details are returned correctly")
    public void theBookingDetailsAreReturnedCorrectly() {
        response.then()
                .statusCode(200)
                .body("firstname", equalTo(bookingRequest.getFirstname()))
                .body("lastname", equalTo(bookingRequest.getLastname()))
                .body("depositpaid", equalTo(bookingRequest.isDepositpaid()))
                .body("bookingdates.checkin",
                        equalTo(bookingRequest.getBookingdates().getCheckin()))
                .body("bookingdates.checkout",
                        equalTo(bookingRequest.getBookingdates().getCheckout()));
    }

    @When("I create a booking with an invalid email")
    public void iCreateABookingWithAnInvalidEmail() {
        BookingRequest request = new BookingTestDataBuilder()
                .withInvalidEmail()
                .build();

        response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/booking");
    }
    @Then("the booking is rejected with status code {int}")
    public void theBookingIsRejectedWithStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
    }
}
