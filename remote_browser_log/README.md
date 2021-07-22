### Info



logging
### Usage 
Sample code in the test
```java

	DesiredCapabilities capabilities;
		LoggingPreferences loggingPreferences = new LoggingPreferences();
		String hub = "http://" + seleniumHost + ":" + seleniumPort + "/wd/hub";
			loggingPreferences.enable(LogType.BROWSER, Level.ALL);
			loggingPreferences.enable(LogType.CLIENT, Level.INFO);
			loggingPreferences.enable(LogType.SERVER, Level.INFO);
				capabilities.setBrowserName("chrome");
				capabilities.setCapability(CapabilityType.LOGGING_PREFS,
						loggingPreferences);
				driver = new RemoteWebDriver(
						new URL("http://" + seleniumHost + ":" + seleniumPort + "/wd/hub"),
						capabilities);

		driver.get(base_url);
		String className = "control-block";
		WebElement element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.className(className)));
		assertThat(element, notNullValue());
		final String script = "console.log('Test from client: ' + arguments[0].innerHTML); return";
		executeScript(script, element);

LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
	}

```

results in
```text
Discovered supported log types: [server, browser, driver, client]
Analyze log After Test:
time stamp: Wed Jul 21 20:23:27 EDT 2021        log level: FINE message: http://localhost:4444/wd/hub/static/resource/client.js 2465:8 " [  0.118s] [remote.ui.C
lient] Retrieving server status..."
time stamp: Wed Jul 21 20:23:27 EDT 2021        log level: FINE message: http://localhost:4444/wd/hub/static/resource/client.js 2465:8 " [  0.351s] [remote.ui.C
lient] Refreshing sessions..."
time stamp: Wed Jul 21 20:23:27 EDT 2021        log level: SEVERE       message: http://localhost:4444/favicon.ico - Failed to load resource: the server responded with a status of 404 (Not Found)
time stamp: Wed Jul 21 20:23:28 EDT 2021        log level: INFO message: console-api 371:40 "Test from client: \u003Cbutton>Create Session\u003C/button>&nbsp;&nbsp;|&nbsp;&nbsp;\u003Cbutton>Refresh Sessions\u003C/button>"
Analyze log After Suite:
```



### See Also


   * [C# example](https://stackoverflow.com/questions/50986959/how-to-set-up-performance-logging-in-seleniumwebdriver-with-chrome)
   * [standalone repository successor](https://github.com/sergueik/remote_browser_logs), merge pending


