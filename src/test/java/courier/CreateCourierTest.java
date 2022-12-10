package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.Courier;
import org.example.courier.CourierChecks;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.junit.Test;

public class CreateCourierTest {
    private final CourierClient client = new CourierClient();
    private final CourierChecks checks = new CourierChecks();
    private final CourierGenerator generator = new CourierGenerator();

    private int courierId;

    @Test
    @DisplayName("Verification of courier creation, then delete him")
    @Description("Проверим, что:\n" +
            "- курьера можно создать;\n" +
            "- чтобы создать курьера, нужно передать в ручку все обязательные поля;\n" +
            "- запрос возвращает правильный код ответа;\n" +
            "- успешный запрос возвращает ok: true.\n" +
            "-если создать пользователя с логином, который уже есть, возвращается ошибка.")

    public void createCourier() {
        Courier courier = generator.random();//создали рандомный логин
        Response response = client.create(courier);//создали курьера
                            checks.createdSuccessfully(response);//проверили ответ
                            courier.setFirstName(null);//убрали ненужное для логина поле
        Response responseId = client.login(courier);//залогинились в созданный аккаунт
        responseId.then().log().all();
        int courierId = responseId.path("id");//вычислили курьера по айди
        Response responseDelete = client.delete(courierId);//удалили курьера по айди
        checks.deleteSuccessfully(responseDelete);//проверили ответ
    }

    @Test
    @DisplayName("Create courier twice")
    @Description("Проверим, что:\n" +
                 "-нельзя создать двух одинаковых курьеров;\n" +
                 "-если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void createCourierTwice(){
        Courier courier = generator.random();
        client.create(courier);
        Response response = client.create(courier);
        checks.creationFailed(response);
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Проверим, что:\n" +
            "- если одного из полей нет, запрос на создание курьера возвращает ошибку.")

    public void createWithoutPassword() {
        Courier courier = generator.generic();
        courier.setPassword(null);
        Response response = client.create(courier);
                            checks.creationWithoutPasswordFailed(response);
    }
}