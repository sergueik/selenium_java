###  JUnit-DataProviders [![BuildStatus](https://travis-ci.org/sergueik/junit-dataproviders.svg?branch=master)](https://https://travis-ci.org/sergueik/junit-dataproviders)

This project exercises the following data providers with [JUnitParams](https://github.com/Pragmatists/JUnitParams):

  * Excel 2003 OLE documents - a.k.a. Horrible SpreadSheet Format [org.apache.poi.hssf.usermodel.*)](http://shanmugavelc.blogspot.com/2011/08/apache-poi-read-excel-for-use-of.html)
  * Excel 2007 OOXML (.xlsx) - a.k.a. XML SpreadSheet Format [org.apache.poi.xssf.usermodel.*](http://howtodoinjava.com/2013/06/19/readingwriting-excel-files-in-java-poi-tutorial/)
  * OpenOffice SpreadSheet (.ods) [example1](http://www.programcreek.com/java-api-examples/index.php?api=org.jopendocument.dom.spreadsheet.Sheet), [example 2](http://half-wit4u.blogspot.com/2011/05/read-openoffice-spreadsheet-ods.html)
  * Custom JSON [org.json.JSON](http://www.docjar.com/docs/api/org/json/JSONObject.html)

  
### Usage

* Create the Excel 2003, Excel 2007 or Open Office Spreadsheet with test parameters e.g.

| ROWNUM |  SEARCH | COUNT |
|--------|---------|-------|
| 1      | junit   | 100   |

or a json file with the following structure:
```javascript
{
    "test": [{
        "keyword": "junit",
        "count": 101.0
    }, {
        "keyword": "testng",
        "count": 31.0
    }, {
        "keyword": "spock",
        "count": 11.0
    }],
    "other_test": [{
        "keyword": "not used",
        "count": 1.0
    }]
}

```
* Annotate the test methods in the following way:
```java
@Test
@ExcelParameters(filepath = "classpath:data_2007.xlsx", sheetName = "", type = "Excel 2007")
public void loadParamsFromEmbeddedExcel2007(double rowNum, String keyword, double count) {
	assumeTrue("search", keyword.matches("(?:junit|testng|spock)"));
	assertThat((int) count).isGreaterThan(0);
}
```
or
```java
@Test
@ExcelParameters(filepath = "file:src/test/resources/data_2003.xls", sheetName = "", type = "Excel 2003")
public void loadParamsFromFileExcel2003(double rownum, String keyword, double count) {
	assumeTrue("search", keyword.matches("(?:junit|testng|spock)"));
	assertThat((int) count).isGreaterThan(0);
}
```
or
```java
@Test
@ExcelParameters(filepath = "file:src/test/resources/data.ods", sheetName = "", type = "OpenOffice Spreadsheet")
public void loadParamsFromFileOpenOfficeSpreadsheel(double rowNum,
    String keyword, double count) {
  assumeTrue("search", keyword.matches("(?:junit|testng|spock)"));
  assertThat((int) count).isGreaterThan(0);
}

```
The `ExcelParametersProvider` class will read all columns from the Excel 2007, Excel 2003 or Open Office spreadhsheet and executes the test for every row of data.
The test developer is responsible for matching the test method argument types and the column data types.

To enable debug messages during the data loading, set the `debug` flag with `@ExcelParameters` attribute:
```java
@Test
@ExcelParameters(filepath = "classpath:data_2007.xlsx", sheetName = "", type = "Excel 2007", debug = true)
public void loadParamsFromEmbeddedExcel2007(double rowNum, String keyword,
    double count) {
  dataTest(keyword, count);
}
```

this will show the following:
```shell
0 = A ID
1 = B SEARCH
2 = C COUNT
Skipped the header
Cell Value: "1.0" class java.lang.Double
Cell Value: "junit" class java.lang.String
Cell Value: "104.0" class java.lang.Double
...
Loaded 3 rows
row 0 : [1.0, junit, 104.0]
...
```

NOTE: attributes for column selection and for converting every column type to `String` is *work in progress*.

### Maven Central

The snapshot versions are deployed to [https://oss.sonatype.org/content/repositories/snapshots/com/github/sergueik/junitparams/junit_params/](https://oss.sonatype.org/content/repositories/snapshots/com/github/sergueik/junitparams/junit_params/)
Release versions status: [pending](https://issues.sonatype.org/browse/OSSRH-36771?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel).

To use the snapshot version, add the following to `pom.xml`:
```xml
<dependency>
  <groupId>com.github.sergueik.junitparams</groupId>
  <artifactId>junit_params</artifactId>
  <version>0.0.7-SNAPSHOT</version>
</dependency>

<repositories>
  <repository>
    <id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
  </repository>
</repositories>
```

### Note
This project and the [testNg-DataProviders](https://github.com/sergueik/testng-dataproviders) - 
have large code overlap for processing spreadsheets and only differ in test methdod annotation details.

The Google Spreadsheet API portion of the project is the work in progress and ifin the absence of `client_secret.json` (not checked in) some tests fail please simply remove the
```shell
src/{main,test}/java/com/github/sergueik/utils
```


### See Also

 * Using Excel/Open Office / JSON as [testNG data providers](https://github.com/sergueik/testng-dataproviders)
 * [testng dataProviders](http://testng.org/doc/documentation-main.html#parameters-dataproviders)
 * [TNG/junit-dataprovider](https://github.com/TNG/junit-dataprovider) - a different TestNG-like dataprovider runner for JUnit and Allure.
 * [Pragmatists/JunitParams](https://github.com/Pragmatists/JUnitParams)
 * [junit contribution: test "assumes" annotation to build inter test dependencies](https://github.com/junit-team/junit.contrib/tree/master/assumes)
 * [how to use Google Sheets API to read data from Spreadsheet](http://www.seleniumeasy.com/selenium-tutorials/read-data-from-google-spreadsheet-using-api) 

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
