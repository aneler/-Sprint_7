package com.courier.test;

import com.courier.teststeps.CommonSteps;
import com.courier.teststeps.LoginSteps;
import com.courier.teststeps.RequestEndpoints;
import json.Courier;
import static util.Messages.*;
import static util.URLs.*;
import json.CourierLoginCreds;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.Description;
import com.github.javafaker.Faker;

public class CourierSignUpTest {
    static Faker faker = new Faker();
    static String fakeLogin = faker.internet().emailAddress();
    static String anotherFakeLogin = faker.internet().emailAddress();
    static String fakePass = faker.internet().password();
    static String fakeFirstName = faker.name().firstName();
    static String anotherFakeFirstName = faker.name().firstName();
    static String emptyString = "";
    RequestEndpoints requestEndpoints = new RequestEndpoints();
    CommonSteps commonSteps = new CommonSteps();
    LoginSteps loginSteps = new LoginSteps();
    static Courier courier = new Courier(fakeLogin, fakePass, fakeFirstName);
    public static int UserId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    //этот тест проверяет регистрацию и авторизацию курьера, к сожалению не придумала
    //как организовать тестовые данные так, вынести проверку авторизации в отдельный класс
    //с другими тестами авторизации
    @Test
    @Description("Check signup and login with correct data, and delete courier after")
    public void successfulSignUp() {
        Response responseSignUp = requestEndpoints.sendSignUpRequest(courier);
        commonSteps.checkResponseCode(responseSignUp, 201);
        commonSteps.checkResponseBody(responseSignUp, "ok", true);

        CourierLoginCreds loginCreds = new CourierLoginCreds(fakeLogin, fakePass);
        Response responseLogin = requestEndpoints.sendLoginRequest(loginCreds);
        commonSteps.checkResponseCode(responseLogin, 200);
        commonSteps.checkResponseData(responseLogin, "id");
        UserId = loginSteps.extractUserId(responseLogin);

        Response deleteCourier = requestEndpoints.deleteCourierRequest(UserId);
        commonSteps.checkResponseCode(deleteCourier, 200);
        commonSteps.checkResponseBody(deleteCourier, "ok", true);
    }

    @Test
    @Description("Check signup with duplicate courier name")
    public void checkDuplicateCourier(){
        Response responseFirstSignUp = requestEndpoints.sendSignUpRequest(courier);
        commonSteps.checkResponseCode(responseFirstSignUp, 201);

        Response responseSecondSignUp = requestEndpoints.sendSignUpRequest(courier);
        commonSteps.checkResponseCode(responseSecondSignUp, 409);
        commonSteps.checkResponseMessage(responseSecondSignUp, DUPLICATE_LOGIN_MSG);
    }
    @Test
    @Description("Test sign up with missing login")
    public void checkIfLoginMissing(){
        Courier missingLigin = new Courier(emptyString, fakePass, fakeFirstName);
        Response response = requestEndpoints.sendSignUpRequest(missingLigin);
        commonSteps.checkResponseCode(response, 400);
        commonSteps.checkResponseMessage(response, MISSING_FIELD_MSG);
    }
    @Test
    @Description("Test sign up with missing password")
    public void checkIfPasswordMissing(){
        Courier missingPassword = new Courier(anotherFakeLogin, emptyString, anotherFakeFirstName);
        Response response = requestEndpoints.sendSignUpRequest(missingPassword);
        commonSteps.checkResponseCode(response, 400);
        commonSteps.checkResponseMessage(response, MISSING_FIELD_MSG);
    }
    //в описании задачи написано "если одного из полей нет, запрос возвращает ошибку;"
    //этот тест падает, так как, при отсутствии имени курьера возвращается статус код 201
    //по всей видимости это поле таки не явлдяется обязательным
    @Test
    @Description("Test sign up with missing name")
    public void checkIfNameMissing(){
        Courier missingName = new Courier(anotherFakeLogin, fakePass, emptyString);
        Response response = requestEndpoints.sendSignUpRequest(missingName);
        commonSteps.checkResponseCode(response, 400);
        commonSteps.checkResponseMessage(response, MISSING_FIELD_MSG);
    }
}
