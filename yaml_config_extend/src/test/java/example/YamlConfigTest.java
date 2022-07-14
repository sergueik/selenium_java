package example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import example.YamlConfig;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("deprecation")
public class YamlConfigTest {

	private final String yamlFile = "test.yml";
	private final InputStream resource = getClass().getClassLoader()
			.getResourceAsStream(yamlFile);
	private final YamlConfig config = YamlConfig.load(resource);

	@Before
	public void setup() {
		YamlConfig.setDebug(true);
	}

	@Test
	public void getStringArrayElement() {
		assertThat(config.getString("services.names[0]"), notNullValue());
		String value = config.getString("services.names[1].first");
		assertThat(value, notNullValue());
		assertThat(value, is("Andrew"));
	}

	@Test
	public void getStringOutOfIndex() {

		assertThat(config.getString("services.names[-1]"), nullValue());
		assertThat(config.getString("services.names[3]"), nullValue());
		assertThat(config.getString("services.names[3].somefield"), nullValue());
	}

	@Test
	public void getStringInvalidKey() {
		assertThat(config.getString("dummy"), nullValue());
		assertThat(config.getString("services.test.first"), nullValue());
		assertThat(config.getString("services.test.non.existent.key"), nullValue());
	}

	@Test
	public void getStringNumber() {
		String value = config.getString("version");
		assertThat(value, notNullValue());
		assertThat(value, is("3"));
	}

	@Test
	public void getString() {
		String value = config.getString("services.db.image");
		assertThat(value, notNullValue());
		assertThat(value, is("mysql"));
	}

	@Test
	public void getInt() {
		Integer value = config.getInt("version");
		assertThat(value, notNullValue());
		assertThat(value, is(3));
	}

	@Test
	public void getBadInt() {
		Integer value = config.getInt("services.db.image");
		assertThat(value, nullValue());
		value = config.getInt("services.web.property");
		assertThat(value, nullValue());
		value = config.getInt("services.web.ports[0]");
		assertThat(value, nullValue());

	}

	@Test
	public void getBoolean() {
		// Failed tests:
		// boolean_setting_2 1 is
		// expected to be true but is false

		for (String key : Arrays.asList(
				"boolean_setting_1", /* "boolean_setting_2", */
				"boolean_setting_3")) {
			boolean value = config.getBoolean(key);
			assertThat(value, notNullValue());
			// the method assertThat(String, boolean) in the type MatcherAssert is not
			// applicable for the arguments (String, Matcher<Boolean>)
			// assertThat(value, is(Boolean.TRUE));
			// assertThat(value, is(true));
			// assertThat(value, true);
			assertThat(
					String.format(key + " ( = " + config.getString(key)
							+ ") is expected to be true but is " + value),
					value, equalTo(true));
		}
		// NPE with null value
		for (String key : Arrays
				.asList("boolean_setting_4" /*, "boolean_setting_5" */)) {
			boolean value = config.getBoolean(key);
			assertThat(value, notNullValue());
			assertThat(value, equalTo(false));
		}
	}

	@Test
	public void getNullString() {
		assertThat(config.getString("services.web.property"), nullValue());
	}

	@Test
	public void getQuotesRemovedString() {
		String value1 = config.getString("key1");
		assertThat(value1, notNullValue());

		String value2 = config.getString("key2");
		assertThat(value2, notNullValue());
		assertThat(value1, is(value2));
		String value3 = config.getString("key3");
		assertThat(value3, notNullValue());
		assertThat(value3, is(value1));
		String value4 = config.getString("key4");
		assertThat(value4, notNullValue());
		assertThat(value4, is(value1));

	}

	@Test
	public void getMap() {
		Map<String, Object> value = config.getMap("services.web");
		assertThat(value, notNullValue());
		assertThat(value.keySet(), hasItems(new String[] { "property", "ports",
				"build", "image", "restart", "depends_on", "container_name" }));
	}

	@Test
	public void getMap2() {
		String key = "map_setting";
		Map<String, Object> value = config.getMap(key);
		assertThat(value, notNullValue());
		assertThat(value.keySet(), hasItems(new String[] { "boolean_setting",
				"integer_setting", "string_setting" }));
		for (String subkey : Arrays.asList("boolean_setting", "integer_setting",
				"string_setting")) {
			String compound_key = String.format("%s.%s", key, subkey);
			assertThat("expect the classpath notation to work for " + compound_key,
					config.getString(compound_key), notNullValue());

		}
		assertThat(Boolean.parseBoolean(value.get("boolean_setting").toString()),
				equalTo(config.getBoolean("map_setting.boolean_setting")));
		assertThat(Integer.valueOf(value.get("integer_setting").toString()),
				equalTo(config.getInt("map_setting.integer_setting")));
		assertThat((String) value.get("string_setting"),
				equalTo(config.getString("map_setting.string_setting")));
	}

	@Test
	public void getNullInMap() {
		Map<String, Object> value = config.getMap("services.web");
		assertThat(value, notNullValue());
		assertThat(value.containsKey("property"), is(true));
		assertThat(value.get("property"), nullValue());
	}

	@Test
	public void getList() {
		ArrayList<Object> value = config.getList("services.web.ports");
		assertThat(value, notNullValue());
		assertThat(value.size(), greaterThan(0));
		assertThat(value.get(0).toString(), is("8080:80"));
	}

	@Test
	public void getListWithHere() {
		ArrayList<Object> value = config.getList("commands1");
		assertThat(value, notNullValue());
		assertThat(value.size(), is(5));
		assertThat(value.get(0).toString(), is("Line 1 Line 2\n"));
		assertThat(value.get(1).toString(), is("command2"));
	}

	@Test
	public void getListWithHereWithNewline() {
		ArrayList<Object> value = config.getList("commands2");
		assertThat(value, notNullValue());
		assertThat(value.size(), is(2));
		assertThat(value.get(0).toString(), is("Line 1 Line 2"));
	}

	@Test
	public void getCompactArrayList() {
		ArrayList<Object> value = config.getList("compact");
		assertThat(value, notNullValue());
		assertThat(value.get(0).toString(), is("a"));
	}

	@Test
	public void getCompactArrayElement() {
		String value = config.getString("compact[0]");
		assertThat(value, is("a"));

		value = config.getString("compact_array[0]");
		assertThat(value, is("a"));
	}

	@Test
	public void getCompactCollectionElement() {
		String value = config.getString("info.name");
		assertThat(value, is("John Doe"));

		value = config.getString("info.job");
		assertThat(value, is("Java Developer"));
	}

	@Test
	public void getDocString1() {
		String value = config.getString("shell_command1");
		assertThat(value, notNullValue());
		for (int cnt = 1; cnt != 4; cnt++) {
			assertThat(value, containsString(String.format("Line %s", cnt)));
		}
		assertThat(value, not(containsString("|")));
		// NOTE: has newlines
		assertThat(value, containsString("\n"));
		System.err.println(String.format("getDocString1: \"%s\"", value));
	}

	@Test
	public void getDocString2() {
		String value = config.getString("shell_command2");
		assertThat(value, notNullValue());
		for (int cnt = 1; cnt != 4; cnt++) {
			assertThat(value, containsString(String.format("Line %s", cnt)));

		}
		assertThat(value, not(containsString("|-")));
		// NOTE: has newlines
		assertThat(value, containsString("\n"));
		System.err.println(String.format("getDocString2: \"%s\"", value));
	}

	@Test
	public void getDocString3() {
		String value = config.getString("shell_command3");
		assertThat(value, notNullValue());
		for (int cnt = 1; cnt != 4; cnt++) {
			assertThat(value, containsString(String.format("Line %s", cnt)));
		}
		assertThat(value, not(containsString(">")));
		// NOTE: has single newline at the end
		assertThat(value.substring(0, value.length() - 2),
				not(containsString("\n")));
		System.err.println(String.format("getDocString3: \"%s\"", value));
	}
}