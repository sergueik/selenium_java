### About
This project exercises Selenium driver [logging](https://code.google.com/p/selenium/wiki/Logging) functionality.


### Usage
The browser should be identified at `testng.config` level:
```
<suite name="Suite 1" verbose="10">
  <parameter name="selenium.host" value="localhost"/>
  <parameter name="selenium.port" value="4444"/>
  <parameter name="selenium.browser" value="firefox"/>
  <parameter name="selenium.run" value="remote"/>

  <test name="Selenium Browser Log Capture Test">
    <classes>
      <class name="com.mycompany.app.AppTest"/>
      <methods>
        <include name="LoggingTest"/>
      </methods>
    </classes>
  </test>
</suite>

```
Currently Chrome and Firefox are supported. The verbosity of logs captured varies with the browser. Example:

```
Sat Jan 09 13:02:16 EST 2016 INFO http://www.i.cdn.cnn.com/.a/1.231.2/js/cnn-header-second.min.js 12:6843 [AmazonDirectMatchBuy]
```


### See Also
 - [Log entries](https://logentries.com/doc/java/)
 - [ChromeDriver Capabilities ](https://sites.google.com/a/chromium.org/chromedriver/capabilities)
 - [Capturing Browser logs with-Selenium] (http://stackoverflow.com/questions/25431380/capturing-browser-logs-with-selenium)
 - [klepikov/Test.java](https://gist.github.com/klepikov/5457750)
 - [discussion of missing C# bindings for WebDriver logging](https://code.google.com/p/selenium/issues/detail?id=6832)
 - interacing with JSErrorCollector.xpi [java](https://github.com/mguillem/JSErrorCollector) client
 - interacing with JSErrorCollector.xpi [.net](https://github.com/protectedtrust/JSErrorCollector.NET) client
 * [intro to Selenium Webdriver logging](https://comaqa.gitbook.io/selenium-webdriver-lectures/selenium-webdriver.-problemnye-momenty/loggirovanie-v-selenium-webdriver)(in Russian)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

