package com.cicdclouds;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class GreetingApiTest3 {

    @Test
    public void testGreetingContainsName() {
        String baseUrl = System.getProperty("app.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getenv("APP_URL");
        }
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Target URL not provided! Use -Dapp.url or set APP_URL env variable.");
        }

        RestAssured.baseURI = baseUrl;

        given()
            .param("userName", "Hariprasad")
        .when()
            .post()
        .then()
            .statusCode(200)
            .body(containsString("Hariprasad"));  // ✅ works with plain text response
    }
}