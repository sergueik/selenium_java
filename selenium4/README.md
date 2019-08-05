### Info

The project practices Java Selenium 4.0 beta release [ChromiumDriver](https://github.com/SeleniumHQ/selenium/blob/master/java/client/src/org/openqa/selenium/chromium/ChromiumDriver.java) for executing the Chrome DevTools Protocol (cdp) commands (a entirely different set of API communicated to the Chrome browser family via `POST` requests to `/session/$sessionId/goog/cdp/execute` with API-specific payload) feature.

### Example

One can call cdp protocol to invoke [setUserAgentOverride](https://chromedevtools.github.io/devtools-protocol/tot/Network#method-setUserAgentOverride) method and dynmically modify the `user-agent` header during the test:

```java
  import org.openqa.selenium.chrome.ChromeDriver;
  import org.openqa.selenium.chromium.ChromiumDriver;

  ChromiumDriver driver = new ChromeDriver();
  driver.get("https://www.whoishostingthis.com/tools/user-agent/");
  By locator = By.cssSelector(".user-agent");
  WebElement element = driver.findElement(locato);
  assertThat(element.getAttribute("innerText"), containsString("Mozilla"));
  Map<String, Object> params = Map.of(
    "userAgent", "python 2.7",
    "platform", "Windows"
  );
  driver.executeCdpCommand("Network.setUserAgentOverride", params);
  driver.navigate().refresh();
  sleep(100);

  element = driver.findElement(locator);
  assertThat(element.isDisplayed(), is(true));
  assertThat(element.getAttribute("innerText"), containsString("python 2.7"));

```
demonstrates that the user-agent is indeed changing
		
### Selenum release dependency

It appears that the critical dependency jar of this project, [selenium-chromium-driver](https://jcenter.bintray.com/org/seleniumhq/selenium/selenium-chromium-driver/) only available for Selenum release 4.x.

### See Also

  * [chrome devtools](https://github.com/ChromeDevTools/awesome-chrome-devtools)
 project
  * [puppeteer online](https://try-puppeteer.appspot.com/)
  * [GoogleChrome/puppeteer](https://github.com/GoogleChrome/puppeteer)
  * [ChromeDevTools/debugger-protocol-viewer](https://github.com/ChromeDevTools/debugger-protocol-viewer)
  * [standalond java cdp clienti](https://github.com/webfolderio/cdp4j) (commecial)
  * [cdp4j/javadoc](https://webfolder.io/cdp4j/javadoc/index.html)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
