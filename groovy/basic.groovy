@Grapes([
    @Grab(group = 'com.google.code.gson', module = 'gson', version = '2.4'),
    @Grab(group = 'org.apache.httpcomponents', module = 'httpcore', version = '4.3.2'),
    @Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.3'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-chrome-driver', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-firefox-driver', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-remote-driver', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-support', version = '[2.43.0,2.44.0)'),
    @GrabExclude('xerces:xercesImpl'),
    @GrabExclude('xml-apis:xml-apis'),
])

// default 
// ~/.groovy/grapes/org.gebish/geb-core/jars/geb-core-0.9.3.jar

import groovy.grape.Grape

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogEntry
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import java.util.concurrent.TimeUnit
import java.util.logging.Level
/*
to run in the node witn xvfb/selenium/java/groovy stack
export GROOVY_HOME=/usr/local/groovy-2.4.5
export PATH=$PATH:$GROOVY_HOME/bin
export PATH=$PATH:/home/vncuser/selenium/firefox
export DISPLAY=:99
groovy basic.groovy
*/
System.setProperty('webdriver.chrome.driver', 'c:/java/selenium/chromedriver.exe')
def capabilities = DesiredCapabilities.chrome()
def logging_preferences = new LoggingPreferences()
logging_preferences.enable(LogType.BROWSER, Level.ALL)
capabilities.setCapability(CapabilityType.LOGGING_PREFS, logging_preferences)
def driver = new ChromeDriver(capabilities)
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

driver.get('http://www.google.com')
def element = driver.findElement(By.name('q'))
element.sendKeys('Cheese!')
element.submit()
println('Page title is: ' + driver.getTitle())
(new WebDriverWait(driver, 10)).until(new ExpectedCondition() {
     public Boolean apply(WebDriver d) {
 return d.getTitle().toLowerCase().startsWith('cheese!')
     }
})
println('Page title is: ' + driver.getTitle())
driver.quit()

