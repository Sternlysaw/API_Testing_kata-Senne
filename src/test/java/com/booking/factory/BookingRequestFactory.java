package com.booking.factory;

import com.booking.builders.BookingTestDataBuilder;
import com.booking.models.BookingRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingRequestFactory {

    public static Response createBookingSuccessfully() {
        int roomId = given()
                .when()
                .get("/room")
                .then()
                .statusCode(200)
                .extract()
                .path("rooms[0].roomid");

        Response response;

        do {
            BookingRequest request = new BookingTestDataBuilder()
                    .withRoomId(roomId)
                    .build();

            response = given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post("/booking");

        } while (response.statusCode() == 409);

        response.then().statusCode(201);
        return response;
    }
    public static Response createBookingAndReturnResponse(BookingRequest request) {
        Response response;

        do {
            response = given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post("/booking");
        } while (response.statusCode() == 409);

        response.then().statusCode(201);
        return response;
    }
}