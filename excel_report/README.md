### Info
This directory contains a replca of [TestNG Excel Report Generator](https://github.com/rahulrathore44/ExcelReportGenerator) project. 

This java app converts `testng-result.xml`, that is normally found in `target/surefire-reports` into an Excel 2003 report with columns

|column           |data                        | 
| ----------------|:--------------------------:|
|Test Case Name   |package, class, method name | 
|Status           |pass or fail                |
|Exception        |exceptions thrown           |
|Exception Message|stack trace                 |

to run the formatter,
```sh
mvn clean install
java -jar target/TestNgToExcel-1.0.7-jar-with-dependencies.jar testngxmlfiles\testng-results.xml
```
or
```cmd
mvn clean test package
java -cp target\TestNgToExcel-1.0.7.jar;target\lib\* com.xls.report.main.MainClass testngxmlfiles\testng-results.xml
```

### See also

* [autotest.report.excel](https://github.com/LinuxSuRen/autotest.report.excel)
* [excel-reports-using-testng blog](https://blog.testproject.io/2016/04/17/excel-reports-using-testng/)
* [PhoenixAutotest WebUI Automated Test Framework reports](http://surenpi.com/2017/06/21/autotest_report/)
* [custom Reporting with TestNG](https://www.baeldung.com/testng-custom-reporting)
