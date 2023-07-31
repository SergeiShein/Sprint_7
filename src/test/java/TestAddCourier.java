import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class TestAddCourier {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    public void createNewCourier(){
        File json = new File("src/test/resources/Courier/valideCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
    @Test
    public void createRepeatCourier(){
        File json = new File("src/test/resources/Courier/valideSecondCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        Response repeatResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        repeatResponse.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
    @Test
    public void createInvalidCourier(){
        File json = new File("src/test/resources/Courier/invalideCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @Test
    public void createInvalidSecondCourier(){
        File json = new File("src/test/resources/Courier/invalideSecondCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @AfterClass
    public static void deleteCourier(){
        File json = new File("src/test/resources/Courier/valideCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        Integer id = response.path("id");
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + id);
        File secondJson = new File("src/test/resources/Courier/valideSecondCourier.json");
        Response secondResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(secondJson)
                        .when()
                        .post("/api/v1/courier/login");
        Integer secondId = secondResponse.path("id");
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + secondId);
    }
}
