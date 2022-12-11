package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.Courier;
import org.example.courier.CourierChecks;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.junit.Test;

public class LoginCourierTest {

    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierChecks checks = new CourierChecks();

    @Test
    @DisplayName("Login courier")
    @Description("Проверим, что:\n" +
            "- курьер может авторизоваться;\n" +
            "- для авторизации нужно передать все обязательные поля;\n" +
            "- успешный запрос возвращает id.")

    public void loginCourier(){
        Courier courier = generator.loginData();
        Response response = client.login(courier);
                            checks.loginSuccessfully(response);
    }

    @Test
    @DisplayName("Wrong login when logging in")
    @Description("Проверим, что:\n" +
            "- система вернёт ошибку, если неправильно указать логин;\n" +
            "- если авторизоваться под несуществующим пользователем, запрос возвращает ошибку.")

    public void wrongLoginCourier() {
        Courier courier = generator.loginData();
        courier.setLogin("Lodo");
        Response response = client.login(courier);
                            checks.loginFailed(response);
    }

    @Test
    @DisplayName("Wrong password when logging in")
    @Description("Проверим, что:\n" +
            "- система вернёт ошибку, если неправильно указать пароль.")

    public void wrongPasswordCourier() {
        Courier courier = generator.loginData();
        courier.setPassword("1233");
        Response response = client.login(courier);
                            checks.loginFailed(response);
    }

    @Test
    @DisplayName("Login without password")
    @Description("Проверим, что:\n" +
            "- если какого-то поля нет, запрос возвращает ошибку.")

    public void loginWithoutPassword(){
        Courier courier = generator.loginData();
        courier.setPassword("");
        Response response = client.login(courier);
                            checks.loginWithoutPasswordFailed(response);
    }
}