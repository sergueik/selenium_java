package tests;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class TestLogin {

	private final String baseUrl = "http://petstore.swagger.io/v2";
	private final String urlToUser = "/user/login";

	@Test
	public void loginUser() {
		given().param("username", "IgorSanzh").param("password", "1111qwert1234")
				.when().get(baseUrl + urlToUser).then().statusCode(200);

	}
}
