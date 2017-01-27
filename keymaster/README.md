### Info
The project practices embedding the  [madrobby/keymaster](https://github.com/madrobby/keymaster) into the page and testing it with Selenium.
Eventual plan is to merge the [keymaster.js](https://github.com/madrobby/keymaster/blob/master/keymaster.js) helper module into 
[ElementSearch.js](https://github.com/dzharii/swd-recorder/blob/master/SwdPageRecorder/SwdPageRecorder.WebDriver/JavaScript/ElementSearch.js) to enable custom hotkeys in [dzharii/swd-recorder](https://github.com/dzharii/swd-recorder) 

The only change made to `keymaster.js` to make it injectable was
```diff
< })(this);
---
> })(window);
```

### Details
It is possible to test the planned changes to `ElementSearch.js` through WebDriver outside of SWD by injecting the `keymaster.js` into the target page:
```java

		String helperScript = getScriptContent("keymaster.js");
		try {
			final InputStream stream = KeyMasterTest.class.getClassLoader()
					.getResourceAsStream(helperScript);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			String helperScript =  new String(bytes, "UTF-8");
      String testScript = "key('o, enter, left', function(){ window.alert('o, enter or left pressed!');});";
      ArrayList<String> scripts = new ArrayList<String>(
          Arrays.asList(helperScript, testScript ));
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
			throw new RuntimeException(scriptName);
		}


```
The C# version is 100% along the same lines.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
