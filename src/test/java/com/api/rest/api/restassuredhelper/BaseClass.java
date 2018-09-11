package com.api.rest.api.restassuredhelper;

import org.junit.BeforeClass;
import static io.restassured.RestAssured.*;

public class BaseClass {

	@BeforeClass
	public static void setUp(){
		baseURI = "http://localhost";
		basePath = "/laptop-bag/webapi/api";
		port = 8081;
	}
}
