package org.example.courier;

import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierChecks {
    public void createdSuccessfully(Response response){
        response.then().log().all()
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    public void creationFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    public void creationWithoutPasswordFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    public void loginSuccessfully(Response response){
        response.then().log().all()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    public void loginFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    public void loginWithoutPasswordFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    public  void deleteSuccessfully(Response response){
        response.then().log().all()
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }
}
