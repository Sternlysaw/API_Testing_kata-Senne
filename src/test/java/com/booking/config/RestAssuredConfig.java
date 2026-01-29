package com.booking.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
public class RestAssuredConfig {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://automationintesting.online";
        RestAssured.basePath = "/api";
    }
}
