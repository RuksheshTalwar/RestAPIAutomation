package com.api.rest.api.auth;
import static io.restassured.RestAssured.*;

import org.junit.BeforeClass;

public class BaseSecureClass {

	@BeforeClass
	public static void setup(){
		baseURI = "http://localhost";
		port = 8081;
		basePath = "laptop-bag/webapi/secure";
		authentication = preemptive().basic("admin", "welcome");
	}
}
