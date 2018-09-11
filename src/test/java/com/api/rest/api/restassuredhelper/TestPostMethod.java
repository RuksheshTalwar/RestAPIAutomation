package com.api.rest.api.restassuredhelper;

import java.util.ArrayList;
import java.util.Arrays;

import io.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.api.rest.api.helper.Features;
import com.api.rest.api.helper.LaptopBag;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestPostMethod extends BaseClass{
	
	@Test
	public void testPost(){
		
		/****
		 * Given Accept the content in XML format
		 * With Content Type as JSON
		 * And Body
		 * When I perform the POST request
		 * Then Status Code 200 OK should be returned
		 * And the response should contain the ID
		 * 
		 */
		
		String id = (int)(1000*(Math.random())) + "";
		
		String jsonbody = "{" + "\"BrandName\": \"HP\"," +  "\"Features\": {" + "\"Feature\": [\"8 GB RAM\",\"128 GB ROM\",\"200 GB SSD\",\"1TB HD\",\"All Disk Drives\"]},\"Id\": " + id + ",\"LaptopName\": \"Pavillion\"}";
		
		given()
		.log()
		.body(true)
		.accept(ContentType.XML)
		.with()
		.contentType(ContentType.JSON)
		.and()
		.body(jsonbody)
		.when()
		.post("/add")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK)
		.and()
		.body("Laptop.Id", equalTo(id));
	}
	
	@Test
	public void testPostWithoutBody(){
		
		/****
		 * Given Accept the content in XML format
		 * With Content Type as JSON
		 * When I perform the POST request
		 * Then Status Code 400 Bad Request should be returned
		 * 
		 */
				
		given()
		.accept(ContentType.XML)
		.with()
		.contentType(ContentType.JSON)
		.and()
		.when()
		.post("/add")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void testPostWithObjectMapping(){
		/**
		 * 1. Create the mapping class
		 * 2. Create the object of mapping class
		 * 3. Initialize the field values present in the mapping class
		 * 4. Send the object of mapping class along with post request
		 */
		String id = (int)(1000*(Math.random())) + "";
		
		LaptopBag bag = new LaptopBag();
		bag.setBrandName("Microsoft");
		bag.setLaptopName("Surface Pro");
		bag.setId(id);
		Features feature = new Features();
		feature.setFeature(new ArrayList<String>(Arrays.asList("8GB RAM", "1 TB Hard Drive")));
		bag.setFeatures(feature);
		
		/****
		 * Given Accept the content in JSON format
		 * With Content Type as XML
		 * And Body
		 * When I perform the POST request
		 * Then Status Code 200 OK should be returned
i		 * And the response should contain the ID
		 * 
		 */
		
				
		given()
		.log()
		.body()
		.accept(ContentType.JSON)
		.with()
		.contentType(ContentType.JSON)
		.body(bag)
		.post("/add")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK)		
		.body("Id", equalTo(Integer.parseInt(id)));
	}

	@Test
	public void testPostWithDeserialization(){
		/**
		 * 1. Create the mapping class
		 * 2. Create the object of mapping class
		 * 3. Initialize the field values present in the mapping class
		 * 4. Send the object of mapping class along with post request
		 */
		String id = (int)(1000*(Math.random())) + "";
		
		LaptopBag bag = new LaptopBag();
		bag.setBrandName("Microsoft");
		bag.setLaptopName("Surface Pro");
		bag.setId(id);
		Features feature = new Features();
		feature.setFeature(new ArrayList<String>(Arrays.asList("8GB RAM", "1 TB Hard Drive")));
		bag.setFeatures(feature);
		
		/****
		 * Given Accept the content in JSON format
		 * With Content Type as XML
		 * And Body
		 * When I perform the POST request
		 * Then Status Code 200 OK should be returned
i		 * And the response should contain the ID
		 * 
		 */
		LaptopBag responseBag = given()
		.log()
		.body()
		.accept(ContentType.JSON)
		.with()
		.contentType(ContentType.JSON)
		.body(bag)
		.post("/add")
		.thenReturn()
		.as(LaptopBag.class);
		
//		String responseBodyPost = given()
//				.accept(ContentType.JSON)
//				.with()
//				.contentType(ContentType.XML)
//				.body(bag)
//				.post("/add")
//				.thenReturn()
//				.asString();
//		
//		System.out.println(responseBodyPost);
		
		Assert.assertEquals("Microsoft", responseBag.getBrandName());
	}
	
}
