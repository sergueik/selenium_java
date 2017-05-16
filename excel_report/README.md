### Info
This directory contains a clone of [TestNG Excel Report Generator](https://github.com/rahulrathore44/ExcelReportGenerator) project. 

This java api converts `testng-result.xml` to an excel report with columns

|column           |data                        | 
| ----------------|:--------------------------:|
|Test Case Name   |package, class, method name | 
|Status           |pass or fail                |
|Exception        |exceptions thrown           |
|Exception Message|stack trace                 |

to run the formatter,
```
mvn clean install
java -jar target\TestNgToExcel-1.0.7-jar-with-dependencies.jar testngxmlfiles\testng-results.xml
```
or
```
mvn clean test package
java -cp target\TestNgToExcel-1.0.7.jar;target\lib\* com.xls.report.main.MainClass testngxmlfiles\testng-results.xml
```

