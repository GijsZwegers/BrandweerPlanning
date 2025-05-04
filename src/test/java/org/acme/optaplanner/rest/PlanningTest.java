package org.acme.optaplanner.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PlanningTest {

    @Test
    void test() {
        given()
                .get("planning/2025-05-04")
                .prettyPeek();
    }

}
