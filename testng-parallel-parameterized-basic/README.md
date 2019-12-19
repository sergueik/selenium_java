### Info

This is a skeleton data provider parallel test suite replicated from
origin: [testng-browserstack](https://github.com/nidhimj22/testng-browserstack)

For paralllel thread run on Windows host the following `pom.xml` fragment may be necessary (not verified)
```xml
 <build>
   <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>${maven-surefire-plugin.version}</version>
        <configuration>
          <trimStackTrace>false</trimStackTrace>
          <encoding>${project.build.sourceEncoding}</encoding>
          <suiteXmlFiles>
            <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
          </suiteXmlFiles>
          <argLine>-Dfile.encoding=${project.build.sourceEncoding} -Dparallel=tests -DthreadCount=2 </argLine>
        </configuration>
        <executions>
          <execution>
            <id>unit-tests</id>
            <phase>test</phase>
            <goals>
              <goal>test</goal>
            </goals>
            <configuration>
              <skip>false</skip>
              <systemPropertyVariables>
                <webdriver.driver>${browser.name}</webdriver.driver>
                <selenium.version>${selenium.version}</selenium.version>
                <property.filepath>${property.filepath}</property.filepath>
              </systemPropertyVariables>
            </configuration>
          </execution>
        </executions>
```
by default the test run in mockup mode, withput really getting an individual browser session:

```sh
mvn test
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< example:parallel_test >------------------------
[INFO] Building app 0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ parallel
_test ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory C:\developer\sergueik\selenium_java\b
rowserstack\src\main\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.6.0:compile (default-compile) @ parallel_test
 ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @
parallel_test ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.6.0:testCompile (default-testCompile) @ paral
lel_test ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to C:\developer\sergueik\selenium_java\browserst
ack\target\test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.20:test (default-test) @ parallel_test ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-surefire-plugin:2.20:test (unit-tests) @ parallel_test ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
Creating browser driver for safari 6.0 MAC on thread:
Creating browser driver for firefox 43.0 MAC on thread:
Creating browser driver for chrome 51.0 WINDOWS on thread:
running test on thread: 1
running test on thread: 1
running test on thread: 1
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.642 s -
 in TestSuite
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
### See Also

   * [stackoverflow](https://stackoverflow.com/questions/26604745/parameterized-selenium-tests-in-parallel-with-testng)
   * [Advanced parallel testing with TestNG and data providers](https://beust.com/weblog/2009/04/22/advanced-parallel-testing-with-testng-and-data-providers/)
   * [TestNG parallel execution of tests, classes and suites](https://howtodoinjava.com/testng/testng-executing-parallel-tests/)
