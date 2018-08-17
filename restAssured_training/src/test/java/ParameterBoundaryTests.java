import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.core.Is.is;
// import contract.ResponseContainer;
import io.restassured.path.json.JsonPath;

// import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

// based on: https://github.com/testvagrant/RESTTests_RestAssured
public class ParameterBoundaryTests {

	/*
	@Test
	public void shouldReturn0ItemsWhenMaxResultsIsSpecifiedAs0()
			throws IOException {
		String responseString = given().param("max-results", "0").param("v", "2")
				.param("alt", "jsonc").when()
				.get("http://gdata.youtube.com/feeds/api/videos").asString();
	
		ObjectMapper mapper = new ObjectMapper();
		ResponseContainer responseContainer = mapper.readValue(responseString,
				ResponseContainer.class);
		assertNull(responseContainer.getData().getItems());
	}
	*/
	@Test
	public void shouldReturn50ItemsWhenMaxResultsIsSpecifiedAs50() {
		String responseString = given().param("max-results", "50").param("v", "2")
				.param("alt", "jsonc").when()
				.get("http://gdata.youtube.com/feeds/api/videos").asString();

		JsonPath jp = new JsonPath(responseString);
		assertThat(jp.getList("data.items").size(), is(50));
	}

	@Test
	public void shouldReturnErrorWhenItemsRequestedis51() {
		given().param("max-results", "51").param("v", "2").param("alt", "jsonc")
				.when().get("http://gdata.youtube.com/feeds/api/videos").then()
				.assertThat().body("error.code", equalTo(400)).and().assertThat()
				.body("error.message", equalTo(
						"Max-results value is too high. Only up to 50 results can be returned per query."));

	}

	@Test
	public void shouldReturnErrorWhenItemsRequestedIsMinus1() {
		given()
		.param("max-results", "-1")
		.param("v", "2")
		.param("alt", "jsonc")
		.when()
		.get("http://gdata.youtube.com/feeds/api/videos")
		.then()
		.assertThat()
		.body("error.code", equalTo(400))
		.and()
		.assertThat()
		.body("error.message", equalTo("Invalid max-results"));

	}

}
