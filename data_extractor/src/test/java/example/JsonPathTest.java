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

import static im.nll.data.extractor.Extractors.*;

public class JsonPathTest {
	private String jsonString;
	private Extractors extractors = null;
	private DocumentContext jsonContext;

	@Before
	public void before() {
		jsonString = getScriptContent("example.json");
		jsonContext = JsonPath.parse(jsonString);
	}

	// loosely typed de-serialization with some com.jayway.jsonpath paths
	// testng syntax:
	// @Test(expectedExceptions = ClassCastException.class)
	@Test(expected = ClassCastException.class)
	public void basicTest1() throws Exception {
		String store = jsonContext.read("$[\"store\"]");
		assertThat(store, notNullValue());
		System.err.println("Store: " + store);
		// java.util.LinkedHashMap cannot be cast to java.lang.String
	}

	@Test
	public void basicTest2() {
		Map<String, Object> store = jsonContext.read("$[\"store\"]");
		assertThat(store, notNullValue());
		assertThat(store.containsKey("book"), is(true));
		System.err.println("Store: " + store);
	}

	@Test
	public void basicTest3() throws Exception {
		JSONArray books = jsonContext.read("$.store.book");
		assertThat(books, notNullValue());
		assertThat(books.size(), is(4));
		System.err.println("Books[0]: " + books.get(0).toString());
	}

	@Test
	public void basicTest4() throws Exception {
		List<Map<String, Object>> books = jsonContext.read("$.store.book");
		assertThat(books, notNullValue());
		assertThat(books.size(), is(4));
		System.err.println("Books[1]: " + books.get(1).toString());
	}

	@Test
	public void basicTest5() throws Exception {
		List<Map<String, Object>> books = jsonContext
				.read("$.store.book[?(@.category == \"fiction\")] ");
		assertThat(books, notNullValue());
		assertThat(books.size(), greaterThan(0));
		System.err.println("Books[1]: " + books.get(1).toString());
	}

	@Test
	public void basicTest6() throws Exception {
		List<Map<String, Object>> stores = jsonContext
				.read("$.store[?(@.book[0].category == \"reference\")] ");
		assertThat(stores, notNullValue());
		assertThat(stores.size(), greaterThan(0));
		System.err.println("Store[0] books: " + stores.get(0).get("book"));
	}

	@Test
	public void basicTest7() throws Exception {
		Filter categoryFilter = Filter
				.filter(Criteria.where("category").eq("fiction"));
		List<Map<String, Object>> books = JsonPath.parse(jsonString)
				.read("$.store.book", categoryFilter);
		assertThat(books, notNullValue());
		assertThat(books.size(), greaterThan(0));
		System.err.println("Books[1]: " + books.get(1).toString());
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = JsonPathTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

}

