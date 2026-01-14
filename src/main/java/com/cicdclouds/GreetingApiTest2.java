package com.cicdclouds;

import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class GreetingApiTest2 {

    @ParameterizedTest
    @ValueSource(strings = {"Hariprasad", "Anita", "Ravi", "CloudUser"})
    public void testGreetingContainsName(String userName) {
        String baseUrl = System.getProperty("app.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getenv("APP_URL");
        }
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Target URL not provided! Use -Dapp.url or set APP_URL env variable.");
        }

        RestAssured.baseURI = baseUrl;

        given()
            .param("userName", userName)
        .when()
            .post()
        .then()
            .statusCode(200)
            .body(containsString(userName));  // ✅ checks response contains the entered name
    }
}