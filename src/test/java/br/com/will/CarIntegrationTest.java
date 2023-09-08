package br.com.will;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class CarIntegrationTest {

    @Test
    public void testListAllCars() {
        given().contentType(ContentType.JSON).header("x-tenant", "tenant1")
                .when().get("/carController/listAll")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));
    }

}
