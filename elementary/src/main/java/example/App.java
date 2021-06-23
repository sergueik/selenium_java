package example;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class App {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	public static void main(String[] args)
			throws InterruptedException, java.io.IOException {
		if (debug) {
			System.err.println("started");
		}

		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("url");
		commandLineParser.saveFlagValue("browser");
		commandLineParser.saveFlagValue("host");
		commandLineParser.saveFlagValue("port");
		if (debug) {
			System.err.println("parse args");
		}

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		String browser = commandLineParser.getFlagValue("browser");
		if (browser == null) {
			System.err.println("Missing required argument: browser");
			return;
		}
		String port = commandLineParser.hasFlag("port")
				? commandLineParser.getFlagValue("port") : "4444";
		String host = commandLineParser.hasFlag("host")
				? commandLineParser.getFlagValue("host") : "localhost";

		if (commandLineParser.getFlagValue("url") == null) {
			System.err.println("Missing required argument: url");
			return;
		}
		String url = commandLineParser.getFlagValue("url");
		// need to handle prefix
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browser);
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
		String hub = String.format("http://%s:%s/wd/hub", host, port);
		if (debug) {
			System.err.println("launching browser on hub " + hub);
		}
		final WebDriver driver = new RemoteWebDriver(new URL(hub), capabilities);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Go to URL
		// "http://www.hollandamerica.com/"
		driver.get(String.format("http://%s", url));

		// Maximize Window
		driver.manage().window().maximize();

		// Wait For Page To Load
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		// take a screenshot
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		// save the screenshot in png format on the disk.
		FileUtils.copyFile(scrFile,
				new File(FilenameUtils.concat(currentDir, "screenshot.png")));
		driver.close();
		driver.quit();

	}

}
