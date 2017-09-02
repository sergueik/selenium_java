package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestWithData {

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public Alert alert;
	public JavascriptExecutor js;
	public TakesScreenshot screenshot;

	private static String osName;

	public int scriptTimeout = 5;
	public int flexibleWait = 120;
	public int implicitWait = 1;
	public long pollingInterval = 500;

	public String baseURL = "about:blank";

	private String username = "you%40yourdomain.com";
	private String authkey = "yourauthkey";
	private Sheet mySheet = getSpreadSheet();

	@BeforeSuite
	public void beforeSuite() {
		/*
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", "Chrome");	// pulls the latest version
		caps.setCapability("platform", "Windows 10");	// to specify version, add setCapability("version", "desired version")
		try { 
		driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey +"@hub.crossbrowsertesting.com:80/wd/hub"), caps);
		} catch ( MalformedURLException e) {
		 // abort
		}
		*/

		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("enableNetwork", "true");
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("allow-running-insecure-content");
		options.addArguments("allow-insecure-localhost");
		options.addArguments("enable-local-file-accesses");
		options.addArguments("disable-notifications");
		// options.addArguments("start-maximized");
		options.addArguments("browser.download.folderList=2");
		options.addArguments(
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
		options.addArguments("browser.download.dir=" + downloadFilepath);
		// options.addArguments("user-data-dir=/path/to/your/custom/profile");
		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capabilities);
		mySheet = getSpreadSheet();
	}

	public Sheet getSpreadSheet() {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "target\\classes\\Test.xlsx");

		FileInputStream inputStream = null;
		Workbook wb = null;
		try {
			inputStream = new FileInputStream(file);
			wb = WorkbookFactory.create(inputStream);
			System.out.println(wb.toString());
		} catch (IOException ex) {
			System.out.println("Error Message " + ex.getMessage());
		} catch (InvalidFormatException e) {
			System.out.println("Invalid File format!");
		}

		Sheet mySheet = wb.getSheet("MySheet");

		return mySheet;
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		String methodName = method.getName();
		System.out.println("Test Name: " + methodName + "\n");
		driver.get("http://crossbrowsertesting.github.io/login-form.html");
	}

	@AfterMethod
	public void afterMethod() {
		driver.get("about:blank");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void failedLoginPage() {

		String user = mySheet.getRow(0).getCell(0).toString();
		String pass = mySheet.getRow(0).getCell(1).toString();

		// the first time around, it should not work!
		driver.findElement(By.name("username")).sendKeys(user);

		// then by entering the password
		System.out.println("Entering password");
		driver.findElement(By.name("password")).sendKeys(pass);

		// then by clicking the login button
		System.out.println("Logging in");
		driver.findElement(By.cssSelector("div.form-actions > button")).click();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(
				By.xpath("/html/body/div/div/div/div[1]"),
				"Username or password is incorrect"));
	}

	@Test
	public void loginPage() {

		String user = mySheet.getRow(1).getCell(0).toString();
		String pass = mySheet.getRow(1).getCell(1).toString();

		driver.findElement(By.name("username")).sendKeys(user);

		// then by entering the password
		System.out.println("Entering password");
		driver.findElement(By.name("password")).sendKeys(pass);

		// then by clicking the login button
		System.out.println("Logging in");
		driver.findElement(By.cssSelector("div.form-actions > button")).click();

		// let's wait here to ensure the page has loaded completely
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"logged-in-message\"]/h2")));

		String welcomeMessage = driver
				.findElement(By.xpath("//*[@id=\"logged-in-message\"]/h2")).getText();
		Assert.assertEquals("Welcome tester@crossbrowsertesting.com",
				welcomeMessage);

		System.out.println("TestFinished");
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
}
