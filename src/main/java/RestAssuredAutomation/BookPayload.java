package RestAssuredAutomation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BookPayload {
	public static String Addbook(String name, String isbn, String aisle, String author) {
		// public static String Addbook(String isbn, String aisle) {

		String payload = "{\n" + "\"name\":\"" + name + "\",\n" + "\"isbn\":\"" + isbn + "\",\n" + "\"aisle\":\""
				+ aisle + "\",\n" + "\"author\":\"" + author + "\"\n" + "}";

		/*
		 * String payload = "{\n" + "\"name\":\"Learn Appium Automation with Java\",\n"
		 * + "\"isbn\":\"" + isbn + "\",\n" + "\"aisle\":\"" + aisle + "\",\n" +
		 * "\"author\":\"John foe\"\n" + "}";
		 */
		return payload;
	}

	public static JsonPath rawToJson(Response resp) {
		JsonPath js = new JsonPath(resp.toString());
		return js;
	}

	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
