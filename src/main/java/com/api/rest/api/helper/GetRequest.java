package com.api.rest.api.helper;

import java.io.IOException;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * 
 * @author Rukshesh
 * Step 1 : Create the HTTP GET method
 * Step 2 : Create the HTTP client
 * Step 3 : Execute the HTTP method (GET) using the client
 * Step 4 : Catch the response of execution
 * Step 5 : Display the response at the console
 *
 */
public class GetRequest {

	public static void main(String[] args) {
		HttpGet get = new HttpGet("http://localhost:8080/laptop-bag/webapi/api/ping/hello");
		/**
		 * Code Optimization using Java 8 try catch (https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
		 */
		try(CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(get)){         
		//CloseableHttpClient client = HttpClientBuilder.create().build();
		//CloseableHttpResponse response = client.execute(get);
		StatusLine status = response.getStatusLine();
		int statusCode = status.getStatusCode();
		System.out.println(statusCode);
		ProtocolVersion protocol = status.getProtocolVersion();
		System.out.println(protocol.toString());
		ResponseHandler<String> body = new BasicResponseHandler();
		String responseBody = body.handleResponse(response);
		System.out.println(responseBody);
		//client.close();
		//response.close();
	}catch(ClientProtocolException cpe){
		cpe.printStackTrace();
	}catch(IOException io){
		io.printStackTrace();
	}
		
}
}