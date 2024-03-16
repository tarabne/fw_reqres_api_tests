package ru.tarabne.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;
import ru.tarabne.models.lombok.LoginBodyLombokModel;
import ru.tarabne.models.lombok.LoginResponseLombokModel;
import ru.tarabne.models.lombok.MissingPasswordModel;
import ru.tarabne.models.pojo.LoginBodyModel;
import ru.tarabne.models.pojo.LoginResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.tarabne.helpers.CustomAllureListener.withCustomTemplates;
import static ru.tarabne.specs.LoginSpec.*;

public class WithModelTests {

    @Test
    void successfulLoginPOJOTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = given()
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .body(authData)
            .when()
                .post("https://reqres.in/api/login")
            .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
//                .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginLombokTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
//                .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
//                .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginCustomAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
//                .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithStepsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make request", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().method()
                    .log().body()
                    .log().headers()
                    .contentType(JSON)
                    .body(authData)
                .when()
                    .post("https://reqres.in/api/login")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(LoginResponseLombokModel.class));
//                .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
        step("Check response", () ->
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successfulLoginWithSpecificationsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                .when()
                        .post()
                .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));
        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void missingPasswordTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");

        MissingPasswordModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(missingPasswordResponseSpec)
                        .extract().as(MissingPasswordModel.class));
        step("Check response", () ->
                assertEquals("Missing password", response.getError()));
    }
}
