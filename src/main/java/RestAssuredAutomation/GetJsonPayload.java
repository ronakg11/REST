package RestAssuredAutomation;

public class GetJsonPayload {
	public static String addPlace() {
		return "{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
				+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Rahul Shetty Academy\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n" + "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n" + "  ],\r\n" + "  \"website\": \"http://rahulshettyacademy.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n" + "}\r\n" + "";
	}

	public static String updatePlace() {
		return "{\n" + "\"place_id\":\"REPLACE_THIS_PLACE_ID\",\n" + "\"address\":\"REPLACE_THIS_ADDRESS\",\n"
				+ "\"key\":\"qaclick123\"\n" + "}";
	}

	public static String coursePrice() {
		return "{\r\n" + "  \"dashboard\": {\r\n" + "    \"purchaseAmount\": 1162,\r\n"
				+ "    \"website\": \"rahulshettyacademy.com\"\r\n" + "  },\r\n" + "  \"courses\": [\r\n" + "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n" + "      \"price\": 50,\r\n" + "      \"copies\": 6\r\n"
				+ "    },\r\n" + "    {\r\n" + "      \"title\": \"Cypress\",\r\n" + "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n" + "    },\r\n" + "    {\r\n" + "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n" + "      \"copies\": 10\r\n" + "    },\r\n" + "     {\r\n"
				+ "      \"title\": \"Appium\",\r\n" + "      \"price\": 36,\r\n" + "      \"copies\": 7\r\n"
				+ "    }\r\n" + "    \r\n" + "    \r\n" + "    \r\n" + "  ]\r\n" + "}\r\n" + "";
	}

	public static String newBugJson() {
		return "{\n" + "    \"fields\": {\n" + "       \"project\":\n" + "       {\n" + "          \"key\": \"NEP\"\n"
				+ "       },\n" + "       \"summary\": \"New bug created using REST Assured\",\n"
				+ "       \"description\": \"Issue created using project keys and issue type names using REST Assured library.\",\n"
				+ "       \"issuetype\": {\n" + "          \"name\": \"Bug\"\n" + "       },\n"
				+ "       \"assignee\": {\n" + "            \"accountId\": \"60b6ab7448b895006963eb97\"\n"
				+ "       },\n" + "       \"priority\": {\n" + "            \"id\": \"2\"\n" + "       },\n"
				+ "       \"environment\": \"ST01\",\n"
				+ "       \"labels\": [\"Catastrophic_Failure\", \"Staging_Blocker\"]\n" + "   }\n" + "}";
	}

	public static String editBugJson() {
		return "{\n" + "  \"update\": {\n" + "    \"labels\": [\n" + "      {\n"
				+ "        \"add\": \"Triaging_Complete\"\n" + "      },\n" + "      {\n"
				+ "        \"remove\": \"Blocker_Issue\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  \"fields\": {\n"
				+ "    \"summary\": \"The summary was updated using REST Assured Test\"\n" + "  }\n" + "}";
	}

	public static String newCommentJson() {
		return "{\n" + "  \"body\": {\n" + "    \"type\": \"doc\",\n" + "    \"version\": 1,\n" + "    \"content\": [\n"
				+ "      {\n" + "        \"type\": \"paragraph\",\n" + "        \"content\": [\n" + "          {\n"
				+ "            \"text\": \"replaceThisComment\",\n" + "            \"type\": \"text\"\n"
				+ "          }\n" + "        ]\n" + "      }\n" + "    ]\n" + "  }\n" + "}";
	}

	public static String editCommentJson() {
		return "{\n" + "  \"body\": {\n" + "    \"type\": \"doc\",\n" + "    \"version\": 1,\n" + "    \"content\": [\n"
				+ "      {\n" + "        \"type\": \"paragraph\",\n" + "        \"content\": [\n" + "          {\n"
				+ "            \"text\": \"replaceThisComment\",\n" + "            \"type\": \"text\"\n"
				+ "          }\n" + "        ]\n" + "      }\n" + "    ]\n" + "  }\n" + "}";
	}
}
