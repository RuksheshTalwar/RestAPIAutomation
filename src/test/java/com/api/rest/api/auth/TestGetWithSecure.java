package com.api.rest.api.auth;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.api.rest.api.helper.Features;
import com.api.rest.api.helper.LaptopBag;

public class TestGetWithSecure extends BaseSecureClass{
	
	@Test
	public void testGetWithoutAuth(){
		expect()
		.log()
		.all()
		.statusCode(HttpStatus.SC_OK)
		.when()
		.get("http://localhost:8081/laptop-bag/webapi/secure/all");
	}
	
	@Test
	public void testGetWithAuth(){
		
		
		/**
		 * Two ways of authentication/authorization
		 * 1. Using Header
		 * 2. Using RestAssured Methods
		 * 
		 */
		given()
		.log()
		.all()
		.header("Authorization", "Basic YWRtaW46d2VsY29tZQ==")
		.when()
		.get("http://localhost:8081/laptop-bag/webapi/secure/all")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testBasicAuth(){
		
		/**
		 * 1. Preemptive Basic Authentication : It will always send the username/password along with request
		 * 
		 * 2. Challenged Basic Authentication : It will send the username/password when server explicitly ask for it i.e. the server 
		 * challenges the request with authentication to be passed
		 * 
		 */
		given()
		//.auth()
		//.preemptive()
		//.basic("admin", "welcome")
		.log()
		.all()
		.when()
		.get("http://localhost:8081/laptop-bag/webapi/secure/all")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
		
	}
	
	@Test
	public void testPostWithObjectMapping(){
		String id = (int)(1000*(Math.random())) + "";
		
		LaptopBag bag = new LaptopBag();
		bag.setBrandName("Microsoft");
		bag.setLaptopName("Surface Pro");
		bag.setId(id);
		Features feature = new Features();
		feature.setFeature(new ArrayList<String>(Arrays.asList("8GB RAM", "1 TB Hard Drive")));
		bag.setFeatures(feature);
		
		given()
		.log()
		.body()
		.accept(ContentType.JSON)
		.with()
		//.auth()
		//.preemptive()
		//.basic("admin", "welcome")
		.contentType(ContentType.JSON)
		.body(bag)
		.post("http://localhost:8081/laptop-bag/webapi/secure/add")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK)		
		.body("Id", equalTo(Integer.parseInt(id)));
	}
	
}
