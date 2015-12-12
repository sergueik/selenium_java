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
// exact versions:
import groovy.transform.Field
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
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions

import java.text.*

import java.util.concurrent.TimeUnit
import java.util.logging.Level

// global variables

@Field WebDriver driver
@Field JavascriptExecutor javascriptExecutor
@Field int implicit_wait_interval = 1
@Field int flexible_wait_interval = 5
@Field long wait_polling_interval = 500
@Field WebDriverWait wait
@Field Actions actions
@Field WebElement element
@Field String password =  ''

@Field long highlight_interval = 1000

def printErr = System.err.&println
def highlight(WebElement element) {
  wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
  wait.until(ExpectedConditions.visibilityOf(element));
  javascriptExecutor.executeScript("arguments[0].style.border='3px solid yellow'", element);
  Thread.sleep(highlight_interval);
  javascriptExecutor.executeScript("arguments[0].style.border=''", element);
}

def driver_path =  (System.properties['os.name'].toLowerCase().contains('windows')) ? 'c:/java/selenium/chromedriver.exe' : '/home/vncuser/selenium/chromedriver/chromedriver'

def cli = new CliBuilder(usage: 'showdate.groovy -[hp]')
import org.apache.commons.cli.Option

cli.with {
  h longOpt: 'help', 'Show usage information'
  p longOpt: 'password', args: 1, argName: 'password', 'password'
}
def options = cli.parse(args)
if (!options) {
  return
}
if (options.h) {
  cli.usage()
  return
}
if (options.p){
  password = options.p
}

if (password.length() == 0 ) {
  cli.usage()
  return
}
System.setProperty('webdriver.chrome.driver', driver_path )
def capabilities = DesiredCapabilities.chrome()
def logging_preferences = new LoggingPreferences()
logging_preferences.enable(LogType.BROWSER, Level.ALL)
capabilities.setCapability(CapabilityType.LOGGING_PREFS, logging_preferences)
driver = new FirefoxDriver(capabilities)
wait = new WebDriverWait(driver, flexible_wait_interval )
actions = new Actions(driver)

if (driver instanceof JavascriptExecutor) {
  javascriptExecutor=(JavascriptExecutor)driver
}
else {
  throw new RuntimeException("JavascriptExecutor interface.")
}
driver.manage().timeouts().implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS)
base_url = 'http://way2automation.com/way2auto_jquery/index.php'
driver.get(base_url)

def signup_css_selector = 'div#load_box.popupbox form#load_form a.fancybox[href="#login"]'
element = driver.findElement(By.cssSelector(signup_css_selector))

// show signup link
actions.moveToElement(element).build().perform()
highlight(element)


element.click()

def login_username_selector = 'div#login.popupbox form#load_form input[name="username"]'
element = driver.findElement(By.cssSelector(login_username_selector))
highlight(element)
element.sendKeys('sergueik')

login_password_selector = 'div#login.popupbox form#load_form input[type="password"][name="password"]'
element = driver.findElement(By.cssSelector(login_password_selector))
highlight(element)
element.sendKeys(password)

login_button_selector = 'div#login.popupbox form#load_form [value="Submit"]'
element = driver.findElement(By.cssSelector(login_button_selector))
highlight(element)
element.submit()

// custom wait while Login lightbox is visible 
// applying to inverse of 
// (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('div#login.popupbox')))
// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html
// was 'until', but lacks 'while'
(new WebDriverWait(driver, 60)).until(new ExpectedCondition() {
  public Boolean apply(WebDriver d) {
     return (d.findElements(By.cssSelector('div#login.popupbox')).size() == 0)
  }
})


protractor_test_base_url = 'http://www.way2automation.com/protractor-angularjs-practice-website.html'
driver.get(protractor_test_base_url)

println('Angular Exercise Page title is: ' + driver.getTitle())

exercise_css_selector = "div.row div.linkbox ul.boxed_style li a[href='http://www.way2automation.com/angularjs-protractor/checkboxes']"
element = driver.findElement(By.cssSelector(exercise_css_selector))
highlight(element)
// prevent opening a new tab from target="_blank"
javascriptExecutor.executeScript("arguments[0].setAttribute('target', '')",element)
actions.moveToElement(element).click().build().perform()

// plain Selenium

elements_css_selector = 'li[ng-repeat="prod in cat.products"]'
List<WebElement> elements_collection = driver.findElements(By.cssSelector(elements_css_selector))
for(WebElement element_item : elements_collection) {
  println(element_item.getText())
  actions.moveToElement(element_item).build().perform()
  highlight(element_item)
}

Thread.sleep(10000)
driver.quit()

