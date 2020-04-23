package example;

import im.nll.data.extractor.Extractors;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static im.nll.data.extractor.Extractors.*;

public class ExtractorsTest {
	private String jsonString;
	private Extractors extractors = null;

	@Before
	public void before() {
		jsonString = getScriptContent("example.json");
		extractors = Extractors.on(jsonString);
	}

	// loosely typed de-serialization with some com.jayway.jsonpath paths
	@Test
	public void basicTest1ByJson() throws Exception {
		String store = extractors.extract("store", Extractors.json("$[\"store\"]")).asJSONString();
		assertThat(store, notNullValue());
		System.err.println("Store: " + store);
	}

	@Test
	public void basicTest2ByJson() throws Exception {
		Map<String, String> store = extractors.extract("store", Extractors.json("$[\"store\"]")).asMap();
		System.err.println("Store:" + store);
		assertThat(store, hasKey("store"));
		System.err.println("Store: " + store.get("store"));
	}

	// strongly typed de-serialization with some jq paths
	@Test
	public void testToBeanListByJson() throws Exception {
		List<Book> books = extractors.split(json("$..book.*")).extract("category", json("$..category"))
				.extract("author", json("$..author")).extract("title", json("$..title"))
				.extract("price", json("$..price")).asBeanList(Book.class);
		isValidBooks(books);
	}

	@Test
	public void testToBeanListByJsonString() throws Exception {
		List<Book> books = extractors.split("json:$..book.*").extract("category", "json:$..category")
				.extract("author", "json:$..author").extract("title", "json:$..title").extract("price", "json:$..price")
				.asBeanList(Book.class);
		isValidBooks(books);
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = ExtractorsTest.class.getClassLoader().getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	private void isValidBooks(List<Book> books) throws AssertionError {

		assertThat(books, notNullValue());
		// Assert.assertNotNull(books);
		assertThat(books.size(), is(4));
		// Assert.assertEquals(books.size(), 4);
		final Book second = books.get(1);
		assertThat(second.getCategory(), is("fiction"));
		// Assert.assertEquals(second.getCategory(), "fiction");
		assertThat(second.getAuthor(), containsString("Evelyn Waugh"));
		// Assert.assertEquals(second.getAuthor(), "Evelyn Waugh");
		assertThat(second.getTitle(), containsString("Sword of Honour"));
		// Assert.assertEquals(second.getTitle(), "Sword of Honour");
		assertThat(second.getPrice(), is(new Double(12.99)));
		// Assert.assertEquals(second.getPrice(), new Double(12.99));
	}

	public static class Book {
		private String category;
		private String author;
		private String title;
		private Double price;

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}
	}

}
