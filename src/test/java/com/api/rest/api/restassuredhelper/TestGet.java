package com.api.rest.api.restassuredhelper;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class TestGet extends BaseClass{

	
	@Test
	public void testGet() throws URISyntaxException{
		
		/**
		 * Given Accept the response in JSON format
		 * When I perform the GET request
		 * Then Status code 200 OK should be returned
		 */
		/*int code = given().accept(ContentType.JSON).when().get(new URI("http://localhost:8080/laptop-bag/webapi/api/ping/hello")).thenReturn().statusCode();
		System.out.println(code);
		Assert.assertEquals(HttpStatus.SC_OK, code);*/
		
		given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("/all"))
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
		
		
		Response res = given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("/all"));
		
		System.out.println(res.asString());
		
		/*URI uri = new URI("http://localhost:8080/laptop-bag/webapi/api/ping/hello");*/ //Throwing checked exception 'URISyntaxException'
		/*Response response = when().get(new URI("http://localhost:8080/laptop-bag/webapi/api/ping/hello"));*/
		/*System.out.println(response.asString());*/
		
		
		//thenReturn() : capture the status code / any other information in the response or he headers
		//then() : Validation of response
	}
	
	@Test
	public void testStatusCode() throws URISyntaxException{
		
		/**
		 * Given Accept the response in JSON format
		 * When I perform the GET request
		 * Then Status code 200 OK should be returned
		 */
		
		//Using thenReturn()
		int statusCode = given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("/all"))
		.thenReturn()
		.statusCode();
		
		Assert.assertEquals(HttpStatus.SC_OK, statusCode);
		
		//Using then()
		given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("/all"))
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
		
	}
	
	@Test
	public void testGetWithId() throws URISyntaxException{
		/**
		 * Given Accept the response in JSON format
		 * When I perform the GET request with Id 203
		 * Then Status code 200 OK should be returned
		 */
		given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("/find/132"))
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testGetWithNonExistId() throws URISyntaxException{
		/**
		 * Given Accept the response in JSON format
		 * When I perform the GET request with Id 203
		 * Then Status code 200 OK should be returned
		 */
		given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("/find/126"))
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	//Custom Headers along with the request
	@Test
	public void testGetWithIdWithHeader() throws URISyntaxException{
		/**
		 * Given Accept the response in JSON format
		 * When I perform the GET request with Id 126
		 * Then Status code 200 OK should be returned
		 */
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		
		String body = given()
		.headers(header)
		.when()
		.get(new URI("/find/131"))
		.thenReturn()
		.body()
		.asString();
		
		System.out.println(body);
	}
	
	@Test
	public void testJsonContent(){
		/***
		 * Given Accept the response in JSON format
		 * When I perform the GET request with ID 126
		 * Then the response should have brand name Dell
		 */
		given()
		.accept(ContentType.JSON)
		.when()
		.get("/find/133")
		.then()
		.body("BrandName", equalToIgnoringCase("HP"), "LaptopName", equalToIgnoringCase("Convertible"), "Id", equalTo(133));
		
		//Parsing the JSON response object and validating the nested JSON object
		given()
		.accept(ContentType.JSON)
		.when()
		.get("/find/133")
		.then()
		.body("Features.Feature", hasItems("64 GB RAM", "1024 GB ROM"));
		
		given()
		.accept(ContentType.JSON)
		.when()
		.get("/find/133")
		.then()
		.body("Features.Feature", hasSize(5));
		
		/****
		 * Methods for Validating the list/nested objects in the response 
		 * hasItem()
		 * hasItems()
		 * hasSize
		 */
	}
	
	@Test
	public void testXMLContent(){
		/***
		 * Given Accept the response in XML format
		 * When I perform the GET request with ID as 133
		 * Then the response should have brand name Dell
		 * And the Features should have 8 GB RAM
		 */
		String xmlData = given()
		.accept(ContentType.XML)
		.when()
		.get("/find/133")
		.thenReturn()
		.asString();
		System.out.println(xmlData);
		
		given()
		.accept(ContentType.XML)
		.when()
		.get("/find/133")
		.then()
		.assertThat()
		.body("Laptop.BrandName", equalTo("HP"), "Laptop.Id", equalTo("133"), "Laptop.LaptopName", equalTo("Convertible"))
		.and()
		.assertThat()
		.body("Laptop.Features.Feature", hasItem("64 GB RAM"));
	}
	
	
	@Test
	public void testXMLContentXmlPath(){
		/***
		 * Given Accept the response in XML format
		 * When I perform the GET request with ID as 126
		 * Then the response should have brand name Dell
		 * And the Features should have 8 GB RAM
		 */
		/**
		 * XmlPath class  to validate response body
		 * It has methods like getId, getString, getList to validate the XML response components
		 */
		
		String responseBody = given()
		.accept(ContentType.XML)
		.when()
		.get("/find/133")
		.thenReturn()
		.asString();
		
		XmlPath xmlPath = new XmlPath(responseBody);
		
		Assert.assertEquals("HP", xmlPath.getString("Laptop.BrandName"));
		Assert.assertEquals(133, xmlPath.getInt("Laptop.Id"));
		Assert.assertEquals("Convertible", xmlPath.getString("Laptop.LaptopName"));
		Assert.assertTrue(xmlPath.getList("Laptop.Features.Feature").contains("64 GB RAM"));
	}
	
	@Test
	public void testJSONContentJsonPath(){
		/***
		 * Given Accept the response in JSON format
		 * When I perform the GET request with ID as 133
		 * Then the response should have brand name Dell
		 * And the Features should have 8 GB RAM
		 */
		//JsonPath class 
		
		String responseBody = given()
		.accept(ContentType.JSON)
		.when()
		.get("/find/133")
		.thenReturn()
		.asString();
		
		JsonPath jsonPath = new JsonPath(responseBody);
		
		Assert.assertEquals("HP", jsonPath.getString("BrandName"));
		Assert.assertEquals(133, jsonPath.getInt("Id"));
		Assert.assertEquals("Convertible", jsonPath.getString("LaptopName"));
		Assert.assertTrue(jsonPath.getList("Features.Feature").contains("64 GB RAM"));
	}
	
	
	
}
