
### Info

This directory contains project
based on [crossbrowsertesting/selenium-testng](https://github.com/crossbrowsertesting/selenium-testng)
and [testng documentation](http://testng.org/doc/documentation-main.html#running-testng-programmatically)
and [http://www.baeldung.com/executable-jar-with-maven](http://www.baeldung.com/executable-jar-with-maven)
See also [jinahya/executable-jar-with-maven-example](https://github.com/jinahya/executable-jar-with-maven-example).

### Note

To enable single runnable jar with deendencies (at a cost of the size), use `runnable-jar-with-dependencies` profile:

```cmd
mvn -P runnable-jar-with-dependencies clean package
```
and run
```cmd
set DUMMYDIR=c:\temp
copy target\runnable-testng-jar-with-dependencies.jar %DUMMYDIR%
mkdir %DUMMYDIR%\target\classes
copy target\classes\Test.xlsx %DUMMYDIR%\target\classes
The `%DUMMYDIR%` represents the Jenkins workspace directory

Running the test
```cmd
pushd %DUMMYDIR%
java -jar runnable-testng-jar-with-dependencies.jar
```

would produce:

```cmd
===============================================
Command line suite
Total tests run: 2, Failures: 0, Skips: 0
===============================================
```
In the default profile, dependencies are coped to the `target/lib`, and can be stored anywhere in the system
```cmd
mvn clean package
robocopy target\lib c:\temp\shared /s
java -cp c:\temp\shared\*;target\runnable-testng-0.2-SNAPSHOT.jar demo.EntryPoint
```

This would also produce:
```cmd
===============================================
Command line suite
Total tests run: 2, Failures: 0, Skips: 0
===============================================


Note that this example project has the data parameter file `Tests.xlsx` which is loaded from file system:

```java		
File file = new File(System.getProperty("user.dir") + File.separator
    + "target\\classes\\Test.xlsx");
```
therefore one has to make it avalable from the specific path relative to the directory jar is placed.

The `default` profile makes the project version part of resulting jar filename, 
the `runnable-jar-with-dependencies` profile strips it away.

The packaging of all and every dependencies into one jar has a disadvantage of 
seriously increasing the size of that jar file, in our case from under 15 K
```cmd
02/27/2018  08:55 AM            14,445 runnable-testng-0.2-SNAPSHOT.jar
```
to over 36M:
```cmd
02/27/2018  08:57 AM        36,901,791 runnable-testng-jar-with-dependencies.jar
```

### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
