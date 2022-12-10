package org.example.order;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    protected final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    protected final String ROOT = "/api/v1/orders";

    public Response create(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT);
    }

    public Response get(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .get(ROOT);
    }
}