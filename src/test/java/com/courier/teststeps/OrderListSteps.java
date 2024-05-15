package com.courier.teststeps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.Orders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class OrderListSteps {
    @Step("Check response body is not empty")
    public  void checkResponseNotEmpty(Response response){
        Orders orders = response.as(Orders.class);
        assertNotNull(orders);
        assertFalse(orders.getOrders().isEmpty());
    }
    //проверяется что список заказов не пустой и количество заказов которое отдается по умолчанию
    @Step("Check array of orders default size in response body")
    public void checkArrayDefaultSize(Response response, int maxSize) {
        Orders orders = response.as(Orders.class);
        assertNotNull(orders);
        assertThat(orders.getOrders().size(), lessThanOrEqualTo(maxSize));
    }
}
