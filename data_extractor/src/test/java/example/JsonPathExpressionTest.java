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

import example.Utils;

public class JsonPathExpressionTest {
	private DocumentContext jsonContext;

	// https://qna.habr.com/q/777357
	// https://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html
	// https://support.smartbear.com/alertsite/docs/dashboards/dashboard.html
	@Before
	public void before() {
		jsonContext = JsonPath.parse(Utils.getScriptContent("goal_exclude.json"));
	}

	@Test
	public void test1() {
		List<Map<String, Object>> data = jsonContext
				.read("$.[?(@.acc != 'Not Found')]");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		List<Integer> ids = data.stream()
				.map(o -> Integer.parseInt(o.get("id").toString()))
				.collect(Collectors.toList());
		System.err.println("test1 results(ids): " + ids);
	}

	@Test
	public void test2() {
		List<String> data = jsonContext.read("$.[?(@.acc != 'Not Found')].acc");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		System.err.println("test2 results(acc): " + data);
	}

	@Test
	public void test3() {
		List<String> data = jsonContext.read("$.[?(@.id > 1)][?(@.pass)].acc");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		System.err.println("test3 results(acc): " + data);
	}

	@Test
	public void test4() {
		List<String> data = jsonContext.read("$.[?(@.id < 3 && @.pass)].acc");
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		System.err.println("test4 results(acc): " + data);
	}

	// @Ignore
	@Test
	public void test5() {
		Filter categoryFilter = Filter
				.filter(Criteria.where("acc").eq("Not Found"));
		// java.lang.IllegalArgumentException: path can not be null or empty
		List<Map<String, Object>> data = jsonContext.read("$.[?]", categoryFilter);
		assertThat(data, notNullValue());
		assertThat(data.size(), greaterThan(0));
		System.err.println("test5 results(acc): ");
		for (int cnt = 0; cnt != data.size(); cnt++) {
			System.err.println(
					String.format("Books[%d]: %s ", cnt, data.get(cnt).toString()));
		}
	}

}
