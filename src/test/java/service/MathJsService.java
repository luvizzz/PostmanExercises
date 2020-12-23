package service;

import domain.MultipleOperationsBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MathJsService {

    public static final String BASE_URL = "http://api.mathjs.org/v4/";

    public Response getMathJs(String expression) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .when().log().all()
                .param("expr", expression)
                .get()
                .then().log().all()
                .extract().response();
    }

    public Response postMathJs(MultipleOperationsBody body) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when().log().all()
                .post()
                .then().log().all()
                .extract().response();
    }
}
