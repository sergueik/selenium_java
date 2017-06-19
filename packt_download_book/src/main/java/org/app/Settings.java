package org.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.util.HashMap;

class Settings {
	private static Settings instance = null;
	private static final Logger LOG = LogManager.getLogger(Settings.class);
	private final String DRIVER_FILE_PATH = "chromedriver.exe";
	private final String DRIVER_NAME = "webdriver.chrome.driver";
	private final String PAGE_URL = "https://www.packtpub.com/packt/offers/free-learning";
	private String login = "";
	private String pass = "";
	private String downloadFolder = "";
	private WebDriver driver;
	private Actions builder;

	private Settings() {
	}

	static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	void unpackExeFromJar() {
		InputStream is = null;
		try {
			is = getClass().getResource("/chromedriver.exe").openStream();
			OutputStream os = new FileOutputStream("chromedriver.exe");
			LOG.info("Create temporary file chromedriver.exe");
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void deleteTempExe() {
		LOG.info("here");
		File file = new File("chromedriver.exe");
		if (file.exists()) {
			file.delete();
			LOG.info("Temp file deleted.");
		} else {
			LOG.warn("Delete failed.");
		}
	}

	public void setUp() {
		File file = new File(DRIVER_FILE_PATH);
		System.setProperty(DRIVER_NAME, file.getAbsolutePath());
		HashMap<String, Object> chromeOptions = new HashMap<>();
		chromeOptions.put("profile.default_content_settings.popups", 0);
		chromeOptions.put("download.default_directory", downloadFolder);
		chromeOptions.put("download.prompt_for_download", false);
		chromeOptions.put("--no-startup-window", true);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromeOptions);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);
		builder = new Actions(driver);
		LOG.info("Driver setUp complete.");
	}

	WebDriver getDriver() {
		return driver;
	}

	Actions getBuilder() {
		return builder;
	}

	String getPAGE_URL() {
		return PAGE_URL;
	}

	String getLogin() {
		return login;
	}

	String getPass() {
		return pass;
	}

	String getDownloadFolder() {
		return downloadFolder;
	}

	void setLogin(String login) {
		this.login = login;
	}

	void setPass(String pass) {
		this.pass = pass;
	}

	void setDownloadFolder(String downloadFolder) {
		this.downloadFolder = downloadFolder;
	}
}