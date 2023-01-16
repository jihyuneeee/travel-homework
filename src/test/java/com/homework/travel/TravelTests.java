package com.homework.travel;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TravelTests {

    @Test
    public void registerTravel() {

        HashMap<String, String> requestData = new HashMap<>();
        requestData.put("cityId", "1");
        requestData.put("userId", "1");
        requestData.put("travelname", "testtravel");
        requestData.put("startDate", "2022-12-30 00:00:00");
        requestData.put("endDate", "2023-01-19 00:00:00");

        RestAssured
                .given().port(8080).contentType(ContentType.JSON).body(requestData).log().all()
                .when().post("/api/travel")
                .then().log().all().statusCode(200).assertThat().body("id", notNullValue());
    }
}
