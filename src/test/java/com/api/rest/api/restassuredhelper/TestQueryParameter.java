package com.api.rest.api.restassuredhelper;

import io.restassured.http.ContentType;

import org.junit.Test;

import static io.restassured.RestAssured.*;

public class TestQueryParameter extends BaseClass{

	@Test
	public void testQueryPar(){
		/*****
		 * Given Accept the content in JSON format
		 * And ID as 126
		 * And Laptop name as Latitude
		 * When I perform the GET request
		 * Then Status code 200 OK should be returned
		 * And the response content should have ID as 126
		 * And the feature list should contain 1024 GB of SSD
		 */
		String responseBody = given()
		.accept(ContentType.JSON)
		.param("Id", "128")
		.param("LaptopName", "Pavillion")
		.when()
		.get("/query")
		.thenReturn()
		.asString();
		
		System.out.println(responseBody);
	}
}
