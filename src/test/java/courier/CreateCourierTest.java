package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.courier.CourierGenerator;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
      /*
        проверим, что:
        - курьера можно создать;
        - чтобы создать курьера, нужно передать в ручку все обязательные поля;
        - запрос возвращает правильный код ответа;
        - успешный запрос возвращает ok: true
         */
    @DisplayName("verification of courier creation")
    @Description("checking the creation of a courier; response code; the impossibility of creating two identical couriers; when creating a user with a login that already exists, an error is returned")
    public void createCourier() {
        var courier = new CourierGenerator().random();

        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
                         response.then().log().all()
                        .assertThat().body("ok", equalTo(true))
                                 .and()
                        .statusCode(201);

        /*
        проверим, что:
        -нельзя создать двух одинаковых курьеров;
        -если создать пользователя с логином, который уже есть, возвращается ошибка
         */
        Response response2 =
                given().log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier");
                response2.then().log().all()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Create courier without password") // имя теста
    @Description("If one of the fields is missing, the request to create a courier returns an error.")
      /*
        проверим, что:
        - если одного из полей нет, запрос на создание курьера возвращает ошибку.
         */
    public void createWithoutPassword(){
        String json = "{\"login\": \"Loda\", \"password\": \"\"}";
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Login courier") // имя теста
    @Description("- The courier can log in; for authorization, you need to pass all the required fields; a successful request returns an id.")
      /*
        проверим, что:
        - курьер может авторизоваться;
        - для авторизации нужно передать все обязательные поля;
        - успешный запрос возвращает id.
         */
    public void loginCourier(){
        String json = "{\"login\": \"Loda\", \"password\": \"1234\"}";
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().log().all()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Wrong login when logging in") // имя теста
    @Description("The system will return an error if the login is incorrect; if you log in as a non-existent user, the request returns an error.")
   /*
        проверим, что:
        - система вернёт ошибку, если неправильно указать логин;
        - если авторизоваться под несуществующим пользователем, запрос возвращает ошибку.
         */
    public void wrongLoginCourier() {
        String json = "{\"login\": \"Lodo\", \"password\": \"1234\"}";
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
                         response.then().log().all()
                        .assertThat().body("message", equalTo("Учетная запись не найдена"))
                                 .and()
                        .statusCode(404);
    }

    @Test
    @DisplayName("Wrong password when logging in") // имя теста
    @Description("The system will return an error if the password is incorrect.")
   /*
        проверим, что:
        - система вернёт ошибку, если неправильно указать пароль.
         */
    public void wrongPasswordCourier() {
        String json = "{\"login\": \"Loda\", \"password\": \"1233\"}";
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
                         response.then().log().all()
                        .assertThat().body("message", equalTo("Учетная запись не найдена"))
                                 .and()
                        .statusCode(404);
    }

    @Test
    @DisplayName("Login without password") // имя теста
    @Description("If some field is missing, the request returns an error.")
   /*
        проверим, что:
        - если какого-то поля нет, запрос возвращает ошибку.
         */
    public void loginWithoutPassword(){
        String json = "{\"login\": \"Loda\", \"password\": \"\"}";
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}


