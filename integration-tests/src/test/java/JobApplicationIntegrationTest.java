import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class JobApplicationIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4005";
    }

    @Test
    public void shouldReturnJobApplicationWithValidToken() {
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                """;

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/job-applications")
                .then()
                .statusCode(200)
                .body("items", notNullValue());
    }
}