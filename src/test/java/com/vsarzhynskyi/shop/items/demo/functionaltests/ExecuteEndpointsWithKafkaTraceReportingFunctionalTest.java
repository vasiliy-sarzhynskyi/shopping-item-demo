package com.vsarzhynskyi.shop.items.demo.functionaltests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class ExecuteEndpointsWithKafkaTraceReportingFunctionalTest extends BaseFunctionalTest {

    @Autowired
    private ShoppingItemHttpTraceReportingKafkaConsumer shoppingItemHttpTraceReportingKafkaConsumer;

    @Test
    void shouldSuccessfullyExecuteEndpointsAndProduceKafkaTraceReportingMessages() {
        // create shopping item
        String shoppingItemRequestJson =
                """
                                {
                                  "name": "demo item",
                                  "description": "demo description"
                                }
                        """;

        var createdItemId = RestAssured.given()
                .when()
                .port(serverPort)
                .contentType(ContentType.JSON)
                .body(shoppingItemRequestJson)
                .when()
                .post("shopping-items")
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .body()
                .as(Long.class);
        log.info("shopping item has been created with ID = {}", createdItemId);

        // verify HTTP trace reporting Kafka message was sent for create shopping item
        var expectedPostHttpTraceReportingKafkaMessage = """
                   {
                        "method":"POST",
                        "uri":"/shopping-items",
                        "statusCode":201
                    }
                """;
        var consumedKafkaMessages = shoppingItemHttpTraceReportingKafkaConsumer.waitAndReceiveRecords(1);
        boolean isMatchedExpectation = consumedKafkaMessages.stream().anyMatch(consumedKafkaMessage ->
                matchJsonNonStrict(expectedPostHttpTraceReportingKafkaMessage, consumedKafkaMessage));
        assertTrue(isMatchedExpectation);

        // fetch shopping item
        var fetchedItem = RestAssured.given()
                .when()
                .port(serverPort)
                .get(format("shopping-items/%d", createdItemId))
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .asString();
        log.info("shopping item has been fetched {}", fetchedItem);

        // verify HTTP trace reporting Kafka message was sent for fetch shopping item
        var expectedGetHttpTraceReportingKafkaMessage = """
                   {
                        "method":"GET",
                        "uri":"/shopping-items/$itemId",
                        "statusCode":200
                    }
                """.replace("$itemId", String.valueOf(createdItemId));
        consumedKafkaMessages = shoppingItemHttpTraceReportingKafkaConsumer.waitAndReceiveRecords(1);
        isMatchedExpectation = consumedKafkaMessages.stream().anyMatch(consumedKafkaMessage ->
                matchJsonNonStrict(expectedGetHttpTraceReportingKafkaMessage, consumedKafkaMessage));
        assertTrue(isMatchedExpectation);
    }

    @SneakyThrows
    private boolean matchJsonNonStrict(String expected, String actual) {
        return JSONCompare.compareJSON(expected, actual, JSONCompareMode.LENIENT).passed();
    }

}
