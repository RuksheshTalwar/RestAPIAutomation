package com.api.rest.api.ssl;
import static io.restassured.RestAssured.*;

import org.junit.Test;

import io.restassured.http.ContentType;

public class TestGetWithSSL {

	@Test
	public void testGet(){
		String response = given()
				.relaxedHTTPSValidation()
		.accept(ContentType.JSON)
		.when()
		.get("http://localhost:8081/laptop-bag/webapi/sslres/all")
		.thenReturn()
		.asString();
		
		System.out.println(response);
	}
}
