package com.vsarzhynskyi.shop.items.demo.functionaltests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class SwaggerTest extends BaseFunctionalTest {

    @Test
    void shouldExposeSwaggerUi() {
        RestAssured.given()
                .when()
                .port(serverPort)
                .get("/swagger-ui.html")
                .then()
                .statusCode(SC_OK);
    }

    @Test
    void shouldExposeSwaggerApi() {
        RestAssured.given()
                .when()
                .port(serverPort)
                .get("/v3/api-docs/public-api")
                .then()
                .statusCode(SC_OK)
                .body("info.title", Matchers.is("Shopping Items Demo Service"));
    }

}
