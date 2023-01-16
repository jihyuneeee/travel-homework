package com.homework.travel;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class CityTests {

    @Test
    public void registerCity() {

        HashMap<String, String> requestData = new HashMap<>();
        requestData.put("cityname", "Seoul");

        RestAssured
                .given().port(8080).contentType(ContentType.JSON).body(requestData).log().all()
                .when().post("/api/city")
                .then().log().all().statusCode(200).assertThat().body("id", notNullValue());
    }

    @Test
    public void updateCity() {

        HashMap<String, String> requestData = new HashMap<>();
        requestData.put("cityname", "Incheon");

        RestAssured
                .given().contentType(ContentType.JSON).body(requestData).log().all()
                .when().put("/api/city/1")
                .then().log().all().statusCode(200).assertThat()
                .body("id", notNullValue(), "cityname", is(requestData.get("cityname")));
    }

    @Test
    public void selectCity() {

        RestAssured
                .given().contentType(ContentType.JSON)
                .when().get("/api/city/2")
                .then().log().all().statusCode(200).assertThat();
    }

    @Test
    public void selectCityList() {

        RestAssured
                .given().contentType(ContentType.JSON)
                .when().get("/api/city/list/1")
                .then().log().all().statusCode(200).assertThat();
    }

}
