package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.Test;

public class TestGetUserDataDeserialization {

	private final String baseUrl = "http://petstore.swagger.io/v2";
	private final String urlToUser = "/user/{username}";

	@Test(enabled = false)
	public void testDeserialization() {
		User userData = new User();
		userData.setUsername("IgorSanzh");
		User userResponse = given().pathParam("username", "IgorSanzh").when()
				.get(baseUrl + urlToUser).then().statusCode(200).extract()
				.as(User.class);

		int printId = userResponse.getId();
		System.out.println(printId);
		String printUsername = userResponse.getUsername();
		System.out.println(printUsername);

		assertThat(userResponse.getUsername(), equalTo(userData.getUsername()));

	}

}
