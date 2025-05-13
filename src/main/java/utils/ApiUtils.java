package utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static final String BASE_URI = "https://pokeapi.co";

    public static Response getRequest(String endpoint) {
        return given()
                .baseUri(BASE_URI)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
}