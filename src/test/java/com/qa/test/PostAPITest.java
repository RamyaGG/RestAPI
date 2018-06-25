package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.*;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class PostAPITest extends TestBase {

	TestBase testBase;
	String serviceURL;
	String url;
	String apiUrl;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		serviceURL = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		// https://reqres.in/api/users

		url = serviceURL + apiUrl;

	}

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		// jackson API -- to do POJO to JSON and JSON to POJO conversion
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader"); // expected users object

		// object to JSON file Conversion:
		mapper.writeValue(
				new File("H:\\Edureka\\Selenium\\WorkSpace\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"),
				users);

		// object to Json in String
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);

		closeableHttpResponse = restClient.post(url, usersJsonString, headerMap); // call the API

		//validate the response from API:
		// a. Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code :" + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Status Code is not 201");

		// b. JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API --->" + responseJson);
		
		//json to java object
		Users usersResObj = mapper.readValue(responseString, Users.class); //actual Users Object
		System.out.println(usersResObj);
		
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));

		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());
	}

}
