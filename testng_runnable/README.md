
### Info

This directory contains project
based on [crossbrowsertesting/selenium-testng](https://github.com/crossbrowsertesting/selenium-testng)
and [testng documentation](http://testng.org/doc/documentation-main.html#running-testng-programmatically)
and [http://www.baeldung.com/executable-jar-with-maven](http://www.baeldung.com/executable-jar-with-maven)

### Note

Currently the 

```cmd
java -jar target\0.1-SNAPSHOT\runnable-testng-0.1-SNAPSHOT-jar-with-dependencies.jar
```
may not work, use
```cmd
java -cp target\lib\*;target\runnable-testng-0.1-SNAPSHOT.jar demo.EntryPoint
```
