package example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import example.Utils;
import im.nll.data.extractor.Extractors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import static im.nll.data.extractor.Extractors.*;

import example.Utils;

public class ExtractorsTest {
	private String jsonString;
	private Extractors extractors = null;
	private static final Logger logger = LogManager.getLogger(ExtractorsTest.class);

	@Before
	public void before() {
		jsonString = Utils.getScriptContent("example.json");
		extractors = Extractors.on(jsonString);
	}

	// loosely typed de-serialization with some com.jayway.jsonpath paths
	@Test
	public void basicTest1ByJson() throws Exception {
		String store = extractors.extract("store", Extractors.json("$[\"store\"]")).asJSONString();
		assertThat(store, notNullValue());
		logger.log(Level.INFO, "Store: " + store);
	}

	@Test
	public void basicTest2ByJson() throws Exception {
		Map<String, String> store = extractors.extract("store", Extractors.json("$[\"store\"]")).asMap();
		logger.log(Level.INFO, "Store:" + store);
		assertThat(store, hasKey("store"));
		logger.log(Level.INFO, "Store: " + store.get("store"));
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
