package com.api.rest.api.oauth;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.path.json.JsonPath;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestAccessToken {

	/**
	 * Two approaches for generating Access Tokens
	 * 1. From UI
	 * 2. From Code
	 */
	private static String OAUTH_TOKEN = "";
	
	@BeforeClass
	public static void setup(){
		baseURI = "http://coop.apps.knpuniversity.com";
		
		JsonPath token = given()
				.formParam("client_id", "TestOAuthR")
				.formParam("client_secret", "8d551b989e117175634b96241027ba02")
				.formParam("grant_type", "client_credentials")
		.when()
		.post("/token")
		.andReturn()
		.jsonPath();
		
		OAUTH_TOKEN = token.getString("access_token");
	}
	
	@Test
	public void testPostWithoutAccessToken(){
		String s = when()
		.post("/api/2467/eggs-collect")
		.thenReturn()
		.asString();
		
		System.out.println(s);
	}
	
	@Test
	public void testPostWithAccessTokenEggsCollect(){
		 
		given()
		.auth()
		.oauth2(OAUTH_TOKEN)
		.when()
		.post("/api/2467/eggs-collect")
		.then()
		.assertThat()
		.body("action", equalToIgnoringCase("eggs-collect"));
		
	}
	
	@Test
	public void testPostWithAccessTokenChickensFeed(){
		String s = 
				given()
				.auth()
				.oauth2(OAUTH_TOKEN)
				.when()
		.post("http://coop.apps.knpuniversity.com/api/2467/chickens-feed")
		.thenReturn()
		.asString();
		
		System.out.println(s);
	}
	
	@Test
	public void testPostWithAccessTokenEggsCount(){
		String s = 
				given()
				.auth()
				.oauth2(OAUTH_TOKEN)
				.when()
		.post("http://coop.apps.knpuniversity.com/api/2467/eggs-count")
		.thenReturn()
		.asString();
		
		System.out.println(s);
	}
	
	
	
	
	
}
