### Info
 * run
```
mvn clean test
```

it will produce binary file `target/jacoco.exec` and a regular plain HTML report rendered by __jacoco__ itself (there is a number of alternative processors)

![Example](https://github.com/sergueik/selenium_java/blob/master/jacoco_examples/java/screenshots/capture.png)


Note, it will be using an `javaagent`
```sh
[INFO] argLine set to -javaagent:C:\\Users\\Serguei\\.m2\\repository\\org\\jacoc
o\\org.jacoco.agent\\0.8.6\\org.jacoco.agent-0.8.6-runtime.jar=destfile=C:\\deve
loper\\sergueik\\selenium_java\\jacoco_examples\\java\\target\\jacoco.exec
```
 * the code coverage report will be available in
```sh
target/site/jacoco/example/Example.html
```
 * NOTE: the report command suggested in [codecov project](https://github.com/codecov/example-java)
```sh
bash <(curl -s https://codecov.io/bash)
```
is failing with missing argument:
```sh
HTTP 400
Please provide the repository token to upload reports via
`-t :repository-token`
```
### Note

running the goal
```sh
mvn jacoco:check
```
will fail with
```
[ERROR] Failed to execute goal org.jacoco:jacoco-maven-plugin:0.8.6:check (defau
lt-cli) on project example_java: The parameters 'rules' for goal org.jacoco:jaco
co-maven-plugin:0.8.6:check are missing or invalid -> [Help 1]
[ERROR]
```
but `jacoco:check` goal is bound to `verify` maven goal and one can run the latter. The downside is that verify is default target and to prevent it from failing the other goals, one will end up setting very low and override via maven parameter:
```sh
mvn -Dminimum.coverage=0.85 clean verify
```
Note: reading properties from envionment in the profile section
```xml
    <profile>
      <id>verify</id>
      <properties>
        <minimum.coverage>${env:COVERAGE}</minimum.coverage>
      </properties>
      <activation>
        <activeByDefault>false</activeByDefault>
      </activation>
    </profile>
```

```cmd
set COVERAGE=0.85
mvn -P verify clean verify
```
by vanilla maven is currently not working:
```sh
Unable to parse configuration of mojo org.jacoco:jacoco-maven-plugin:0.8.6:check for parameter minimum: Cannot create instance of class java.math.BigDecimal: java.math.BigDecimal.<init>()
```
therefore one is forced to hardcode the desired coverage level in `pom.xml` which makes the profile somewhat useless
### See Also

  * [intro to jacoco](https://www.baeldung.com/jacoco)
  * [discussion](https://stackoverflow.com/questions/10463077/how-to-refer-environment-variable-in-pom-xml) of passing environment properties to maven

