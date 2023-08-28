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
// import java.lang.reflect.Constructor;
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
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.nodes.Tag;

import static org.hamcrest.MatcherAssert.assertThat;
import example.Configuration;
import example.Microservice;
import example.Services;
import example.Settings;

@SuppressWarnings({ "deprecation", "unchecked" })
public class SnakeStronglyTypedYamlTest {

	// https://bitbucket.org/snakeyaml/snakeyaml/wiki/Documentation
	private LoaderOptions options;
	private Yaml yaml1;
	private Yaml yaml2;
	private Yaml yaml3;

	private String text;
	private final String yamlFile1 = "task_config.yml";
	private final InputStream resource1 = getClass().getClassLoader()
			.getResourceAsStream(yamlFile1);

	private final String yamlFile3 = "task_config_plain.yml";
	private final InputStream resource3 = getClass().getClassLoader()
			.getResourceAsStream(yamlFile3);

	private final String yamlFile2 = "task_config_compact.yml";
	private final InputStream resource2 = getClass().getClassLoader()
			.getResourceAsStream(yamlFile2);
	DumperOptions dumperOptions = new DumperOptions();

	@Before
	public void setup() throws IOException {
		options = new LoaderOptions();
		yaml1 = new Yaml(options);
		yaml2 = new Yaml(new Constructor(Configuration.class));
		dumperOptions.setIndent(2);
		dumperOptions.setPrettyFlow(true);
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		yaml3 = new Yaml(dumperOptions);
	}

	@After
	public void after() {
	}

	// see also:
	// https://stackabuse.com/reading-and-writing-yaml-files-in-java-with-snakeyaml/
	// https://github.com/TaimoorChoudhary/snake-yaml
	@Test
	public void test3() {

		Settings settings = new Settings(true, 100, "value");
		Microservice microservice = new Microservice("db", "mysql:latest");
		Services services = new Services(
				Arrays.asList(new Microservice[] { microservice }));
		String version = "2.0";
		Configuration object3 = new Configuration(version, services, settings);
		String output = yaml3.dump(object3);

		System.err.println("test3 result (with indentation):" + "\n" + output);
	}

	// see also:
	// https://stackabuse.com/reading-and-writing-yaml-files-in-java-with-snakeyaml/
	@Test
	public void test4() /* throws IOException */ {

		Settings settings = new Settings(true, 100, "value");
		Microservice microservice1 = new Microservice("db", "mysql:latest");
		Microservice microservice2 = new Microservice("web", "alpine:python");
		Services services = new Services(
				Arrays.asList(new Microservice[] { microservice1, microservice2 }));
		String version = "2.0";
		Configuration object3 = new Configuration(version, services, settings);
		String output = yaml2.dump(object3);

		System.err.println("test4 result:" + output);
		Configuration object2 = (Configuration) yaml2.load(output);
		assertThat(object2, notNullValue());
		System.err.println("test4 : " + "Configuration:  " + object2);
		System.err.println("dump:  ");
		// see also:
		// https://www.tabnine.com/code/java/methods/org.yaml.snakeyaml.Yaml/dumpAs
		yaml2.dumpAs(object2, Tag.STR, FlowStyle.BLOCK);
	}

	@Test
	public void test1() throws IOException {
		text = readAll(resource3);
		Map<String, Object> object1 = (Map<String, Object>) yaml1.load(text);
		assertThat(object1, notNullValue());
		System.err.println("Object:  " + object1);
		// Configuration object2 = (Configuration) object1;
		// assertThat(object2, notNullValue());
		// System.err.println("Configuration: " + object2);
	}

	// @Ignore
	@Test(expected = ConstructorException.class)
	public void test2() throws ConstructorException, IOException {
		try {
			text = readAll(resource1);
			Configuration object2 = (Configuration) yaml2.load(text);
			assertThat(object2, notNullValue());
			System.err.println("Configuration:  " + object2);
		} catch (ConstructorException e) {

			System.err.println("test2: " + "exception (captured): " + e.getClass()
					+ " " + e.toString());
			throw e;
		}
	}

	// @Ignore
	@Test
	public void test5() throws IOException {
		text = readAll(resource2);
		Configuration object2 = (Configuration) yaml2.load(text);
		assertThat(object2, notNullValue());
		System.err.println("Configuration:  " + object2);
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
