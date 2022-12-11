package org.example.courier;

import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static java.net.HttpURLConnection.*;

public class CourierChecks {
    public void createdSuccessfully(Response response){
        response.then().log().all()
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(HTTP_CREATED);
    }

    public void creationFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(HTTP_CONFLICT);
    }

    public void creationWithoutPasswordFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    public void loginSuccessfully(Response response){
        response.then().log().all()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(HTTP_OK);
    }

    public void loginFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HTTP_NOT_FOUND);
    }

    public void loginWithoutPasswordFailed(Response response){
        response.then().log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    public  void deleteSuccessfully(Response response){
        response.then().log().all()
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }
}
