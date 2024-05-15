package com.courier.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import static util.URLs.*;
import com.courier.teststeps.*;

public class OrderListTest {
    OrderListSteps orderListSteps = new OrderListSteps();
    RequestEndpoints requestEndpoints = new RequestEndpoints();
    CommonSteps commonSteps = new CommonSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @Description("Request the full order list")
    public void requestOrdersList() {
        Response response = requestEndpoints.sendGetOrdersRequest();
        commonSteps.checkResponseCode(response, 200);
        orderListSteps.checkResponseNotEmpty(response);
        orderListSteps.checkArrayDefaultSize(response, 30);
    }
}
