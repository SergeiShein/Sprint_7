import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestListOrders {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    public void listOrdersNotEmpty(){
        Response response = given()
                .get("/api/v1/orders");
        List <HashMap<String, Object>> orders = response.jsonPath().getList("orders");
        Assert.assertFalse(orders.isEmpty());
    }
}
