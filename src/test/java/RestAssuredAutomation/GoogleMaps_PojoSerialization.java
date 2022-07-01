package RestAssuredAutomation;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
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
		
		RequestSpecification reqSpec = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.setBody(p)
				.build();
		
		RequestSpecification spec = RestAssured.given()
				.spec(reqSpec)
				.log().all();
		
		ResponseSpecification resSpec = new ResponseSpecBuilder()
				.expectHeader("server", "Apache/2.4.41 (Ubuntu)")
				.expectContentType(ContentType.JSON)
				.expectStatusCode(200)
				.expectBody("scope", equalTo("APP"))
				.expectBody("status", equalTo("OK"))
				.build();
		
		String response = spec
				.when().post("maps/api/place/add/json")
				.then().spec(resSpec)
				.extract()
				.response().asString();
		
		//System.out.println("Response: " + response);
		JsonPath js = new JsonPath(response); // for parsing Json
		String placeId = js.getString("place_id");
		System.out.println("Place Id: " + placeId);
	}
}
