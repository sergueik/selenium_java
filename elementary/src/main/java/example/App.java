package example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

// https://gist.github.com/nickname55/880addec70a8303b2359680376d5d066
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class App {

	private static boolean debug = true;
	private static String browser = "chrome";
	private static String port = "4444";
	private static String host = "localhost";
	private static String url = null;

	private static Options options = new Options();

	private static WebDriver driver = null;

	protected static boolean local = false;

	public static void main(String[] args)
			throws InterruptedException, IOException {

		options.addOption(Option.builder("u").longOpt("url").hasArg(true)
				.desc("url to open").required().build());
		options.addOption(Option.builder("h").longOpt("host").hasArg(true)
				.desc("hub host").required(false).build());
		options.addOption(Option.builder("p").longOpt("port").hasArg(true)
				.desc("hub port").required(false).build());
		options.addOption(Option.builder("b").longOpt("browser").hasArg(true)
				.desc("browser to run").required(false).build());
		options.addOption(Option.builder("d").longOpt("debug").hasArg(false)
				.desc("debug").required(false).build());
		options.addOption(Option.builder("l").longOpt("local").hasArg(false)
				.desc("localt").required(false).build());

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		if (debug) {
			System.err.println("parse args");
		}
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("d"))
				debug = true;

			if (cmd.hasOption("b")) {
				browser = cmd.getOptionValue("b");
			}
			if (cmd.hasOption("h")) {
				host = cmd.getOptionValue("h");
			}
			if (cmd.hasOption("p")) {
				port = cmd.getOptionValue("p");
			}
			if (!cmd.hasOption("u")) {
				System.err.println("Missing required argument: url");
				return;
			} else {
				url = cmd.getOptionValue("u");
			}
			if (cmd.hasOption("l")) {
				local = true;
			}
		} catch (ParseException pe) {
			System.out.println("Error parsing command-line arguments");
			HelpFormatter formatter = new HelpFormatter();
			System.exit(1);
		}

		if (local) {
			ChromeOptions options = new ChromeOptions();
			// @formatter:off
			for (String optionAgrument : (new String[] {
					"--allow-insecure-localhost",
					"--allow-running-insecure-content", 
					"--browser.download.folderList=2",
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
					"--disable-blink-features=AutomationControlled", 
					"--disable-default-app", 
					"--disable-dev-shm-usage",
					"--disable-extensions", 
					"--disable-gpu",
					"--disable-infobars", 
					"--disable-in-process-stack-traces",
					"--disable-logging", 
					"--disable-notifications", 
					"--disable-popup-blocking",
					"--disable-save-password-bubble", 
					"--disable-translate", 
					"--disable-web-security",
					"--enable-local-file-accesses", 
					"--headless", 
					"--ignore-certificate-errors",
					"--ignore-ssl-errors=true", 
					"--log-level=3", 
					"--no-proxy-server", 
					"--no-sandbox",
					"--output=/dev/null",
					"--ssl-protocol=any", 
					"--user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0", 
				})) {
				// @formatter:on
				options.addArguments(optionAgrument);
			}
			options.addArguments();
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("os.name").toLowerCase().contains("windows")
							? Paths.get(System.getProperty("user.home")).resolve("Downloads")
									.resolve("chromedriver.exe").toAbsolutePath().toString()
							: new File("/usr/local/bin/chromedriver").exists()
									? "/usr/local/bin/chromedriver"
									: Paths.get(System.getProperty("user.home"))
											.resolve("Downloads").resolve("chromedriver")
											.toAbsolutePath().toString());
			driver = new ChromeDriver(options);
		} else {
			final DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setBrowserName(browser);

			capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
			String hub = String.format("http://%s:%s/wd/hub", host, port);
			if (debug) {
				System.err.println("launching browser on hub " + hub);
			}
			driver = new RemoteWebDriver(new URL(hub), capabilities);
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Go to URL
		System.err.println("get url: " + url);
		driver.get(url);

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
