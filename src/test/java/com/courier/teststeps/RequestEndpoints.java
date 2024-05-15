package com.courier.teststeps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.Courier;
import json.CourierLoginCreds;
import json.NewOrder;

import static io.restassured.RestAssured.given;
import static util.URLs.*;

public class RequestEndpoints {
    @Step("Send POST request to /api/v1/courier/login")
    public Response sendLoginRequest(CourierLoginCreds courier){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_PATH);
        return response;
    }

    @Step("Send POST request to /api/v1/courier")
    public Response sendSignUpRequest(Courier courier){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(SIGNUP_COURIER_PATH);
        return response;
    }

    @Step("Send GET request to /api/v1/orders")
    public Response sendGetOrdersRequest(){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(ORDERS_PATH);
        return response;
    }

    @Step("Send DELETE request to /api/v1/courier/:id")
    public Response deleteCourierRequest(int Id){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(DELETE_COURIER_PATH + Id);
        return response;
    }
    @Step("Send POST request to /api/v1/orders, to create a new order")
    public Response createNewOrder(NewOrder newOrder){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .post(ORDERS_PATH);
        return response;
    }
}
