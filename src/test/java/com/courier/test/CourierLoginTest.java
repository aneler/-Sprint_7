package com.courier.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.CourierLoginCreds;
import org.junit.Before;
import org.junit.Test;

import com.courier.teststeps.*;
import static util.Messages.*;
import static util.URLs.*;

import io.qameta.allure.Description;
import com.github.javafaker.Faker;


public class CourierLoginTest {
    static Faker faker = new Faker();
    static String fakeLogin = faker.internet().emailAddress();
    static String fakePass = faker.internet().password();
    static String emptyString = "";
    RequestEndpoints requestEndpoints = new RequestEndpoints();
    CommonSteps commonSteps = new CommonSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @Description("Check login with missing login")
    public void checkLoginsMissingLogin(){
        CourierLoginCreds missingLogin = new CourierLoginCreds(emptyString, fakePass);
        Response response = requestEndpoints.sendLoginRequest(missingLogin);
        commonSteps.checkResponseCode(response, 400);
        commonSteps.checkResponseMessage(response, LOGIN_ERROR_MSG);
    }

    @Test
    @Description("Check login with missing password")
    public void checkLoginsMissingPassword(){
        CourierLoginCreds missingPassword = new CourierLoginCreds(fakeLogin, emptyString);
        Response response = requestEndpoints.sendLoginRequest(missingPassword);
        commonSteps.checkResponseCode(response, 400);
        commonSteps.checkResponseMessage(response, LOGIN_ERROR_MSG);
    }

    @Test
    @Description("Check login with non-existing user")
    public void checkLoginWithNonExistingUser(){
        CourierLoginCreds userNotExist = new CourierLoginCreds(fakeLogin, fakePass);
        Response response = requestEndpoints.sendLoginRequest(userNotExist);
        commonSteps.checkResponseCode(response, 404);
        commonSteps.checkResponseMessage(response, ACCOUNT_NOT_FOUND);
    }
}
