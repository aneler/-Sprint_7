package com.courier.teststeps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CommonSteps {
    @Step("Check response message")
    public void checkResponseMessage(Response response, String message){
        response.then().assertThat().body("message", equalTo(message));
    }

    @Step("Check response code")
    public void checkResponseCode(Response response, int expectedStatusCode) {
        response.then().assertThat().statusCode(expectedStatusCode);
    }
    @Step("Check status in response body")
    public void checkResponseBody(Response response, String key, boolean expectedBody){
        response.then().assertThat().body(key, equalTo(expectedBody));
    }
    @Step("Check response data")
    public void checkResponseData(Response response, String key){
        response.then().assertThat().body(key, notNullValue());
    }
}
