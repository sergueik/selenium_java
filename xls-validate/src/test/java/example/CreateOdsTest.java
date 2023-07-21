package example;

import org.junit.Test;

import example.ODS;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static example.ODS.containsText;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateOdsTest {
	@Test
	public void fromFile() throws Exception {
		File file = new File(
				getClass().getClassLoader().getResource("statement.ods").toURI());
		assertThat(new ODS(file), containsText("allure"));
	}

	@Test
	public void fromUrl() throws Exception {
		URL url = getClass().getClassLoader().getResource("statement.ods");
		assertThat(new ODS(url), containsText("allure"));
	}

	@Test
	public void fromUri() throws Exception {
		URI uri = getClass().getClassLoader().getResource("statement.ods").toURI();
		assertThat(new ODS(uri), containsText("allure"));
	}

	@Test
	public void fromInputStream() throws Exception {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("statement.ods");
		assertThat(new ODS(inputStream), containsText("allure"));
	}

	@Test
	public void fromBytes() throws Exception {
		byte[] bytes = Files.readAllBytes(Paths
				.get(getClass().getClassLoader().getResource("statement.ods").toURI()));
		assertThat(new ODS(bytes), containsText("allure"));
	}
}
