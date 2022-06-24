package RestAssuredAutomation;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JiraOperationsPoc {
	PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();

	@BeforeTest
	public void createSession() {
		RestAssured.baseURI = "https://ronak-gavandi.atlassian.net";
		authScheme.setUserName("gavandi.ronak@gmail.com");
		authScheme.setPassword("Jo1VNLsBTQXedkqR5yk3825C");
		RestAssured.authentication = authScheme;
	}
	
	@Test
	public void createBug() {
		Response response = given().log().all().header("Content-Type", "application/json")
				.body(GetJsonPayload.newBugJson()).when().post("/rest/api/2/issue/").then().log().all().extract()
				.response();
		Assert.assertEquals(response.getStatusCode(), 201);
	}

	@Test
	public void getBug() {
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-13").when()
				.get("/rest/api/3/issue/{issueIdOrKey}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void getBugSpecificDetails() {
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-15")
				.queryParam("fields", "summary,description,comment,attachment").when()
				.get("/rest/api/3/issue/{issueIdOrKey}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void editBug() {
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-14")
				.header("Content-Type", "application/json").body(GetJsonPayload.editBugJson()).when()
				.put("/rest/api/3/issue/{issueIdOrKey}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 204);
	}

	@Test
	public void deleteBug() {
		Response response = given().log().all().pathParams("issueIdOrKey", "PLUT-2").when()
				.delete("/rest/api/3/issue/{issueIdOrKey}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 204);
	}

	@Test
	public void addComment() {
		String comment = GetJsonPayload.newCommentJson().replace("replaceThisComment",
				"New comment added using REST Assured automation.");
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-15")
				.header("Content-Type", "application/json").body(comment).when()
				.post("/rest/api/3/issue/{issueIdOrKey}/comment").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 201);
	}

	@Test
	public void getComment() {
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-15").pathParams("id", "10003").when()
				.get("/rest/api/3/issue/{issueIdOrKey}/comment/{id}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void editComment() {
		String comment = GetJsonPayload.editCommentJson().replace("replaceThisComment",
				"Updated: Comment updated using REST Assured automation.");
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-15").pathParams("id", "10004")
				.header("Content-Type", "application/json").body(comment).when()
				.put("/rest/api/3/issue/{issueIdOrKey}/comment/{id}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void deleteComment() {
		Response response = given().log().all().pathParams("issueIdOrKey", "NEP-15").pathParams("id", "10005").when()
				.delete("/rest/api/3/issue/{issueIdOrKey}/comment/{id}").then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 204);
	}

	@Test
	public void validateComment() {
		String expectedComment = "This comment was added using REST Assured automation.";
		String response = given().log().all().pathParams("issueIdOrKey", "NEP-15").queryParam("fields", "comment")
				.when().get("/rest/api/3/issue/{issueIdOrKey}").then().log().all().extract().response().asString();
		int commentsSize = (Integer) ReusableMethods.parseJsonForValue(response, "fields.comment.comments.size()");

		for (int i = 0; i < commentsSize; i++) {
			String commentId = ReusableMethods.parseJsonForValue(response, "fields.comment.comments[" + i + "].id")
					.toString();
			if (commentId.equals("10003")) {
				String actualComment = ReusableMethods.parseJsonForValue(response,
						"fields.comment.comments[" + i + "].body.content[0].content[0].text").toString();
				// System.out.println(actualComment);
				Assert.assertEquals(actualComment, expectedComment);
				break;
			}
		}
	}

	@Test
	public void addAttachment() {
		Response response = given().log().all().pathParams("jiraNum", "NEP-18")
				.header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data")
				.multiPart("file", new File("Jira.txt")).when().post("/rest/api/3/issue/{jiraNum}/attachments")
				.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void deleteAttachment() {
		Response response = given().log().all().pathParams("id", "10002").when().delete("/rest/api/3/attachment/{id}")
				.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 204);
	}
}
