package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.Order;
import org.example.order.OrderChecks;
import org.example.order.OrderClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)

public class CreateOrderTest {
    private final OrderClient data = new OrderClient();
    private final OrderChecks checks = new OrderChecks();
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List color) {
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
    @Description("Проверим, что когда создаёшь заказ:\n" +
            "можно указать один из цветов — BLACK или GREY;\n" +
            "можно указать оба цвета;\n" +
            "можно совсем не указывать цвет;\n" +
            "тело ответа содержит track.")

    public void orderCreation(){
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = data.create(order);
                            checks.orderCreatedSuccessfully(response);
    }
}