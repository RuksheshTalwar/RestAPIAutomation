package com.api.rest.api.restassuredhelper;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.Arrays;



import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.api.rest.api.helper.Features;
import com.api.rest.api.helper.LaptopBag;

public class TestPutRequest extends BaseClass{
	
	@Test
	public void testPut(){
	
		//First POST the data in API
		String id = (int) (1000 * (Math.random())) + "";

		LaptopBag bag = new LaptopBag();
		bag.setBrandName("Microsoft");
		bag.setLaptopName("Surface Pro");
		bag.setId(id);
		Features feature = new Features();
		feature.setFeature(new ArrayList<String>(Arrays.asList("8GB RAM",
				"1 TB Hard Drive")));
		bag.setFeatures(feature);

		//POST request
		given()
				.log()
				.body()
		.accept(ContentType.JSON)
		.with()
		.contentType(ContentType.XML)
		.body(bag)
		.post("/add")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK)		
		.body("Id", equalTo(Integer.parseInt(id)));
		
		
		feature.setFeature(new ArrayList<String>(Arrays.asList("8GB RAM",
				"1 TB Hard Drive", "This is a PUT request")));
		bag.setFeatures(feature);
		
		
		given()
		.log()
		.body(true)
		.accept(ContentType.JSON)
		.with()
		.contentType(ContentType.XML)
		.and()
		.body(bag)
		.when()
		.put("/update")
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK)
		.body("Features.Feature", hasItem("This is a PUT request"));
		
		//2nd Approach of PUT request using JsonPath object
		JsonPath body = given()
				.log()
				.everything()
				.accept(ContentType.JSON)
				.with()
				.contentType(ContentType.XML)
				.and()
				.body(bag)
				.when()
				.put("/update")
				.thenReturn()
				.jsonPath();
				
				Assert.assertEquals("Microsoft", body.getString("BrandName"));
}
	
	@Test
	public void testDelete(){
		//First POST the data in API
				String id = (int) (1000 * (Math.random())) + "";

				LaptopBag bag = new LaptopBag();
				bag.setBrandName("Microsoft");
				bag.setLaptopName("Surface Pro");
				bag.setId(id);
				Features feature = new Features();
				feature.setFeature(new ArrayList<String>(Arrays.asList("8GB RAM",
						"1 TB Hard Drive")));
				bag.setFeatures(feature);

				//POST request
				given()
						.log()
						.body()
				.accept(ContentType.JSON)
				.with()
				.contentType(ContentType.XML)
				.body(bag)
				.post("/add");
				
				//Assertion after delete request
				expect()
				.log()
				.everything()
				.statusCode(HttpStatus.SC_OK)
				.when()
				.delete("/delete/" + id);
				
				expect()
				.statusCode(HttpStatus.SC_NOT_FOUND)
				.when()
				.delete("/delete/1");
	}
}
