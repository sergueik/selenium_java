### Info

The project practices Java with selected Selenium test scenarios using the test practice sites:

  * [http://suvian.in/selenium](http://suvian.in/selenium)
  * [http://www.way2automation.com](http://www.way2automation.com)

  and misc. standalone examples found on

  * [https://www.skyscanner.com](https://www.skyscanner.com)
  * [https://embed.plnkr.co/](https://embed.plnkr.co/)
  * [https://datatables.net/extensions/rowgroup/examples/initialisation/customRow.html](https://datatables.net/extensions/rowgroup/examples/initialisation/customRow.html)
  * [https://select2.github.io/examples.html](https://select2.github.io/examples.html)
  * [http://phppot.com/demo/jquery-dependent-dropdown-list-countries-and-states/)
  * [http://jqueryui.com/datepicker/#buttonbar](http://jqueryui.com/datepicker/#buttonbar)
  * [http://demos.telerik.com/kendo-ui/grid/index](http://demos.telerik.com/kendo-ui/grid/index)
  * [http://antenna.io/demo/jquery-bar-rating/examples/](http://antenna.io/demo/jquery-bar-rating/examples/)

### Selenium 3 Testing

This project is also used to debug the issue with launching tests in Firefox 52 / Geckodriver 0.15
under Windows x86 or x64 under Selenium 3.2 / Maven / TestNg. No problem observed on Linux.

The following standalone class invocation works fine, browser launched and navigated succesfully:
```cmd
rd /s/q src\test\java
mvn clean install
java -cp target\app-1.2-SNAPSHOT.jar;c:\java\selenium\selenium-server-standalone-3.3.1.jar;target\lib\* com.mycompany.app.fireFoxBrowseStart
````
but fails without explicitly using the `selenium-server-standalone-3.3.1.jar`
- it is just a fragment of the with methods and fields converted to `static`.

However the same code fails when run by maven / testng:
```java
!
java.lang.RuntimeException: Cannot initialize Firefox driver
  at com.mycompany.app.SuvianTest.beforeSuite(SuvianTest.java:162)
  Caused by: org.openqa.selenium.firefox.NotConnectedException: Unable to connect
to host 127.0.0.1 on port 7055 after 45000 ms. Firefox console output:
```

### Links
 * [stackoverflow](http://stackoverflow.com/questions/30174546/selenium-filter-with-predicate)
 * [seleniumcapsules](https://github.com/yujunliang/seleniumcapsules)
 * [ahussan/Java8LamdaExpressionAndStreamAPITest](https://github.com/ahussan/Java8LamdaExpressionAndStreamAPITest)
 * [sskorol/selenium-camp-17](https://github.com/sskorol/selenium-camp-17)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
