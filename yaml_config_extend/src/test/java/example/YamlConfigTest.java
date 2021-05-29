package example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import example.YamlConfig;

import static org.junit.Assert.assertThat;

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
		String value = config.getString("services.names[1].first");
		assertThat(value, notNullValue());
		assertThat(value, is("Andrew"));
	}

	@Test
	public void getStringOutOfIndex() {
		assertThat(config.getString("services.names[3].first"), nullValue());
	}

	@Test
	public void getStringInvalidKey() {
		assertThat(config.getString("services.test.first"), nullValue());
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
	public void getNullString() {
		assertThat(config.getString("services.web.property"), nullValue());
	}

	@Test
	public void getMap() {
		Map<String, Object> value = config.getMap("services.web");
		assertThat(value, notNullValue());
		assertThat(value.keySet(), hasItems(new String[] { "property", "ports",
				"build", "image", "restart", "depends_on", "container_name" }));
	}

	@Test
	public void getNullInMap() {
		Map<String, Object> value = config.getMap("services.web");
		assertThat(value, notNullValue());
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
	public void getDocString() {
		String value = config.getString("shell_command1");
		assertThat(value, notNullValue());
		for (int cnt = 1; cnt != 4; cnt++) {
			assertThat(value, containsString(String.format("Line %s", cnt)));
		}
		assertThat(value, not(containsString("|")));
		// System.err.println(String.format("Found: \"%s\"", value));
	}

	@Test
	public void getDocString2() {
		String value = config.getString("shell_command2");
		assertThat(value, notNullValue());
		for (int cnt = 1; cnt != 4; cnt++) {
			assertThat(value, containsString(String.format("Line %s", cnt)));

		}
		assertThat(value, not(containsString("|-")));
		// System.err.println(String.format("Found: \"%s\"", value));
	}

}