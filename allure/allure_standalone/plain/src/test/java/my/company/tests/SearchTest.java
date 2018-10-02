package my.company.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

// import my.company.steps.WebDriverSteps;

/**
 * based on discussion on
 * https://automated-testing.info/t/maven-allure-directory-allure-results-not-found/21598/16 
 * (in Russian)
 */
public class SearchTest {

    //  private static WebDriverSteps steps;
        public RemoteWebDriver driver;
        private static String osName = getOSName();

        private static final String downloadFilepath = String.format("%s\\Downloads",
                        osName.equals("windows") ? System.getenv("USERPROFILE")
                                        : System.getenv("HOME"));

        @SuppressWarnings("deprecation")
        @Before
        public void setUp() throws Exception {

                System.setProperty("webdriver.gecko.driver", osName.equals("windows")
                                ? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
                                : /* String.format("%s/Downloads/geckodriver", System.getenv("HOME"))*/
                                Paths.get(System.getProperty("user.home")).resolve("Downloads")
                                                .resolve("geckodriver").toAbsolutePath().toString());
                System.setProperty("webdriver.firefox.bin",
                                osName.equals("windows")
                                                ? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
                                                                .getAbsolutePath()
                                                : "/usr/bin/firefox");

                DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
                                Platform.ANY);

                // NOTE: setting legacy protocol to "true" leads to an error
                // Missing 'marionetteProtocol' field in handshake
                capabilities.setCapability("marionette", false);

                driver = new FirefoxDriver(capabilities);
                // steps = new WebDriverSteps(driver);
        }

/*
        @Test
        public void searchTest() throws Exception {
                steps.openMainPage();
                steps.search("Allure framework");
                steps.makeScreenShot();
                steps.quit();
        }
*/
        @Ignore
        @Test
        public void testChromeDriver() throws Exception {
                assertThat(driver, notNullValue());
        }

        // @Ignore
        @Test
        public void testDownload() throws Exception {
                driver.get("http://www.seleniumhq.org/download/");
                driver.findElement(By.linkText("32 bit Windows IE")).click();
                int downloadInterval = 1000;
                try {
                        Thread.sleep(downloadInterval);
                } catch (InterruptedException e) {
                        System.err.println("Exception (ignored): " + e.toString());
                }

                boolean fileExists = false;
                for (String filename : (new String[] {
                                "IEDriverServer_Win32_3.13.0.zip.crdownload",
                                "IEDriverServer_Win32_3.13.0.zip" })) {
                        String filePath = downloadFilepath + File.separator + filename;
                        System.err.println("Probing " + filePath);
                        if ((new File(filePath)).exists()) {
                                fileExists = true;
                                System.err.println("Found " + filePath);
                        }
                }
                assertThat(fileExists, is(true));
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

        // @Ignore
        @Test
        public void Test() {
                driver.navigate().to("https://ya.ru/");
                WebElement element = driver
                                .findElements(
                                                By.cssSelector("div.search2__button > button > span.button__text"))
                                .get(0);
                final String text = element.getAttribute("outerHTML");
                System.err.println("Text: " + text);
                Assert.assertEquals(element.getText(), "?????");
                assertThat(element.getText(), containsString("?????")); // quotes
        }

        @AfterClass
        public void teardown() {
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
}

