package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;



public class GetAPITest extends TestBase {

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
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);

		// a. Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code :" + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// b. JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API --->" + responseJson);
		
		//Single Value assertion
		//per_page:
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of Per page is --> "+perPageValue);
		
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		
		//total:
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is --> "+totalValue);
		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get the value from JSON ARRAY:
		
		String lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastname);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstname);
		
		// c. All headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

		System.out.println("Headers array -->" + allHeaders);
	}


	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json"); // if the request is JSON then this line is compulsory
//		headermap.put("username", "test@amazon.com");
//		headermap.put("password", "test123");
//		headermap.put("Auth Token", "12345");
		
		
		closeableHttpResponse = restClient.get(url, headermap);

		// a. Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code :" + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// b. JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API --->" + responseJson);
		
		//Single Value assertion
		//per_page:
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of Per page is --> "+perPageValue);
		
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		
		//total:
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is --> "+totalValue);
		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get the value from JSON ARRAY:
		
		String lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastname);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstname);
		
		// c. All headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

		System.out.println("Headers array -->" + allHeaders);
	}

}
