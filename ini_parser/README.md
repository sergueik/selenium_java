### Info

This directory contains a replica of [basic ini file parser](https://github.com/RdlP/IniParser) java project by Ángel Luis 
and a minor modifications to that project.

### Usage
* build app
```cmd
mvn package
```
* write the file `custom.properties` in the launcher directory:
```text
USERNAME=defined
# will be overridden by system property with the same name
option1=user:${USERNAME}
FOO=bar
option2=FOO:${FOO}
```
in the right hand side one can use properties just defined except when the property name collides with predefined ones like `USERNAME` on WINDOW or `HOME` on Unix.
you can update the file any time 
* run the app, which will simply echo the read values of few options
```cmd
set debug=false
java -cp target\iniparser-0.1-SNAPSHOT.jar example.test.Test
```

or on Linux
```
DEBUG=false java -cp target/iniparser-0.1-SNAPSHOT.jar example.test.Test
```
```text
option1: user:Serguei
option2: FOO:bar
option3: default value for option3
```
NOTE: the output will be different on Linux where is no `USERPROFILE` environment variable

* remove the file `custom.properties` and rerun. The `custom.properties` resource will be read 

```
rm -f ./custom.properties
mvn clean package
DEBUG=true java -cp target/iniparser-0.1-SNAPSHOT.jar example.test.Test
```

```text
Reading properties file: 'C:/developer/sergueik/selenium_java/ini_parser/custom.properties'
Reading properties resource stream: 'custom.properties'
Reading: 'option2' = 'FOO:${FOO}'
getting property: FOO
system property: null
environment property: bar
Reading: 'option1' = 'user:${USERNAME}'
getting property: USERNAME
system property: null
environment property: Serguei
Reading: 'FOO' = 'bar'
Reading: 'USERNAME' = 'defined'
getting property: option1
system property: user:Serguei
option1: user:Serguei
getting property: option2
system property: FOO:bar
option2: FOO:bar
getting property: option3
system property: null
environment property: null
option3: default value for option3
```

* temporarily  remove the file `src/main/resources/custom.properties` and rebuild and rerun. The `custom.properties` resource will be read 
```sh
rm -f src/main/resources/custom.properties
mvn clean package
```
```sh
DEBUG=true java -cp target/iniparser-0.1-SNAPSHOT.jar example.test.Test
```
```text
debug: true
fileName: C:/developer/sergueik/selenium_java/ini_parser/custom.properties
Reading properties file: 'C:/developer/sergueik/selenium_java/ini_parser/custom.
properties'
Reading properties resource stream: 'custom.properties'
getting property: option1
system property: null
environment property: null
option1: default value for option1
getting property: option2
system property: null
environment property: null
option2: default value for option2
getting property: option3
system property: null
environment property: null
option3: default value for option3
```
### See also:
  * [C# ini parser project by the same author](https://github.com/RdlP/IniParser/blob/master/IniParser.cs)
  * [same project embedded in Powershell script with Add-Type](https://github.com/sergueik/powershell_ui_samples/blob/master/ini_parser.ps1)
  * [another Powrshell snippet for processing ini files](https://github.com/lipkau/PsIni/blob/master/PSIni/Functions/Get-IniContent.ps1)
  * [another C# ini parser project](https://github.com/lukamicoder/IniParser/tree/master/IniParser)
  * [yet another, somewhat bloated c# project containing complex hierarchy of classes just to deal with the ini file](https://github.com/simplesoft-pt/IniParser)
  * [default Ruby package for ini files](https://github.com/TwP/inifile)
  * [blog](https://ourcodeworld.com/articles/read/839/how-to-read-parse-from-and-write-to-ini-files-easily-in-java) on how to read and write to INI files easily in Java using `org.ini4j.ini4j` [jar](https://mvnrepository.com/artifact/org.ini4j/ini4j)
  
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
