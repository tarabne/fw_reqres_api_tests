package ru.tarabne.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.tarabne.models.UnknownResponseModel;

import java.util.Optional;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;
import static ru.tarabne.specs.ReqresSpec.getRequestSpec;
import static ru.tarabne.specs.ReqresSpec.loginResponseSpec;

@DisplayName("Тесты на список цветов")
@Owner("tarabne")
@Feature("Предзаполнение базы цветов")
@Issue("RQRS-274")
public class ColorTests extends BaseTest {
    @CsvFileSource(resources = "/testdata/checkColorNameInListByIdTest.csv")
    @ParameterizedTest(name = "Цвет с id = {1} на {0} странице имеет название \"{2}\"")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка названия цвета по id в списке цветов.")
    void checkColorNameInListByIdTest(int pageNumber, int colorId, String color) {
        UnknownResponseModel response = step("Make request", () ->
                given(getRequestSpec)
                        .queryParam("page", pageNumber)
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(loginResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/unknown-response-schema.json"))
                        .extract().as(UnknownResponseModel.class));

        Optional<String> colorName = response.getData().stream()
                .filter(colorData -> colorData.getId() == colorId)
                .map(UnknownResponseModel.ColorData::getName)
                .findFirst();
        step("Check response", () ->
                assertAll("Check response",
                        () -> assertTrue(colorName.isPresent()),
                        () -> assertEquals(color, colorName.get())));
    }
}
