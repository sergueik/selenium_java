
### Info

This directory contains project
based on [crossbrowsertesting/selenium-testng](https://github.com/crossbrowsertesting/selenium-testng)
and [testng documentation](http://testng.org/doc/documentation-main.html#running-testng-programmatically)
and [http://www.baeldung.com/executable-jar-with-maven](http://www.baeldung.com/executable-jar-with-maven)
(see also [jinahya/executable-jar-with-maven-example](https://github.com/jinahya/executable-jar-with-maven-example))

### Note

To enable single runnable jar with deendencies (at a cost of the size), use `runnable-jar-with-dependencies` profile:
```cmd
mvn -P runnable-jar-with-dependencies clean package
```
and run
```cmd
java -jar target\runnable-testng-0.1-SNAPSHOT-jar-with-dependencies.jar
```
The default, dependencies are coped to the `target/lib`
```cmd
mvn clean package
java -cp target\lib\*;target\runnable-testng-0.1-SNAPSHOT.jar demo.EntryPoint
```
