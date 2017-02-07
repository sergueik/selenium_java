### Info
The project practices embedding the  [madrobby/keymaster](https://github.com/madrobby/keymaster) into the page and testing it with Selenium.
Eventual plan is to merge the [keymaster.js](https://github.com/madrobby/keymaster/blob/master/keymaster.js) helper module into
[ElementSearch.js](https://github.com/dzharii/swd-recorder/blob/master/SwdPageRecorder/SwdPageRecorder.WebDriver/JavaScript/ElementSearch.js) to enable custom hotkeys in [dzharii/swd-recorder](https://github.com/dzharii/swd-recorder)


### Summary, Details

It is possible to test the planned changes to `ElementSearch.js` through WebDriver outside of SWD by injecting the `keymaster.js` into the target page:
```java
	private void runKeyMaster(WebElement element, Object... arguments) {
		try {
			final InputStream stream = KeyMasterTest.class.getClassLoader()
					.getResourceAsStream("keymaster.js");
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			String helperScript = new String(bytes, "UTF-8");
			String testScript =
      "key('o, enter, left', function(event, handler){ window.alert('o, enter or left pressed on target = ' + event.target.toString() +  ' srcElement = ' + event.srcElement.toString() + ' !');});"));"key('o, enter, left', function(){ window.alert('o, enter or left pressed!');});";
			ArrayList<String> scripts = new ArrayList<String>(
					Arrays.asList(helperScript, testScript));
			for (String script : scripts) {
				if (driver instanceof JavascriptExecutor) {
					JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
							.cast(driver);
					javascriptExecutor.executeScript(script);
				} else {
					throw new RuntimeException("Script execution failed.");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Cannot load helper script");
		}
	}
```

The only change made to `keymaster.js` to make it injectable was
```diff
< })(this);
---
> })(window);
```

Unfortunately this does not going to be useful in the Visual element locator because for keyboard events
the event object propereties will always contain reference to the page body instead of individual elements.

The Alert text will be:
![result](https://raw.githubusercontent.com/sergueik/selenium_java/master/keymaster/screenshots/capture1.png)

```
Accepted alert: o, enter or left pressed on
target = [object HTMLBodyElement]
srcElement = [object HTMLBodyElement] !
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
