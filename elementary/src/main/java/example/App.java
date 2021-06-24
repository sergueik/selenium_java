package example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class App {

	private static boolean debug = true;
	private static String browser = "chrome";
	private static String port = "4444";
	private static String host = "localhost";
	private static String url = null;

	private static Options options = new Options();

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
		} catch (ParseException pe) {
			System.out.println("Error parsing command-line arguments");
			HelpFormatter formatter = new HelpFormatter();
			System.exit(1);
		}

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
