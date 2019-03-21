### Info

This directory contains the [browsermob proxy](https://bmp.lightbody.net/) (BMP)
[study example](https://github.com/lightbody/browsermob-proxy).

To run,
```cmd
mvn -Dmaven.test.skip=true -DskipTests=true package install
set DEBUG_SLEEP=120000
set DEBUG=true
set BROWSER=chrome
java -cp target\app-0.4-SNAPSHOT.jar;target\lib\* com.github.sergueik.bmp.App
```
or run the provided batch file
```cmd
call test.cmd
```

#### Note

The main __BMP__ artifactId is different for 2.0.x and and for 2.1.x versions of the jar.
The information in https://github.com/lightbody/browsermob-proxy/releases may not be correct
Referencing incorrectly named artifact in the `pom.xml` would lead to the error

```sh
Could not resolve dependencies for project com.mycompany.app:app:jar:1.1-SNAPSHOT:
Failure to find net.lightbody.bmp:browsermob-proxy:jar:2.1.0-beta-5
```

The profile `old_bmp` was added to the project `pom.xml` to allow building against old version:
```sh
mvn -Pold_bmp clean package install
```
The BMP version __2.12__ works with Selenium __2.53__  but not with Selenim __3.13.0__. The BMP  version __2.15__ works with Selenium __3.13.0__, but does not work with Selenium __2.53__. Overall the first combination appears more stable then the second. Creation of tags andd branches is a work in progress.


### References (in russian)

* Example projects
  * [sskorol/selenium-camp-samples](https://github.com/sskorol/selenium-camp-samples)
  * [barancev/selen-confetqa-2013](https://github.com/barancev/selen-confetqa-2013)
* [presentation](habrahabr.ru/post/209752/)
* [forum](http://automated-testing.info/t/browsermob-proxy-java-webdriver-pomogite-zapustit-prostejshij-test/4531/24)
* blogs
  * [selenium-browser-proxy](http://sidelnikovmike.blogspot.ru/2016/04/selenium-browser-proxy.html)
  * [BrowserMob Embedded Mode Integration with Webdriver/Selenium Java](https://techblog.dotdash.com/selenium-browsermob-integration-c35f4713fb59)
  * [browsermobproxy-selenium-perfomance](http://internetka.in.ua/browsermobproxy-selenium-perfomance/)
* misc
  * https://groups.google.com/forum/#!topic/webdriver/aQl5o0TorqM
  * http://amormoeba.blogspot.com/2014/02/how-to-use-browser-mob-proxy.html
  * http://www.assertselenium.com/browsermob-proxy/performance-data-collection-using-browsermob-proxy-and-selenium/
  * [HAR reader](https://github.com/sdstoehr/har-reader)

### Author

[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
