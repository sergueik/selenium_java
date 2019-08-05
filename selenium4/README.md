### Info

The project practices Java Selenium 4.0 beta release [ChromiumDriver](https://github.com/SeleniumHQ/selenium/blob/master/java/client/src/org/openqa/selenium/chromium/ChromiumDriver.java) executing cdp command (a `POST` request to `/session/$sessionId/goog/cdp/execute` with API-specific payload) feature.
that can be used to for example to invoke [setUserAgentOverride](https://chromedevtools.github.io/devtools-protocol/tot/Network#method-setUserAgentOverride) method during the test. 

The example method confirms that the user-agent as seen by https://www.whoishostingthis.com/tools/user-agent/, changes during the test
		  

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
