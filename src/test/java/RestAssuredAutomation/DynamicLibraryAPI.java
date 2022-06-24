package RestAssuredAutomation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DynamicLibraryAPI {

	@Test(dataProvider = "BooksData")
	public void addBook(String name, String isbn, String aisle, String author) throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response resp = given().log().all().header("Content-Type", "application/json")
				.body(BookPayload.Addbook(name, isbn, aisle, author))
				// Use the BookPayload.GenerateStringFromResource method
				// to directly grab a json from file
				//.body(BookPayload.GenerateStringFromResource("/JsonData/Addbookdetails.json")
				.when().post("/Library/Addbook.php")
				.then().log().all()
				.assertThat().statusCode(200).extract().response();
		JsonPath js = BookPayload.rawToJson(resp);
		String id = js.get("ID");
		System.out.println(id);
		// Ideally delete Book
	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] { { "WWI: The Start of the Unrest", "lilla1", "9363", "Roger Lulli" },
				{ "WWII History", "lilla2", "4253", "Harold Shimmer" },
				{ "The D-Day Horror", "lilla3", "533", "Dick Walters" } };
	}
}
