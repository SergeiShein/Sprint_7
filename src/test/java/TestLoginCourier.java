import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class TestLoginCourier {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        File json = new File("src/test/resources/Courier/valideCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
    }
    @Test
    public void valideAutorization(){
        File json = new File("src/test/resources/Courier/valideCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        System.out.println(response.getBody().asString());
        response.then().assertThat().body("id",  notNullValue())
                .and()
                .statusCode(200);

    }
    @Test
    public void incorrectLogin(){
        File json = new File("src/test/resources/Courier/incorrectLogin.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        System.out.println(response.getBody().asString());
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }
    @Test
    public void incorrectPassword(){
        File json = new File("src/test/resources/Courier/incorrectPassword.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        System.out.println(response.getBody().asString());
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }
    @Test
    public void emptyPassword(){
        File json = new File("src/test/resources/Courier/invalideCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        System.out.println(response.getBody().asString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }
    @Test
    public void emptyLogin(){
        File json = new File("src/test/resources/Courier/invalideSecondCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        System.out.println(response.getBody().asString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }
    @AfterClass
    public static void deleteCourier() {
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
    }
}