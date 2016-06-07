package org.henrrich.jpagefactory.example.supercalculator;

import com.jprotractor.NgWebDriver;
import org.henrrich.jpagefactory.Channel;
import org.henrrich.jpagefactory.JPageFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by henrrich on 23/05/2016.
 */
public class SuperCalculatorTest {

    private NgWebDriver ngDriver;
    private String baseUrl;

    // change this boolean flag to true to run on chrome emulator
    private boolean isMobile = false;

    private SuperCalculatorPage superCalculatorPage;

    @Before
    public void setUp() throws Exception {

        // change the chrome driver path below to chromedriver_linux32 or chromedriver_linux64 if you are on linux
        // change the chrome driver path below to chromedriver_mac32 if you are on mac
        System.setProperty("webdriver.chrome.driver", "C:\\henrrich\\jpagefactory\\src\\test\\resources\\chromedrivers\\chromedriver.exe");

        if (isMobile) {
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", "Google Nexus 5");
            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            chromeOptions.put("mobileEmulation", mobileEmulation);
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            // set ignoreSynchronization to true to be able to handle the page sync by ourselves instead of using waitForAngular call in JProtractor
            ngDriver = new NgWebDriver(new ChromeDriver(capabilities), true);
        } else {
            // set ignoreSynchronization to true to be able to handle the page sync by ourselves instead of using waitForAngular call in JProtractor
            ngDriver = new NgWebDriver(new ChromeDriver(), true);
        }

        baseUrl = "http://juliemr.github.io/protractor-demo/";
        ngDriver.get(baseUrl);
        ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        superCalculatorPage = new SuperCalculatorPage();

        Channel channel = Channel.WEB;
        if (isMobile) {
            channel = Channel.MOBILE;
        }
        JPageFactory.initElements(ngDriver, channel, superCalculatorPage);
    }

    @Test
    public void testShouldAddOneAndTwo() throws Exception {
        superCalculatorPage.add("1", "2");
        Assert.assertTrue("Result is not 3!", superCalculatorPage.getLatestResult().equals("3"));
    }

    @Test
    public void testShouldTwoTimesThree() throws Exception {
        superCalculatorPage.times("2", "3");
        Assert.assertTrue("Result is not 6!", superCalculatorPage.getLatestResult().equals("6"));
    }

    @Test
    public void testShouldHaveAHistory() throws Exception {
        superCalculatorPage.add("1", "2");
        superCalculatorPage.add("3", "4");
        Assert.assertTrue("Should have 2 history!", superCalculatorPage.getNumberOfHistoryEntries() == 2);

        superCalculatorPage.add("5", "6");
        Assert.assertTrue("Should have 2 history!", superCalculatorPage.getNumberOfHistoryEntries() == 3);

    }

    @After
    public void tearDown() throws Exception {
        ngDriver.quit();
    }


}
