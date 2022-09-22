package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class YamlConfigExtendedLoaderTest {

	private YamlConfigExtended config;
	private final String yamlFile = "test.yml";
	private final String propertiesFilePath = String
			.format("%s/src/test/resources", System.getProperty("user.dir"));
	private final String invalidPropertiesFilePath = String
			.format("%s/src/main/resources", System.getProperty("user.dir"));

	@Before
	public void setup() {
		YamlConfigExtended.setDebug(true);
	}

	@Test
	public void loadfromThread() {
		config = YamlConfigLoader.getYamlConfig(yamlFile, true);
		assertThat(config, notNullValue());
	}

	@Test
	public void loadfromResourcePath() {
		config = YamlConfigLoader.getYamlConfig(yamlFile, propertiesFilePath);
		assertThat(config, notNullValue());
	}

	@Test
	public void loadfromInvalidResourcePath() {
		config = YamlConfigLoader.getYamlConfig(yamlFile,
				invalidPropertiesFilePath);
		assertThat(config, nullValue());
	}

}
