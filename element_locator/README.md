### Info


This directory contains a replica of element locator grabber project
[rahulrathore44/WebDriverElementLocator](https://github.com/rahulrathore44/WebDriverElementLocator)


### Test

* copy PhantomJS standalone app into `src/main/resources/driver/phantomjs.exe`.
* install the app
```cmd
mvn install
```
* run the main class:
```cmd
java -cp target\WebDriverElementLocator-0.0.1-SNAPSHOT.jar;target\lib\* com.driver.locator.MainClass
```
result in `src/main/resources/json` (depends on `src/main/resouces/configfile/config.properties`
