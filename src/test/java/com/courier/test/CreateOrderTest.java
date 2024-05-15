package com.courier.test;

import com.courier.teststeps.RequestEndpoints;
import com.courier.teststeps.CommonSteps;
import json.NewOrder;
import util.Colors;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.Description;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static util.URLs.BASE_URI;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    CommonSteps commonSteps = new CommonSteps();
    RequestEndpoints requestEndpoints = new RequestEndpoints();
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public CreateOrderTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    //набор тестовых данных для тестирования api добавления нового заказа
    //проверяется возможность сделать заказ с одним цветом, двумя цветами, цвет не указан
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"Алиса", "Селезнева", "Аллея Алисы Селезнёвой, 10", 5, "123456789", 5, "2024-10-10", "comment comment comment", Arrays.asList(Colors.BLACK)},
                {"Змей", "Долгожеватель", "Овражная 30", 4, "123456789", 3, "2024-10-3", "comment comment comment", new ArrayList<>()},
                {"Синдбад", "Мореход", "Экваторная 43", 6, "5432186655", 7, "2024-10-4", "comment comment comment", Arrays.asList(Colors.GREY, Colors.BLACK)},
        });
    }

    @Test
    @Description("Create a new order")
    public void createNewOrder(){
        NewOrder newOrder = new NewOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = requestEndpoints.createNewOrder(newOrder);
        commonSteps.checkResponseCode(response, 201);
        commonSteps.checkResponseData(response, "track");
    }
}
