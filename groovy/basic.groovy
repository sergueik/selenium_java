@Grapes([
    @Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.3'),
    @Grab(group = 'com.google.code.gson', module = 'gson', version = '2.4'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-firefox-driver', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-chrome-driver', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-remote-driver', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.seleniumhq.selenium', module = 'selenium-support', version = '[2.43.0,2.44.0)'),
    @Grab(group = 'org.apache.httpcomponents', module = 'httpcore', version = '4.3.2'),
    @GrabExclude('xml-apis:xml-apis'),
    @GrabExclude('xerces:xercesImpl')
])

// http://stackoverflow.com/questions/28153755/intellij-and-groovy-grab-dependencies 

import groovy.grape.Grape
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.By

def driver = new FirefoxDriver()
driver.get('http://www.google.com')
def element = driver.findElement(By.name('q'))
element.sendKeys('Cheese!')
element.submit()
println('Page Title is: ' + driver.getTitle())
(new WebDriverWait(driver, 10)).until(new ExpectedCondition() {
     public Boolean apply(WebDriver d) {
 return d.getTitle().toLowerCase().startsWith('cheese!')
     }
});
println('Page Title is: ' + driver.getTitle())
driver.quit()

