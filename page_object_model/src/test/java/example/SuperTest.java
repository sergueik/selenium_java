package example;

import static java.lang.System.err;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.EmailPopapPage;
import pages.EmailsListPage;
import pages.LoginPage;

import org.testng.Assert;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SuperTest {

	private WebDriver driver;

	private final String login = "test_testov2021@mail.ru";
	private final String password = "202120212021Tt";

	private static String browser = getPropertyEnv("webdriver.driver", "chrome");
	protected static String osName = getOSName();
	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome",
				osName.equals("windows") ? "chromedriver.exe" : "chromedriver");
		browserDrivers.put("firefox",
				osName.equals("windows") ? "geckodriver.exe" : "driver");
		browserDrivers.put("edge", "MicrosoftWebDriver.exe");
	}

	@BeforeMethod
	public void preCondition() {
		// создание объекта драйвера и авторизация
		System.err.println("Launching " + browser);
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					osName.equals("windows")
							? (new File("c:/java/selenium/chromedriver.exe"))
									.getAbsolutePath()
							: Paths.get(System.getProperty("user.home")).resolve("Downloads")
									.resolve("chromedriver").toAbsolutePath().toString());

		}
		System.setProperty("webdriver.chrome.driver",
				"src\\main\\resources\\chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		if (osName.equals("windows")) {
			// TODO: jni
			if (System.getProperty("os.arch").contains("64")) {
				String[] paths = new String[] {
						"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
						"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe" };
				// check file existence
				for (String path : paths) {
					File exe = new File(path);
					err.println("Inspecting browser path: " + path);
					if (exe.exists()) {
						chromeOptions.setBinary(path);
					}
				}
			} else {
				chromeOptions.setBinary(
						"c:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
			}
		}

		driver = new ChromeDriver(chromeOptions);
		driver.get("https://mail.ru/");
		LoginPage page = new LoginPage(driver);
		page.setLogin(login);
		page.setPassword(password);
		page.loginToMailRu(login, password);
	}

	@Test(enabled = false, priority = 3)
	public void createEmailAndSend() {
		EmailsListPage emailsListPage = new EmailsListPage(driver);
		//
		EmailPopapPage emailPopapPage = emailsListPage.clickCreateEmailButton();
		emailPopapPage.setTo(login); // does it have to be the same email?
		emailPopapPage.setSubject("Тестирование отправки письма");
		emailPopapPage.setTextArea("Пишу какой-то текст");
		emailsListPage = emailPopapPage.clickSendEmailButton();
		Assert
				.assertTrue("отправлено".equals(emailsListPage.getTextSendedMessage()));
	}

	@Test(enabled = false, priority = 2)
	public void deleteEmail() throws InterruptedException {
		EmailsListPage emailsListPage = new EmailsListPage(driver);
		//
		int emailsCountBefore = emailsListPage.getCountEmailList();
		emailsListPage.rightClickByEmail(emailsListPage.getEmailList(), 0);
		emailsListPage = emailsListPage.deleteEmail();
		Thread.sleep(1000);
		int emailsCountAfter = emailsListPage.getCountEmailList();
		Assert.assertTrue((emailsCountBefore - 1) == emailsCountAfter);
	}

	@Test(enabled = true, priority = 1)
	public void createDraftEmail() throws InterruptedException {
		EmailsListPage emailsListPage = new EmailsListPage(driver);
		//
		EmailPopapPage emailPopapPage = emailsListPage.clickCreateEmailButton();
		emailPopapPage.setTo(login); // does it have to be the same email?
		emailPopapPage.setSubject("Тестирование отправки письма");
		emailPopapPage.setTextArea("Пишу какой-то текст");
		emailPopapPage.clickSaveEmailButton();
		Thread.sleep(2000);
		Assert.assertTrue(emailPopapPage.getTextNotifyMessage()
				.contains("Сохранено в черновиках"));
		emailPopapPage.clickActionNotifyMessage();
		emailPopapPage.openDraft();
		Thread.sleep(2000);
		Assert.assertTrue(login.equals(emailPopapPage.getTextTo()));
		Assert.assertTrue(
				"Тестирование отправки письма".equals(emailPopapPage.getTextSubject()));
		Assert
				.assertTrue("Пишу какой-то текст".equals(emailPopapPage.getTextArea()));
		emailsListPage = emailPopapPage.clickSendEmailButton();
		Assert
				.assertTrue("отправлено".equals(emailsListPage.getTextSendedMessage()));
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null || value.length() == 0) {
			value = System.getenv(name);
			if (value == null || value.length() == 0) {
				value = defaultValue;
			}
		}
		return value;
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
}
