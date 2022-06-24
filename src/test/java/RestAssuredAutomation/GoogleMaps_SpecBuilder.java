package RestAssuredAutomation;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import googleMapsPojo.DeleteJson;
import googleMapsPojo.DeleteResponseJson;
import googleMapsPojo.GetResponseFailureJson;
import googleMapsPojo.GetResponseJson;
import googleMapsPojo.Location;
import googleMapsPojo.PostJson;
import googleMapsPojo.PostResponseJson;
import googleMapsPojo.PutJson;
import googleMapsPojo.PutResponseJson;
import io.restassured.response.Response;

public class GoogleMaps_SpecBuilder {
	@Test
	public void GoogleMaps() {
		String key = "qaclick123";
		
		// Spec is common to all the CRUD requests below
		RequestSpecification spec = new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", key)
		.setContentType(ContentType.JSON).build();
		
		// Response object built common to all the requests
		ResponseSpecification respSpec = new ResponseSpecBuilder()
		.expectStatusCode(200)
		.expectContentType(ContentType.JSON).build();
		
		
		// Add Place
		PostJson p = new PostJson();
		Location l = new Location();
		List<String> typesLs = new ArrayList<String>();
		typesLs.add("Pets");
		typesLs.add("Pet-Shop");
		typesLs.add("Pet-Sales");
		typesLs.add("Pet-Grooming");
		
		l.setLat(-38.383492);
		l.setLng(33.427366);
		p.setLocation(l);
		p.setAccuracy(50);
		p.setAddress("31 Lilla Street, Lillanagar, Lilland");
		p.setLanguage("ENGLISH-IN");
		p.setName("Lillatronics");
		p.setPhone_number("+1 (408) 920 3168");
		p.setTypes(typesLs);
		p.setWebsite("https://lillabismillah.com");
		
		// Request object specific to post request due to the body being specific
		RequestSpecification postReq = RestAssured.given().spec(spec).body(p);
		
		PostResponseJson respJson = postReq.when().post("maps/api/place/add/json")
		.then().spec(respSpec).extract().response().as(PostResponseJson.class);
		
		String placeId = respJson.getPlace_id();
		System.out.println("Place Id Received: " + placeId);

		
		// Update Place
		String newAddress = "1391 Positano Parkway, Sorrento, Italy";
		PutJson put = new PutJson();
		put.setPlace_id(placeId);
		put.setAddress(newAddress);
		put.setKey(key);
		
		// Request object specific to put request due to the body being specific
		RequestSpecification putReq = RestAssured.given().log().all().spec(spec).body(put);
		
		PutResponseJson putResp = putReq
				.when().put("maps/api/place/update/json")
				.then().spec(respSpec).extract().response()
				.as(PutResponseJson.class);
		
		Assert.assertTrue(putResp.getMsg()
		.equalsIgnoreCase("Address successfully updated"));

		
		// Get Place
		RequestSpecification getSpec = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", key)
				.addQueryParam("place_id", placeId)
				.setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification getReq = RestAssured.given().spec(getSpec);
		
		// created Response object resp for validating the response time
		Response resp = getReq
				.when().get("maps/api/place/get/json")
				.then().spec(respSpec).extract()
				.response();
		
		GetResponseJson getResp = resp.as(GetResponseJson.class);

		String actualAddress = getResp.getAddress();
		System.out.println("Actual address received: " + actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		
		// Code to measure response time
		System.out.println("Response Time: " + resp.getTime());
		System.out.println("Response Time: " + resp.getTimeIn(TimeUnit.MICROSECONDS));
		System.out.println("Response Time: " + resp.time());
		System.out.println("Response Time: " + resp.timeIn(TimeUnit.MICROSECONDS));
		ValidatableResponse v = resp.then();
		v.time(Matchers.lessThan(2000L), TimeUnit.MILLISECONDS);
		// v.time(Matchers.greaterThan(2000L));
		// Asserting response time in between some values
		v.time(Matchers.both(Matchers.greaterThanOrEqualTo(500L)).and(Matchers.lessThanOrEqualTo(1100L)));
 
		
		
		// Delete Place
		DeleteJson delJson = new DeleteJson();
		delJson.setPlace_id(placeId);
		
		// Request object specific to delete request due to the body being specific
		RequestSpecification delReq = RestAssured.given().log().all().spec(spec).body(delJson);
		
		DeleteResponseJson delResp = delReq
		.when().delete("/maps/api/place/delete/json")
		.then().spec(respSpec).extract()
		.response().as(DeleteResponseJson.class);
		
		System.out.println("Delete response: " + delResp.getStatus());
		Assert.assertTrue(delResp.getStatus().equalsIgnoreCase("ok"));
		
		
		// Get Place again to check post deletion
		respSpec = new ResponseSpecBuilder()
				.expectStatusCode(404)
				.expectContentType(ContentType.JSON).build();
		
		GetResponseFailureJson getFailResp = getReq
				.when().get("maps/api/place/get/json")
				.then().spec(respSpec).extract()
				.response().as(GetResponseFailureJson.class);

		String responseMsg = getFailResp.getMsg();
		System.out.println("Actual message received: " + responseMsg);
		Assert.assertTrue(responseMsg
		.equalsIgnoreCase("Get operation failed, looks like place_id  doesn't exists"));
	}
}
