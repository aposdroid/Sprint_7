package org.example.courier;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public static final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    protected final String ROOT = "/api/v1/courier";

    public Response createCourier(Courier courier){
    return given().log().all()
            .contentType(ContentType.JSON)
            .baseUri(BASE_URI)
            .body(courier)
            .when()
            .post(ROOT);
    }

    public Response login(Courier courier){
        return given().log().all()
            .contentType(ContentType.JSON)
            .baseUri(BASE_URI)
            .body(courier)
            .when()
            .post(ROOT + "/login");
    }

    public Response deleteCourier(int courierId){
        String json = String.format("{\"id\": \"%d\"}", courierId);
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(json)
                .when()
                .delete(ROOT + "/" + courierId);
    }
}