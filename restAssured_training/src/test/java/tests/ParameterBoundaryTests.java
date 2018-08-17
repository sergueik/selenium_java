package tests;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.core.Is.is;
import contract.ResponseContainer;
import io.restassured.path.json.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

// based on: https://github.com/testvagrant/RESTTests_RestAssured
// 
public class ParameterBoundaryTests {

	@Test
	// NOTE: original BDD-style method name:
	// shouldReturn0ItemsWhenMaxResultsIsSpecifiedAs0
	public void shouldReturn0ItemsWhenMaxResultsIsSpecifiedAs0()
			throws IOException {
		String responseString = given().param("max-results", "0").param("v", "2")
				.param("alt", "jsonc").when()
				.get("http://gdata.youtube.com/feeds/api/videos").asString();

		ObjectMapper mapper = new ObjectMapper();
		ResponseContainer responseContainer = mapper.readValue(responseString,
				ResponseContainer.class);
		assertThat(responseContainer.getData().getItems(), is(nullValue()));
	}

	@Test
	// NOTE: original BDD-style method name:
	// shouldReturn50ItemsWhenMaxResultsIsSpecifiedAs50
	public void shouldResponseObjectSize() {
		String responseString = given().param("max-results", "50").param("v", "2")
				.param("alt", "jsonc").when()
				.get("http://gdata.youtube.com/feeds/api/videos").asString();

		JsonPath jp = new JsonPath(responseString);
		assertThat(jp.getList("data.items").size(), is(50));
	}

	@Test
	// NOTE: original BDD-style method name:
	// shouldReturnErrorWhenItemsRequestedis51
	public void exceedMaxSizeTest() {
		given().param("max-results", "51").param("v", "2").param("alt", "jsonc")
				.when().get("http://gdata.youtube.com/feeds/api/videos").then()
				.assertThat().body("error.code", equalTo(400)).and().assertThat()
				.body("error.message", equalTo(
						"Max-results value is too high. Only up to 50 results can be returned per query."));
	}

	@Test
	// NOTE: original BDD-style method name:
	// shouldReturnErrorWhenItemsRequestedIsMinus1
	public void invalidMaxResultTest() {
		given().param("max-results", "-1").param("v", "2").param("alt", "jsonc")
				.when().get("http://gdata.youtube.com/feeds/api/videos").then()
				.assertThat().body("error.code", equalTo(400)).and().assertThat()
				.body("error.message", equalTo("Invalid max-results"));
	}

}
