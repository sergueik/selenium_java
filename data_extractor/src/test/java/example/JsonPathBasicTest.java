package example;

import im.nll.data.extractor.Extractors;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import example.Utils;

public class JsonPathBasicTest {
	private DocumentContext jsonContext;

	@Before
	public void before() {
		jsonContext = JsonPath.parse(Utils.getScriptContent("example.json"));
	}

	// loosely typed de-serialization with some com.jayway.jsonpath paths
	// testng syntax:
	// @Test(expectedExceptions = ClassCastException.class)
	@Test(expected = ClassCastException.class)
	public void test1() throws Exception {
		String store = jsonContext.read("$[\"store\"]");
		assertThat(store, notNullValue());
		System.err.println("Store: " + store);
		// java.util.LinkedHashMap cannot be cast to java.lang.String
	}

	@Test
	public void test2() {
		Map<String, Object> store = jsonContext.read("$[\"store\"]");
		assertThat(store, notNullValue());
		assertThat(store.containsKey("book"), is(true));
		System.err.println("Store: " + store);
	}

	@Test
	public void test3() throws Exception {
		JSONArray books = jsonContext.read("$.store.book");
		assertThat(books, notNullValue());
		assertThat(books.size(), is(4));
		System.err.println("test3 result (Books[0]): " + books.get(0).toString());
	}

	@Test
	public void test4() {
		List<Map<String, Object>> books = jsonContext.read("$.store.book");
		assertThat(books, notNullValue());
		assertThat(books.size(), is(4));
		System.err.println("test4 result(books[1]): " + books.get(1).toString());
	}

	@Test
	public void test5() {
		List<Map<String, Object>> books = jsonContext
				.read("$.store.book[?(@.category == \"fiction\")] ");
		assertThat(books, notNullValue());
		assertThat(books.size(), greaterThan(0));
		System.err.println("test5 result(Books[1]): " + books.get(1).toString());
	}

	@Test
	public void test6() {
		List<Map<String, Object>> stores = jsonContext
				.read("$.store[?(@.book[0].category == \"reference\")]");
		assertThat(stores, notNullValue());
		assertThat(stores.size(), greaterThan(0));
		System.err.println("test6 result(book): " + stores.get(0).get("book"));
	}

	@Test
	public void test7() {
		Filter categoryFilter = Filter
				.filter(Criteria.where("category").eq("fiction"));
		List<Map<String, Object>> books = jsonContext.read("$.store.book[?]",
				categoryFilter);
		assertThat(books, notNullValue());
		assertThat(books.size(), greaterThan(0));
		System.err.println("test7 results(category):");
		for (int cnt = 0; cnt != books.size(); cnt++) {
			System.err.println(String.format("Books[%d]: %s ", cnt,
					books.get(cnt).get("category").toString()));
		}
	}

	// https://www.programcreek.com/java-api-examples/?api=com.jayway.jsonpath.Filter
	@Test
	public void test8() {
		Filter categoryFilter = Filter
				.filter(Criteria.where("@.category").ne("fiction"));
		List<Map<String, Object>> books = jsonContext.read("$.store.book[?]",
				categoryFilter);
		assertThat(books, notNullValue());
		assertThat(books.size(), greaterThan(0));
		System.err.println("test8 results(category):");
		for (int cnt = 0; cnt != books.size(); cnt++) {
			System.err.println(String.format("Books[%d]: %s ", cnt,
					books.get(cnt).get("category").toString()));
		}
	}

}
