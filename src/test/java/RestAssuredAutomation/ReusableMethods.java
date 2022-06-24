package RestAssuredAutomation;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	public static Object parseJsonForValue(String jsonResponse, String key) {
		JsonPath jsp = new JsonPath(jsonResponse);
		return jsp.get(key);
	}
}
