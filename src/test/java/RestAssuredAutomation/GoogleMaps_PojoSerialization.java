package RestAssuredAutomation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.*;
import org.testng.annotations.Test;
import googleMapsPojo.Location;
import googleMapsPojo.PostJson;

public class GoogleMaps_PojoSerialization {
	@Test
	public void crudOperations() {
		PostJson p = new PostJson();
		Location l = new Location();
		List<String> typesLs = new ArrayList<String>();
		typesLs.add("Pets");
		typesLs.add("Pet-Shop");
		typesLs.add("Pet-Sales");
		typesLs.add("Pet-Grooming");
		
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		p.setAccuracy(50);
		p.setAddress("14 Lilla Street, Lillanagar, Lilland");
		p.setLanguage("ENGLISH-IN");
		p.setName("Lillatronics");
		p.setPhone_number("+1 (408) 920 3168");
		p.setTypes(typesLs);
		p.setWebsite("https://lillabismillah.com");
		
		// Add Place Rahul Shetty's method
//		RestAssured.baseURI = "https://rahulshettyacademy.com";
//		String response = given().log().all()
//				.queryParam("key", "qaclick123")
//				.header("Content-Type", "application/json")
//				.body(p)
//				.when().post("maps/api/place/add/json")
//				.then().log().all().assertThat()
//				.statusCode(200).body("scope", equalTo("APP"))
//				.body("status", equalTo("OK"))
//				.header("server", "Apache/2.4.41 (Ubuntu)").extract()
//				.response().asString();
		
		// Add Place Amod Mahajan's method
		String response = RestAssured.given()
				.baseUri("https://rahulshettyacademy.com")
				.log().all()
				.queryParam("key", "qaclick123")
				.contentType(ContentType.JSON)
				.body(p)
				.when().post("maps/api/place/add/json")
				.then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP"))
				.body("status", equalTo("OK"))
				.contentType(ContentType.JSON)
				.header("server", "Apache/2.4.41 (Ubuntu)").extract()
				.response().asString();
		
		//System.out.println("Response: " + response);
		JsonPath js = new JsonPath(response); // for parsing Json
		String placeId = js.getString("place_id");
		System.out.println("Place Id: " + placeId);
	}
}
