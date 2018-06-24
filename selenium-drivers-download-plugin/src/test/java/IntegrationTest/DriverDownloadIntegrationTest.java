package IntegrationTest;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.github.abhishek8908.util.DriverUtil.download;

public class DriverDownloadIntegrationTest {

	@Test
	// NOTE: PropertiesConfiguration
	public void donloadTest() throws IOException, ConfigurationException {
		System.setProperty("sys:ver", "2.39");
		System.setProperty("sys:os", "win32");
		System.setProperty("sys:ext", "zip");
		download("chromedriver", "c:\\temp", "2.39");
	}

}
