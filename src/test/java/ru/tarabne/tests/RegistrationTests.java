package ru.tarabne.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tarabne.models.RegistrationModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.tarabne.specs.LoginSpec.postRequestSpec;
import static ru.tarabne.specs.LoginSpec.registrationResponseSpec;

@DisplayName("Тесты на регистрацию")
@Owner("tarabne")
@Story("Регистрация и авторизация пользователя")
@Feature("API | Регистрация")
public class RegistrationTests extends BaseTest {
    @Test
    @DisplayName("Проверка регистрации с заполнением email и пароля")
    @Severity(SeverityLevel.BLOCKER)
    void successfulRegistrationTest() {
        String email = "mikhail.petrovich@reqres.in";
        String password = "пароль";

        RegistrationModel registrationData = new RegistrationModel();
        registrationData.setEmail(email);
        registrationData.setPassword(password);

        RegistrationModel response = step("Make request", () ->
                given(postRequestSpec)
                        .body(registrationData)
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(registrationResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/success-registration-schema.json"))
                        .extract().as(RegistrationModel.class));
        step("Check response", () ->
                assertAll("Check response",
                        () -> assertThat(response.getEmail(), equalTo(email)),
                        () -> assertThat(response.getPassword(), equalTo(password)),
                        () -> assertThat(response.getId(), notNullValue()),
                        () -> assertThat(response.getCreatedAt(), notNullValue())));
    }
}
