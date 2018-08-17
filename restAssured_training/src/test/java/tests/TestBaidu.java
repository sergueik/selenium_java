package tests;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;

public class TestBaidu {
	@Test
	public void test() {
		given().log().all().get("http://www.baidu.com").then().log().all()
				.statusCode(200);
	}

}
