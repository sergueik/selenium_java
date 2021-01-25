package tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class TestBaidu {
	@Test
	public void test() {
		given().log().all().get("http://www.baidu.com").then().log().all()
				.statusCode(200);
	}

}
