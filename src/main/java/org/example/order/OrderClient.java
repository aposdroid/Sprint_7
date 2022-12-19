package org.example.order;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.courier.CourierClient;

import static io.restassured.RestAssured.given;

public class OrderClient {
    protected final String ROOT = "/api/v1/orders";

    public Response create(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(CourierClient.BASE_URI)
                .body(order)
                .when()
                .post(ROOT);
    }

    public Response get(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(CourierClient.BASE_URI)
                .body(order)
                .when()
                .get(ROOT);
    }
}