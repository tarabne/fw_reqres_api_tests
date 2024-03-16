package ru.tarabne.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tarabne.models.lombok.*;

import java.util.Optional;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.tarabne.specs.LoginSpec.*;

public class LoginApiWithModelHWTests extends TestBase {

    @Test
    @DisplayName("Проверка корректности данных у пользователя по id")
    void checkUserInfoByIdTest() {
        GetUserResponseModel response = step("Make request", () ->
            given(getRequestSpec)
            .when()
                .get("/users/12")
            .then()
                .spec(loginResponseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/user-response-schema.json"))
                .extract().as(GetUserResponseModel.class));
        step("Check response", () ->
                assertAll("Check response values",
                () -> assertThat(response.getData().getId(), is(12)),
                () -> assertThat(response.getData().getFirst_name(), is("Rachel")),
                () -> assertThat(response.getData().getLast_name(), is("Howell"))));

    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    void getNonexistentUserTest() {
            step("Make request", () ->
            given(getRequestSpec)
            .when()
                .get("/users/333")
            .then()
                .spec(notFoundResponseSpec));
    }

    @Test
    @DisplayName("Проверка регистрации с заполнением email и пароля")
    void successfulRegistrationTest() {
        String email = "mikhail.petrovich@reqres.in";
        String password = "пароль";

        RegistrationRequestModel registrationData = new RegistrationRequestModel();
        registrationData.setEmail(email);
        registrationData.setPassword(password);

        RegistrationResponseModel response = step("Make request", () ->
            given(postRequestSpec)
                .body(registrationData)
            .when()
                .post("/api/register")
            .then()
                .spec(registrationResponseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/success-registration-schema.json"))
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertAll("Check response",
                () -> assertThat(response.getEmail(), equalTo(email)),
                () -> assertThat(response.getPassword(), equalTo(password)),
                () -> assertThat(response.getId(), notNullValue()),
                () -> assertThat(response.getCreatedAt(), notNullValue())));
    }

    @Test
    @DisplayName("Обновление пользователя целиком")
    void fullUserUpdateTest() {
        String name = "morpheus111";
        String job = "zion resident111";

        UpdateUserRequestModel userData = new UpdateUserRequestModel();
        userData.setName(name);
        userData.setJob(job);

        UpdateUserResponseModel response = step("Make request", () ->
            given(postRequestSpec)
                .body(userData)
            .when()
                .put("/users/2")
            .then()
                .spec(loginResponseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/success-update-schema.json"))
                .extract().as(UpdateUserResponseModel.class));
        step("Check response", () ->
                assertAll("Check response",
                () -> assertThat(response.getName(), equalTo(name)),
                () -> assertThat(response.getJob(), equalTo(job)),
                () -> assertThat(response.getUpdatedAt(), notNullValue())));
    }

    @Test
    @DisplayName("Частичное обновление пользователя")
    void partialUserUpdateTest() {
        String job = "zion resident111";

        UpdateUserRequestModel userData = new UpdateUserRequestModel();
        userData.setJob(job);

        UpdateUserResponseModel response = step("Make request", () ->
            given(postRequestSpec)
                .body(userData)
            .when()
                .patch("/users/2")
            .then()
                .spec(loginResponseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/success-update-schema.json"))
                .extract().as(UpdateUserResponseModel.class));
        step("Check response", () ->
                assertAll("Check response",
                () -> assertThat(response.getJob(), equalTo(job)),
                () -> assertThat(response.getUpdatedAt(), notNullValue())));
    }

    @Test
    @DisplayName("Проверка названия цвета по id в списке цветов")
    void checkNameInListByIdTest() {
        String color = "tigerlily";
        UnknownResponseModel response = step("Make request", () ->
            given(getRequestSpec)
            .when()
                .get("/unknown")
            .then()
                .spec(loginResponseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/unknown-response-schema.json"))
                .extract().as(UnknownResponseModel.class));

        Optional<String> colorName = response.getData().stream()
                .filter(colorData -> colorData.getId() == 5)
                .map(UnknownResponseModel.ColorData::getName)
                .findFirst();
        step("Check response", () ->
                assertAll("Check response",
                () -> assertTrue(colorName.isPresent()),
                () -> assertEquals(color, colorName.get())));

    }
}
