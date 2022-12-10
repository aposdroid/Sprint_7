package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class OrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

        @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters
    public static Object[][] colorOfScooter(){
        return new Object[][]{
                { "Ваагн", "Вовк", "Елабуга Мира 1а", "5", "89564568525", 5, "2023-06-06", "всем привет", Arrays.asList("BLACK")},
                { "Алексей", "Второй", "Челны Московский 130", "7", "8855746407", 2, "2022-12-12", "Бу", Arrays.asList("GREY")},
                { "Усама", "Бен-Ладен", "горы Афганистана", "6", "89654236584", 7, "2023-02-05", "Бабах", Arrays.asList("BLACK", "GREY")},
                { "Barack", "Obama", "White House", "22", "202-456-1414", 1, "2023-01-01", "Hello", Arrays.asList("")},
        };
    }

    @Test
    @DisplayName("Creation order")
    @Description("You can specify one of the colors - BLACK or GRAY; both colors can be specified; you can not specify the color at all; the response body contains track.")

    public void orderCreation(){
        //Order order = new Order("Ваагн", "Вовк", "Елабуга Мира 1а", "Динамо", "89564568525", 5, "2023-06-06", "всем привет", colorOfScooter());
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
                         response.then().log().all()
                        .extract().response();
                         response.then().assertThat().body("track", notNullValue())
                        .and()
                        .statusCode(201);
    }
}