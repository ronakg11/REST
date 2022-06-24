package RestAssuredAutomation;

import static io.restassured.RestAssured.given;
import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import oAuthPojo.RSAJson;
import oAuthPojo.WebAutomation;

public class OAuth2_PojoDeserialization {
	public static void main(String[] args) throws InterruptedException {
		// URL to login using Google authentication (note that Google doesn't allow automation to login anymore)
		// https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		// Enter the changed URL below (after logging in)
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWiNwkxtRsI3AT5WOVff4WkOCn3-YBLKpNKyJ4a0Cni1WOrE2dQbf_tsD8sBRodr4Q&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		// System.out.println(code);

		String response = given().urlEncodingEnabled(false)
						.queryParams("code", code)
						.queryParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
						.queryParams("grant_type", "authorization_code")
						.queryParams("state", "verifyfjdss")
						.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

						// .queryParam("scope",
						// "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
						.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
						// System.out.println(response);
		
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");

//		String jsonString = given().contentType("application/json").
//				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
//				.when().get("https://rahulshettyacademy.com/getCourse.php")
//				.then()
//				.log().all()
//				.extract().response().asString();
		
		RSAJson responsejson = given()
				.queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").as(RSAJson.class);
		
		List<WebAutomation> webAutomationCoursesList = responsejson.getCourses().getWebAutomation();
		int myCoursePrice = 0;
		for(int i = 0; i < webAutomationCoursesList.size(); i++) {
			String webCourse = webAutomationCoursesList.get(i).getCourseTitle();
			
			if(webCourse.equalsIgnoreCase("Protractor")) {
				myCoursePrice = Integer.parseInt(webAutomationCoursesList.get(i).getPrice());
			}
		}
		System.out.println("My course's price is: " + myCoursePrice + " and my instructor for the course is: " + responsejson.getInstructor());
	}
}
