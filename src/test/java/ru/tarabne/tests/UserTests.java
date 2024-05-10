package ru.tarabne.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tarabne.models.GetUserResponseModel;
import ru.tarabne.models.UpdateUserModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.tarabne.specs.ReqresSpec.*;

@DisplayName("Тесты на список пользователей")
@Owner("tarabne")
@Story("Список пользователей")
@Feature("API | CRUD операции над пользователем")
public class UserTests extends BaseTest {

    @Test
    @DisplayName("Проверка корректности данных у пользователя по id")
    @Severity(SeverityLevel.CRITICAL)
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
                        () -> assertThat(response.getData().getFirstName(), is("Rachel")),
                        () -> assertThat(response.getData().getLastName(), is("Howell"))));
    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    @Severity(SeverityLevel.MINOR)
    void getNonexistentUserTest() {
        step("Make request", () ->
                given(getRequestSpec)
                        .when()
                        .get("/users/333")
                        .then()
                        .assertThat()
                        .statusCode(404)
                        .spec(loggingSpec));
    }

    @Test
    @DisplayName("Обновление пользователя целиком")
    @Severity(SeverityLevel.NORMAL)
    void fullUserUpdateTest() {
        String name = "morpheus111";
        String job = "zion resident111";

        UpdateUserModel userData = new UpdateUserModel();
        userData.setName(name);
        userData.setJob(job);

        UpdateUserModel response = step("Make request", () ->
                given(postRequestSpec)
                        .body(userData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(loginResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/success-update-schema.json"))
                        .extract().as(UpdateUserModel.class));
        step("Check response", () ->
                assertAll("Check response",
                        () -> assertThat(response.getName(), equalTo(name)),
                        () -> assertThat(response.getJob(), equalTo(job)),
                        () -> assertThat(response.getUpdatedAt(), notNullValue())));
    }

    @Test
    @DisplayName("Частичное обновление пользователя")
    @Severity(SeverityLevel.NORMAL)
    void partialUserUpdateTest() {
        String job = "zion resident111";

        UpdateUserModel userData = new UpdateUserModel();
        userData.setJob(job);

        UpdateUserModel response = step("Make request", () ->
                given(postRequestSpec)
                        .body(userData)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(loginResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/success-update-schema.json"))
                        .extract().as(UpdateUserModel.class));
        step("Check response", () ->
                assertAll("Check response",
                        () -> assertThat(response.getJob(), equalTo(job)),
                        () -> assertThat(response.getUpdatedAt(), notNullValue())));
    }

    @Test
    @DisplayName("Удаление пользователя")
    @Severity(SeverityLevel.CRITICAL)
    void userDeletionTest() {
        step("Make request", () ->
                given(getRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .assertThat()
                        .statusCode(204)
                        .spec(loggingSpec));
    }
}
