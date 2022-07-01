package RestAssuredAutomation;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import oAuthPojo.RSAJson;
import oAuthPojo.WebAutomation;

public class OAuth2_PojoDeserialization {
	public WebDriver driver;

	@Test//(dependsOnMethods = {"initializeDriverToExistingBrowser"})
	public void OAuth() throws InterruptedException {
		// URL to login using Google authentication (note that Google doesn't allow automation to login anymore)
		// https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		// Enter the changed URL below (after logging in)
		
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("ronakg11");
//		driver.findElement(By.xpath("(//div[@class='VfPpkd-dgl2Hf-ppHlrf-sM5MNb'])[2]/button")).click();
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Swamiom_11@a");
//		driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
//		String url = driver.getCurrentUrl();
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWiHTal0xGIcSxMYkQ1yAjAgMF3tT-Ed0RNXWxfak_0dmSr7TgfPHXAPnDLiXjMCZw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		// System.out.println(code);

		String response = RestAssured.given().urlEncodingEnabled(false)
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
		
		RSAJson responsejson = RestAssured.given()
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
	
	@Test(enabled = false)
	public void runCommandToOpenChromeOnSpecificPort() throws IOException, InterruptedException {
        String[] command = new String[]{"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "--remote-debugging-port=9222", "--user-data-dir=\"/Users/ronakgavandi/Downloads/Nimma\""};
        Runtime.getRuntime().exec(command);

        // Read the output
//        BufferedReader reader =  
//              new BufferedReader(new InputStreamReader(proc.getInputStream()));
//
//        String line = "";
//        while((line = reader.readLine()) != null) {
//            System.out.print(line + "\n");
//        }
//
//        proc.waitFor(); 
	}
	
	@Test(dependsOnMethods = {"runCommandToOpenChromeOnSpecificPort"}, enabled = false)
	public void initializeDriverToExistingBrowser() throws IOException, InterruptedException {
		// Run the following command in terminal first
		// /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --remote-debugging-port=9222 --user-data-dir="/Users/ronakgavandi/Downloads/Nimma
		Thread.sleep(5000);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
		ChromeOptions o = new ChromeOptions();
		o.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		o.setAcceptInsecureCerts(true);
		int port = 9222;
		o.setExperimentalOption("debuggerAddress", "localhost:" + port);
		driver = new ChromeDriver(o);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown() {
//		driver.close();
	}
}
