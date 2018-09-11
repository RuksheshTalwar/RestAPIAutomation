package com.api.twitter;

//import io.restassured.internal.http.URIBuilder;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;

import static io.restassured.RestAssured.*;

import org.junit.Test;

import com.api.rest.api.model.TwitterModel;

public class TestGetTweet {

	private final String CONSUMER_KEY = "OhbLGLZpzDBD1W3z0hdIKZVJ8";
	private final String CONSUMER_SECRET = "KXz14vAPonvGy9cr5mhe0droDPxDWTWe58VpOZ4K7zQLDFaTHQ";
	private final String ACCESS_KEY = "2246746746-dcBTH1w9O5MbhWlml3J3SC6Yw4xh3F4QUyBRy2H";
	private final String ACCESS_SECRET = "1UH2yyV7JpZwEx0xSFm2FWz3Xtip0WpZ7qWtHiRvYVr2S";
	
	/**
	 *  https://api.twitter.com/1.1/statuses/update.json?status=Maybe%20he%27ll%20finally%20find%20his%20keys.%20%23peterfalk
	 * @throws URISyntaxException 
	 */
	@Test
	public void postStatusUpdate() throws URISyntaxException{
		URI postUri = new URIBuilder()
		.setScheme("https")
		.setHost("api.twitter.com/")
		.setPath("1.1/statuses/update.json")
		.addParameter("status", "Test-Status")
		.build();
		
		
		given()
		.auth()
		.oauth(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_KEY, ACCESS_SECRET)
		.when()
		.post(postUri)
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
		
	}
	
	@Test
	public void getFollowers() throws URISyntaxException{
		URI getUri = new URIBuilder()
		.setScheme("https")
		.setHost("api.twitter.com/")
		.setPath("1.1/followers/list.json")
		.setParameter("screen_name", "Schwarzenegger")
		.setParameter("count", "2")
		.setParameter("skip_status", "true")
		.setParameter("include_user_entities", "false")
		.build();
		
		String response = given()
		.auth()
		.oauth(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_KEY, ACCESS_SECRET)
		.when()
		.get(getUri)
		.thenReturn()
		.asString();
		
		System.out.println(response);
		
		
	}
	
	@Test
	public void deleteStatus() throws URISyntaxException{
		//Post URI
		URI postUri = new URIBuilder()
		.setScheme("https")
		.setHost("api.twitter.com/")
		.setPath("1.1/statuses/update.json")
		.addParameter("status", "Test-Status" +Math.random())
		.build();
		
		TwitterModel postResponse = given()
		.auth()
		.oauth(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_KEY, ACCESS_SECRET)
		.when()
		.post(postUri)
		.thenReturn()
		.as(TwitterModel.class);
		
		//Delete URI
		URI deleteUri = new URIBuilder()
		.setScheme("https")
		.setHost("api.twitter.com/")
		.setPath("1.1/statuses/destroy/" + postResponse.id_str + ".json")
		.build();
		
		String deleteResponse = given()
		.auth()
		.oauth(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_KEY, ACCESS_SECRET)
		.when()
		.post(deleteUri)
		.thenReturn()
		.asString();
		
		System.out.println(deleteResponse);
		
	}
}
