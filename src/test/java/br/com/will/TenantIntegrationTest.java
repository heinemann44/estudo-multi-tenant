package br.com.will;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import br.com.will.dto.TenantDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class TenantIntegrationTest {

    @Test
    public void testFailListAllTenants() {
        given().contentType(ContentType.JSON).header("x-tenant", "")
                .when().get("/tenantController/listAll")
                .then()
                .statusCode(403);
    }

    @Test
    public void testFailNoHeader() {
        given().contentType(ContentType.JSON)
                .when().get("/tenantController/listAll")
                .then()
                .statusCode(403);
    }

    @Test
    public void testFailNoneListAllTenants() {
        given().contentType(ContentType.JSON).header("x-tenant", "none")
                .when().get("/tenantController/listAll")
                .then()
                .statusCode(400).body("mensagem", is("No config for this client"));
    }

    @Test
    public void testListAllTenants() {
        given().contentType(ContentType.JSON).header("x-tenant", "default")
                .when().get("/tenantController/listAll")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));
    }

    @Test
    public void testSaveTenants() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        TenantDTO tenant = new TenantDTO();
        tenant.setDatasourceHost("jdbc:postgresql://localhost:5432/");
        tenant.setDatasourceName(simpleDateFormat.format(new Date()));
        tenant.setDatasourcePassword("password");
        tenant.setDatasourceUsername("user");
        tenant.setFlagSSL(Boolean.FALSE);
        tenant.setTenantId(simpleDateFormat.format(new Date()));

        given().contentType(ContentType.JSON).header("x-tenant", "default")
                .body(tenant)
                .when().post("/tenantController/save")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateTenants() {
        given().contentType(ContentType.JSON).header("x-tenant", "default")
                .when().get("/tenantController/listAll")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));
    }

}
