package org.example.order;

import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.notNullValue;
import static java.net.HttpURLConnection.*;

public class OrderChecks {
    public void orderCreatedSuccessfully(Response response){
                response.then().log().all()
                .extract().response();
                response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(HTTP_CREATED);
    }

    public void orderListNotNull(Response response){
                response.then().log().all()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(HTTP_OK);
    }
}