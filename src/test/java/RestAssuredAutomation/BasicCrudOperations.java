package RestAssuredAutomation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicCrudOperations {
	@Test
	public void crudOperations() {
		// Add Place
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = RestAssured.given()
				.baseUri("https://rahulshettyacademy.com")
				.log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(GetJsonPayload.addPlace())
				.when().post("maps/api/place/add/json")
				.then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP"))
				.body("status", equalTo("OK"))
				.header("server", "Apache/2.4.41 (Ubuntu)").extract()
				.response().asString();
		//System.out.println("Response: " + response);
		JsonPath js = new JsonPath(response); // for parsing Json
		String placeId = js.getString("place_id");
		System.out.println("Place Id: " + placeId);

		// Update Place
		String newAddress = "Summer Walk, Africa";
		String putRequestBody = GetJsonPayload.updatePlace().replace("REPLACE_THIS_PLACE_ID", placeId)
				.replace("REPLACE_THIS_ADDRESS", newAddress);
		
		RestAssured.given()
		.baseUri("https://rahulshettyacademy.com")
		.log().all()
		.queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
				.body(putRequestBody)
				.when().put("maps/api/place/update/json")
				.then().assertThat().log().all()
				.statusCode(200).body("msg", equalTo("Address successfully updated"));

		// Get Place
		String getPlaceResponse = RestAssured.given()
				.baseUri("https://rahulshettyacademy.com").log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();

		String actualAddress = (String) ReusableMethods.parseJsonForValue(getPlaceResponse, "address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	}
}
