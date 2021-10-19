### Info

this direcitory contains a replica of [selenum grid 4.0.0 demo project](https://github.com/melih91/selenium-grid-demo). the latest selenium-server or selenium-server-standalone release __4__ available on https://selenium-release.storage.googleapis.com 
is [selenium-server-4.0.0-beta-4.jar](https://selenium-release.storage.googleapis.com/4.0-beta-4/selenium-server-4.0.0-beta-4.jar) (dated June 2021)
and maven central only has [4.0.0-alpha-2](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-server/4.0.0-alpha-2) from July 2019.

The Selenium Server __4.0.0__ can be downloaded from https://www.selenium.dev/downloads/ [downloads](https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.0.0/selenium-server-4.0.0.jar)
and copied to `repo` directory

### Usage

Use Selenium fluxbox Vagrantfile, deploy manually via scp
https://mvnrepository.com/repos/central/org/seleniumhq/selenium/selenium-server/4.0.0/selenium-server-4.0.0.pom -> [Help 1]

on Windows
```cmd
move /y selenium-server-4.0.0.jar %USERPROFILE%\DOWNLOADS
cd %USERPROFILE%\DOWNLOADS
java -jar selenium-server-4.0.0.jar standalone
```
on Linux

```sh
mv selenium-server-4.0.0.jar ~/Downloads
cd ~/Downloads
java -jar selenium-server-4.0.0.jar standalone
```

* test local driver
```java
mvn clean test
```

* test through grid in standalone mode
```java
mvn -Pgrid clean test
```

The test accesses `devTools` and performs `Browser.getVersion` [API call](https://chromedevtools.github.io/devtools-protocol/tot/Browser/#method-getVersion):
```java
GetVersionResponse response = devTools.send(Browser.getVersion());
response.getUserAgent();
logger.info("Browser Version : " + response.getProduct() + "\t"
    + "Browser User Agent : " + response.getUserAgent() + "\t"
    + "Browser Protocol Version : " + response.getProtocolVersion() + "\t"
    + "Browser JS Version : " + response.getJsVersion());
```
when the `devTools` construction was not successful, test just logs the exception and fails shortly after with an `java.lang.NullPointerException` shortly after

### See Also

 * selenium 4 grid [documentation](https://www.selenium.dev/documentation/grid/)
 * https://www.browserstack.com/guide/selenium-grid-4-tutorial
 * https://www.lambdatest.com/blog/selenium-grid-4-tutorial-for-distributed-testing/

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

