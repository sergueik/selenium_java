package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import example.Utils;

public class JsonPathExpressionTest {

	private DocumentContext jsonContext;
	private static final Logger logger = LogManager.getLogger(JsonPathExpressionTest.class);

	@Before
	public void before() {
		jsonContext = JsonPath.parse(Utils.getScriptContent("goal_exclude.json"));
	}

	@Test
	public void test1() {
		List<Map<String, Object>> data = jsonContext.read("$.[?(@.acc != 'Not Found')]");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		List<Integer> ids = data.stream().map(o -> Integer.parseInt(o.get("id").toString()))
				.collect(Collectors.toList());
		logger.info("test1 results(ids): " + ids);
	}

	@Test
	public void test2() {
		List<String> data = jsonContext.read("$.[?(@.acc != 'Not Found')].acc");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		logger.log(Level.INFO, "test2 results(acc): " + data);
	}

	@Test
	public void test3() {
		List<String> data = jsonContext.read("$.[?(@.id > 1)][?(@.pass)].acc");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		logger.info("test3 results(acc): " + data);
	}

	@Test
	public void test4() {
		List<String> data = jsonContext.read("$.[?(@.id < 3 && @.pass)].acc");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		logger.info("test4 results(acc): " + data);
	}

	// @Ignore
	@Test
	public void test5() {
		Filter categoryFilter = Filter.filter(Criteria.where("acc").eq("Not Found"));
		// java.lang.IllegalArgumentException: path can not be null or empty
		List<Map<String, Object>> data = jsonContext.read("$.[?]", categoryFilter);
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		logger.info("test5 results(acc): ");
		for (int cnt = 0; cnt != data.size(); cnt++) {
			logger.info(String.format("Books[%d]: %s ", cnt, data.get(cnt).toString()));
		}
	}

}
