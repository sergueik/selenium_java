Info
----

Resources
---------

Upload
------
- [bare bones implementation](https://saucelabs.com/resources/articles/selenium-file-upload)
- [bare bones implementation] http://selenium2advanced.blogspot.com/2012/07/how-to-upload-file-using-webdriver.html
- [additional services](http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.remote.LocalFileDetector)
- [additional services](http://www.programcreek.com/java-api-examples/index.php?source_dir=thucydides-master/thucydides-core/src/main/java/net/thucydides/core/pages/components/FileToUpload.java)
- [source](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/remote/LocalFileDetector.html)
- [forum](http://automated-testing.info/t/zagruzka-fajla-cherez-webdriver/3823/13)

Download settings in Firefox profile
------------------------------------

http://www.linkedin.com/groups/Downloading-data-Selenium-961927.S.5940784386541649921?view=&item=5940784386541649921&type=member&gid=961927&trk=eml-b2_anet_digest-hero-7-hero-disc-disc-0&midToken=AQGpqMsZZNuraw&fromEmail=fromEmail&ut=3J7egaOWfPe6w1
```javascript
FirefoxProfile firefoxProfile = new FirefoxProfile();
firefoxProfile.setPreference("browser.download.folderList",2);
firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
firefoxProfile.setPreference("browser.download.dir","c:\downloads");
firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");
WebDriver driver = new FirefoxDriver(firefoxProfile);
//new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
driver.navigate().to("http://www.myfile.com/hey.csv");
```
