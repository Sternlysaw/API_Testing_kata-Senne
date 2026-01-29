package com.booking.config;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
public class CucumberHooks {
    @Before
    public void setup() {
        RestAssured.baseURI = "https://automationintesting.online";
        RestAssured.basePath = "/api";
    }
}
