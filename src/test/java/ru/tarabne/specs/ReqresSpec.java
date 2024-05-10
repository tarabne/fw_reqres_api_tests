package ru.tarabne.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;
import static ru.tarabne.helpers.CustomAllureListener.withCustomTemplates;

public class ReqresSpec {
    public static RequestSpecification postRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static RequestSpecification getRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method();

    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification loggingSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
