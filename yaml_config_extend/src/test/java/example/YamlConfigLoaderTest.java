package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class YamlConfigLoaderTest {
	private final String yamlFile = "test.yml";
	private final InputStream resource = getClass().getClassLoader()
			.getResourceAsStream(yamlFile);
	private final YamlConfig config = YamlConfig.load(resource);
	private final String propertiesFilePath = String
			.format("%s/src/test/resources", System.getProperty("user.dir"));
	private final String invalidPropertiesFilePath = String
			.format("%s/src/main/resources", System.getProperty("user.dir"));

	@Before
	public void setup() {
		YamlConfig.setDebug(true);
	}

	@Test
	public void loadfromThread() {
		YamlConfig value = YamlConfigLoader.getYamlConfig(yamlFile, true);
		assertThat(value, notNullValue());
	}

	@Test
	public void loadfromResourcePath() {
		YamlConfig value = YamlConfigLoader.getYamlConfig(yamlFile,
				propertiesFilePath);
		assertThat(value, notNullValue());
	}

	@Test
	public void loadfromInvalidResourcePath() {
		YamlConfig value = YamlConfigLoader.getYamlConfig(yamlFile,
				invalidPropertiesFilePath);
		assertThat(value, nullValue());
	}

}
