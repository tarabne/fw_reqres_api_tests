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

    public static ResponseSpecification registrationResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();


    public static ResponseSpecification notFoundResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(BODY)
            .log(STATUS)
            .build();

    public static ResponseSpecification successDeleteResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(HEADERS)
            .log(BODY)
            .log(STATUS)
            .build();
}
