### Info

This directory contains a replica of [FXTrayIcon](https://github.com/dustinkredmond/FXTrayIcon)
Added JDK 11 

### Usage

```cmd
mvn package
java -cp target\app-0.2.0-SNAPSHOT.jar example.applications.RunnableTest
```

```cmd
java -cp target\app-0.2.0-SNAPSHOT.jar example.applications.notaskbaricon.Main
```

alternatively use Maven javafx plugin to run the project:
```cmd
mvn -P jdk11 javafx:run
```
alternaviely run the supplier launcher script
```cmd
run.cmd
```
### See Also

  * https://www.baeldung.com/java-illegalaccesserror
