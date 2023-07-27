import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)

public class TestAddOrder {
    private final String jsonName;
    public TestAddOrder(String jsonName) {
        this.jsonName = jsonName;
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"oneBlackColourOrder.json"},
                {"oneGreyColourOrder.json"},
                {"twoColourOrder.json"},
                {"withoutColourOrder.json"},
        };
    }
    @Test
    public void testOrder() {
        File json = new File("src/test/resources/Order/" + jsonName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track",  notNullValue())
                .and()
                .statusCode(201);
    }
}
