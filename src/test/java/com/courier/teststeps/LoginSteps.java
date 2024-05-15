package com.courier.teststeps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;

public class LoginSteps {
    @Step("Extract user id")
    public int extractUserId(Response response){
        int userId = response.then().extract().path("id");
        return userId;
    }
}
