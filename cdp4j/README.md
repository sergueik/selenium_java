### Info

This project exercises the [Chrome DevTools Protocol for Java](https://github.com/webfolderio/cdp4j) Selenium alternative API.

  * [ChromeDevTools/awesome-chrome-devtools](https://github.com/ChromeDevTools/awesome-chrome-devtools)
  * [cdp4j/javadoc](https://webfolder.io/cdp4j/javadoc/index.html)
  * [puppeteer online](https://try-puppeteer.appspot.com/)

The project is in active development, so please bookmark and check for updates.

### Status
The following code fragments familiar to a Selenium developer are implemented on top of __CDP__ and demonstrated:

* Iterating over and filtering of the set of elements returned, then perform further action with the specific members similar to Selenium `findElements`:
```java

		Launcher launcher = new Launcher();
		SessionFactory factory = launcher.launch();
		session = factory.create();
		// install extensions
		session.installSizzle();
		session.useSizzle();
		session.clearCookies();
		session.clearCache();
		session.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34");
		session.navigate("https://webfolder.io");

    // examine navbar links
		String cssSelector = "#nav a";

		// Arrange
		session.waitUntil(o -> o.getObjectIds(cssSelector).size() > 0, 1000, 100);
		// Act
		String id = session.getObjectIds(cssSelector).stream().filter(_id ->
		((String) session.getPropertyByObjectId(_id, "href")).matches(".*about.html$")).collect(Collectors.toList()).get(0);
		// Assert
		Assert.assertEquals(session.getPropertyByObjectId(id, "href"),
				"https://webfolder.io/about.html");
		Assert.assertEquals(session.getPropertyByObjectId(id, "innerHTML"),
				"About");
```
* Highlight the element of interest by applying a border style:
```java
	protected void highlight(String selectorOfElement, Session session,
			long interval) {
		String objectId = session.getObjectId(selectorOfElement);
		Integer nodeId = session.getNodeId(selectorOfElement);
		CallFunctionOnResult functionResult = null;
		RemoteObject result = null;
		executeScript("function() { this.style.border='3px solid yellow'; }",
				selectorOfElement);
		sleep(interval);
		executeScript("function() { this.style.border=''; }", selectorOfElement);
	}

	protected Object executeScript(Session session, String script,
			String selectorOfElement) {
		if (!session.matches(selectorOfElement)) {
			return null;
		}
		String objectId = session.getObjectId(selectorOfElement);
		Integer nodeId = session.getNodeId(selectorOfElement);
		CallFunctionOnResult functionResult = null;
		RemoteObject result = null;
		Object value = null;
		try {
			functionResult = session.getCommand().getRuntime().callFunctionOn(script,
					objectId, null, null, null, null, null, null, nodeId, null);
			if (functionResult != null) {
				result = functionResult.getResult();
				if (result != null) {
					value = result.getValue();
					session.releaseObject(result.getObjectId());
				}
			}
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.getMessage());
		}
		// System.err.println("value: " + value);
		return value;
	}

```
* Wait until certain element is [visible](https://stackoverflow.com/questions/1343237/how-to-check-elements-visibility-via-javascript) (similar to Selenium `ExpectedConditions`):
```java
	protected boolean isVisible(String selectorOfElement) {
		return (boolean) (session.matches(selectorOfElement)
				&& (boolean) executeScript(
						"function() { return(this.offsetWidth > 0 || this.offsetHeight > 0); }",
						selectorOfElement));
	}

    session.navigate("https://www.google.com/gmail/about/#")
		session.waitUntil(o -> isVisible(selector), 1000, 100);

```
* [Click on element](https://www.w3schools.com/jsref/met_html_click.asp)- the [Session click(String selector) method](https://webfolder.io/cdp4j/javadoc/io/webfolder/cdp/session/Mouse.html#click-java.lang.String-) appears to be unreliable)
```java
	protected void click(String selector) {
		executeScript(session, "function() { this.click(); }", selector);
	}
```

### TODO

The following code fragments are yet unclear how to implement:

* Finding the target element by applying `findElement(s)` to a certain element found earlier.
* Alerts

### See Also
  * [http://suvian.in/selenium](http://suvian.in/selenium)
  * [http://www.way2automation.com](http://www.way2automation.com)
  * [kw-udon/cdp4j-demo](https://github.com/kw-udon/cdp4j-demo)
  * [ChromeDevTools/debugger-protocol-viewer](https://github.com/ChromeDevTools/debugger-protocol-viewer)
  * [GoogleChrome/puppeteer](https://github.com/GoogleChrome/puppeteer)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
