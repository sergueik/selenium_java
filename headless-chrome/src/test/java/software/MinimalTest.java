package software;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MinimalTest {

	// public static WebDriver driver;
	public static RemoteWebDriver driver;

	private static String osName = getOSName();
	private static final boolean useChromiumSendCommand = true;
	private static final String chomeDriverPath = osName.equals("windows")
			? (new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath()
			: Paths.get(System.getProperty("user.home")).resolve("Downloads")
					.resolve("chromedriver").toAbsolutePath().toString();
	private static final String downloadFilepath = osName.equals("windows")
			? String.format("%s\\Downloads",
					getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei"))
			: String.format("%s/Downloads", getPropertyEnv("HOME", "/home/serguei"));
	private final String version = "4.2.0"; // "3.150.2";

	private static boolean headless = false;

	@BeforeClass
	public static void setup() throws IOException {
		getOSName();
		headless = setHeadless();
		System.setProperty("webdriver.chrome.driver", chomeDriverPath);

		ChromeOptions options = new ChromeOptions();
		// TODO: 64 bit
		// PROCESSOR_ARCHITECTURE=AMD64
		options.setBinary(osName.equals("windows") ? (new File(

				(System.getenv().containsKey("PROCESSOR_ARCHITECTURE")
						&& System.getenv("PROCESSOR_ARCHITECTURE").matches("(?:AMD64)"))
								? "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"
								: "C:/Program Files/Google/Chrome/Application/chrome.exe"))
										.getAbsolutePath()
				// TODO: determine on the fly
				: "/usr/bin/chromium-browser" /* "/usr/bin/google-chrome" */ );
		if (headless) {
			for (String optionAgrument : (new String[] { "headless",
					"--window-size=1200x800", "disable-gpu" })) {
				options.addArguments(optionAgrument);
			}
			System.out.println("Switching to headless");
		} else {
			System.out.println("Switching to / staying visible");
		}

		// based on:
		// https://stackoverflow.com/questions/48049359/download-files-in-java-selenium-using-chromedriver-and-headless-mode
		// https://automated-testing.info/t/kak-ukazat-papku-dlya-zagruzki-fajlov-chrome-v-rezhime-headless/21107/3
		// (in Russian)
		if (!useChromiumSendCommand) {
			Map<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("browser.setDownloadBehavior", "allow");
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);

			options.setExperimentalOption("prefs", chromePrefs);
		}
		// configuration state support like remote driver
		ChromeDriverService chromeDriverSevice = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(chomeDriverPath)).usingAnyFreePort()
				.build();
		chromeDriverSevice.start();

		// driver = new ChromeDriver(options);

		driver = new ChromeDriver(chromeDriverSevice, options);
		assertThat(driver, notNullValue());
		if (useChromiumSendCommand) {
			Map<String, Object> commandParams = new HashMap<>();
			commandParams.put("cmd", "Page.setDownloadBehavior");
			Map<String, String> params = new HashMap<>();
			params.put("behavior", "allow");
			params.put("downloadPath", downloadFilepath);
			commandParams.put("params", params);
			JSONObject commandParamsObj = new JSONObject(commandParams);

			HttpClient httpClient = HttpClientBuilder.create().build();
			String payload = commandParamsObj.toString();
			System.err.println("Posting: " + payload);
			String u = chromeDriverSevice.getUrl().toString() + "/session/"
					+ driver.getSessionId() + "/chromium/send_command";
			HttpPost request = new HttpPost(u);
			request.addHeader("content-type", "application/json");
			request.setEntity(new StringEntity(payload));
			httpClient.execute(request);
		}

		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

	@Test
	public void testDownload() throws Exception {
		driver.get("http://www.seleniumhq.org/download/");
		System.err.println("Downloading:" + driver
				.findElement(By.linkText("32 bit Windows IE")).getAttribute("href"));

		driver.findElement(By.linkText("32 bit Windows IE")).click();

		int downloadInterval = 1000;
		try {
			Thread.sleep(downloadInterval);
		} catch (InterruptedException e) {
			System.err.println("Exception (ignored): " + e.toString());
		}

		boolean fileExists = false;
		for (String filename : (new String[] {
				String.format("IEDriverServer_Win32_%s.zip.crdownload", version),
				String.format("IEDriverServer_Win32_%s.zip", version) })) {
			String filePath = downloadFilepath + File.separator + filename;
			System.err.println("Probing: " + filePath);
			if ((new File(filePath)).exists()) {
				fileExists = true;
				System.err.println("Found " + filePath);
			}
		}
		assertThat(
				"Fileshoulr be present: "
						+ String.format("IEDriverServer_Win32_%s.zip", version),
				fileExists, is(true));
	}

	@Test
	public void dynamicSearchButtonTest() {

		try {
			driver.get("http://www.google.com");
			WebElement queryBox = driver.findElement(By.name("q"));
			queryBox.sendKeys("headless firefox");
			WebElement searchButtonnStatic = driver.findElement(By.name("btnK"));

			// if the script performing Google search is running slowly
			// enough search suggestions are found and the dropdown is pupulated
			// and hides the original search button
			// the page renders a new search button inside the dropdown
			WebElement searchButtonnDynamic = driver.findElement(By.cssSelector(
					"span.ds:nth-child(1) > span.lsbb:nth-child(1) > input.lsb"));
			if (searchButtonnDynamic != null) {
				System.err.println("clicking the dynamic search button");
				searchButtonnDynamic.click();
			} else {
				System.err.println("clicking the static search button");
				searchButtonnStatic.click();
			}
			WebElement iresDiv = driver.findElement(By.id("ires"));
			iresDiv.findElements(By.tagName("a")).get(0).click();
			System.err.println(
					"Response: " + driver.getPageSource().substring(0, 120) + "...");
		} catch (WebDriverException e) {
			System.err.println("Excepion (ignored) " + e.toString());
			// Without using dynamic search button,
			// approximately 1/3 (in headless mode, at least )
			// of the test runs result in exception
			// Element <input name="btnK" type="submit"> is not clickable at
			// point (607,411) because another element <div class="sbqs_c">
			// obscures it (the name of obscuring element varies)
			try {
				// take screenshot in catch block.
				System.err.println("Taking a screenshot");
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String currentDir = System.getProperty("user.dir");
				FileUtils.copyFile(scrFile,
						new File(FilenameUtils.concat(currentDir, "screenshot.png")));
			} catch (IOException ex) {
				System.err.println(
						"Excepion when taking the screenshot (ignored) " + ex.toString());
				// ignore
			}
		}
	}

	@Ignore
	@Test
	public void Test() {
		driver.navigate().to("https://ya.ru/");
		WebElement element = driver
				.findElements(
						By.cssSelector("div.search2__button > button > span.button__text"))
				.get(0);
		final String text = element.getAttribute("outerHTML");
		System.err.println("Text: " + text);
		Assert.assertEquals(element.getText(), "Найти");
		assertThat(element.getText(), containsString("Найти")); // quotes
	}

	@AfterClass
	public static void teardown() {
		if (driver != null) {
			try {
				// take screenshot in teardown.
				System.err.println("Taking a screenshot");
				File scrFile = ((TakesScreenshot) driver)

						.getScreenshotAs(OutputType.FILE);
				String currentDir = System.getProperty("user.dir");
				FileUtils.copyFile(scrFile,
						new File(FilenameUtils.concat(currentDir, "screenshot.png")));
			} catch (IOException ex) {
				System.err.println(
						"Excepion when taking the screenshot (ignored) " + ex.toString());
				// ignore
			}
			driver.quit();
		}
	}

	// Utilities
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public static boolean setHeadless() {
		boolean headless = false;
		if (System.getenv().containsKey("HEADLESS")
				&& System.getenv("HEADLESS").matches("(?:true|yes|1)")) {
			headless = true;
		}
		if (!(getOSName().equals("windows"))
				&& !(System.getenv().containsKey("DISPLAY"))) {
			headless = true;
		}

		// alternatively not even add the environment variable WINDOWS_NO_DISPLAY
		if (getOSName().equals("windows")) {
			headless = false;
			System.out
					.println("Observed environment keys: " + System.getenv().keySet());

			for (String key : (new String[] { "JAVA_OPTS", "MAVEN_OPTS",
					"JAVA_TOOL_OPTIONS" })) {
				System.out.println(String.format("Checking environment %s: %s", key,
						System.getenv(key)));
			}

			// NOTE: when set through batch file the new environment variable
			// WINDOWS_NO_DISPLAY
			// remains undiscovered
			if (System.getenv().containsKey("WINDOWS_NO_DISPLAY")
					&& System.getenv("WINDOWS_NO_DISPLAY").matches("(?:true|yes|1)")) {
				System.out.println("Detected WINDOWS_NO_DISPLAY");
				headless = true;
			}
			if (getPropertyEnv("WINDOWS_NO_DISPLAY", "").matches("(?:true|yes|1)")) {
				System.out
						.println("Detected WINDOWS_NO_DISPLAY environment or property");
				headless = true;
			}
			if (System.getenv().containsKey("JAVA_TOOL_OPTIONS")
					&& System.getenv("JAVA_TOOL_OPTIONS")
							.matches(".*WINDOWS_NO_DISPLAY=(?:true|yes|1).*")) {
				System.out.println("Detected WINDOWS_NO_DISPLAY in JAVA_TOOL_OPTIONS");
				headless = true;
			}
		}
		return headless;
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}

}
