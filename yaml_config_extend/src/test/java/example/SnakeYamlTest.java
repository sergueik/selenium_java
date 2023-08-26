package example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import example.YamlConfig;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings({ "deprecation", "unchecked" })
public class SnakeYamlTest {

	// https://bitbucket.org/snakeyaml/snakeyaml/wiki/Documentation
	private LoaderOptions options;
	private Yaml yaml;

	private final String data = "aGVsbG8sIHdvcmxkCg==";
	private File file;
	private String text;
	private final String yamlFile = "test.yml";
	private final InputStream resource = getClass().getClassLoader()
			.getResourceAsStream(yamlFile);

	@Before
	public void setup() throws IOException {
		options = new LoaderOptions();
		yaml = new Yaml(options);
		file = File.createTempFile("key", "yaml");
	}

	@After
	public void after() {
		file.delete();
	}

	@Test
	public void test1() throws IOException {
		String data = "---\n\nversion: 3\nservices:\n  db:\n    image: mysql\n    container_name: mysql_db\n    restart: always\n    environment:\n      - MYSQL_ROOT_PASSWORD=secret\n";

		saveString2File(file, data);

		System.err.println("Written " + file.length() + " chars of data");
		String filePath = file.getAbsolutePath();

		Path resource = Paths.get(filePath);
		byte[] payload = Files.readAllBytes(resource);
		text = new String(payload, "UTF-8");

		// List<String> list = (List<String>) yaml.load(text);
		System.out.println(text);
		Object object1 = yaml.load(text);
		assertThat(object1, notNullValue());
		Map<String, Object> object = (Map<String, Object>) yaml.load(text);
		System.out.println(object);
		assertThat(object.containsKey("services"), is(true));

		Map<String, Object> services = (Map<String, Object>) object.get("services");
		assertThat(services.containsKey("db"), is(true));

		Map<String, Object> db = (Map<String, Object>) services.get("db");
		assertThat(db, notNullValue());
		assertThat(db.containsKey("image"), is(true));

	}

	@Test
	public void test2() throws IOException {
		text = readAll(resource);
		Object object1 = yaml.load(text);
		assertThat(object1, notNullValue());
		Map<String, Object> object = (Map<String, Object>) yaml.load(text);
		System.out.println(object);
		assertThat(object.containsKey("services"), is(true));

		Map<String, Object> services = (Map<String, Object>) object.get("services");
		assertThat(services.containsKey("db"), is(true));

		Map<String, Object> db = (Map<String, Object>) services.get("db");
		assertThat(db, notNullValue());
		assertThat(db.containsKey("image"), is(true));
		assertThat(services.containsKey("names"), is(true));
		List<Map<String, Object>> names = (List<Map<String, Object>>) services
				.get("names");
		assertThat(names, notNullValue());
		assertThat(names.size(), is(2));

		Map<String, Object> name1 = (Map<String, Object>) names.get(0);
		assertThat(name1, notNullValue());
		assertThat(name1.containsKey("first"), is(true));
		assertThat(name1.containsKey("last"), is(true));
	}

	// origin:http://www.java2s.com/ref/java/java-file-write-text-to-file.html
	public static final boolean saveString2File(File file, String content) {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file)));
			bw.write(content);
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// http://www.java2s.com/example/java-utility-method/inputstream-read-all/readall-inputstream-in-c5be9.html
	public static final String readAll(InputStream in) throws IOException {
		StringBuilder bob = new StringBuilder();
		int c;
		while ((c = in.read()) != -1) {
			bob.append((char) c);
		}
		return bob.toString();
	}
}
