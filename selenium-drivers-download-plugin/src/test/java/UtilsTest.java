import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.github.abhishek8908.util.DriverUtil.*;

public class UtilsTest {

	@Ignore
	@Test
	public static void newDownload() throws IOException {

		String fromFile = "https://github.com/mozilla/geckodriver/releases/download/v0.20.1/geckodriver-v0.20.1-win32.zip";
		String toFile = "c:/temp/geckodriver-v0.20.1-win32.zip";
		// connectionTimeout, readTimeout = 10 seconds
		FileUtils.copyURLToFile(new URL(fromFile), new File(toFile), 10000, 10000);

	}

	@Test
	public void testSystemProperty() throws ConfigurationException {
		System.setProperty("ver", "2.39");
		System.setProperty("os", "linux64");
		System.setProperty("ext", "zip");
		System.out.println(DriverUtil.readProperty("chromedriver.download.url"));
	}

	@Test
	public void urlTest() {
		getFileNameFromUrl(
				"https://chromedriver.storage.googleapis.com/2.39/chromedriver_win32.zip");
	}

	@Ignore
	@Test
	public void fileRename() throws IOException {
		changeFileName("c:\\temp\\chromedriver.exe",
				"c:\\temp\\chromedriver-" + "2.38" + ".exe");
	}

	@Test
	public void testCleanDir() {
		cleanDir("c:\\temp");
	}

	@Test
	public void testDriverExists() throws IOException {
		System.out
				.println(checkDriverVersionExists("chromedriver", "2.38", "c:/temp"));
	}

	@Test
	public void testProperty() throws ConfigurationException {
		System.out.println(readProperty("chrome.download.url"));
		System.getProperty("os.name");
	}

}
