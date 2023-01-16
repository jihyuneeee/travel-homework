package com.homework.travel;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

@SpringBootTest
class TravelApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void joinUser() {

		HashMap<String, Object> requestData = new HashMap<>();
		requestData.put("username", "testuser4");

		RestAssured.given().port(8080).contentType(
				ContentType.JSON).body(requestData).log().all()
				.when().post("/api/user")
				.then().log().all().statusCode(200).assertThat().body("id", notNullValue());
	}

}
