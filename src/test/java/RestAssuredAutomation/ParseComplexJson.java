package RestAssuredAutomation;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;

public class ParseComplexJson {
	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(GetJsonPayload.coursePrice());
		int count = js.getInt("courses.size()");

		int totalCoursePriceReceived = js.getInt("dashboard.purchaseAmount");
		System.out.println("Dashboard -> Purchase Amount: " + totalCoursePriceReceived + "\n");

		int totalPriceCalculated = 0;
		for (int i = 0; i < count; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			int amount = price * copies;
			System.out.println("Course: " + js.getString("courses[" + i + "].title"));
			System.out.println("Price: " + js.getString("courses[" + i + "].price"));
			System.out.println("Copies: " + js.getString("courses[" + i + "].copies"));
			System.out.println("Total Price: " + amount + "\n");
			totalPriceCalculated = totalPriceCalculated + amount;
		}
		Assert.assertEquals(totalPriceCalculated, totalCoursePriceReceived);
	}
}