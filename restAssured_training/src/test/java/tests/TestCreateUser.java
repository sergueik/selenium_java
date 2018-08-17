package tests;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class TestCreateUser {

	private final String baseUrl = "http://petstore.swagger.io/v2";
	private final String urlToUser = "/User";

	User newUser = new User().setId(0).setUsername("IgorSanzh")
			.setFirstName("Igor").setLastName("Sanzh").setEmail("qwerty@qwer.qw")
			.setPassword("qwert1234").setPhone("1234567890").setUserStatus(0);

	@Test
	public void createUser() {
		given().contentType("application/json").body(newUser).when()
				.post(baseUrl + urlToUser).then().log().all();

	}
}
