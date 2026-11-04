### Info

convert Visio VSDX to Mermaid

```cmd
java -Dorg.apache.logging.log4j.level=DEBUG -jar target\example.read_xml.jar
```
```text
N11["Accept Submission?"]
N66["?"]
N12["?"]
N13["Write Acceptance Letter"]
N14["Yes"]
N16["?"]
N17["Acceptance Letter"]
N18["Notify Student on the Rejection"]
N19["No"]
N3["?"]
N4["No"]
N6["Yes"]
N7["Submit the registration form"]
N8["Form Complete?"]
N9["Verify Details of Submission"]
N62["Start"]
N63["?"]
N64["End"]
N65["?"]
N17 --> N64
N9 --> N11
N7 --> N8
N11 --> N13
N8 --> N7
N13 --> N17
N8 --> N9
N11 --> N18
N62 --> N7
N18 --> N64
```
###Troubleshooting

there is *no* standard `-D` property that overrides the root logger level in an already-loaded vintage [Log4j](https://logging.apache.org/log4j/2.x/index.html) 1.x XML configuration. the engine has been end-of-life since 2015
the following does not go:
```cmd
copy src\main\resources\log4j.xml .
java -Dlog4j.configuration=file:log4j.xml -jar target\example.read_xml.jar
```
```text
java -Dlog4j.configuration=file:///%CD%/log4j.xml -jar target\example.read_xml.jar
log4j:WARN Continuable parsing error 11 and column 13
log4j:WARN The content of element type "appender" must match "(errorHandler?,param*,rollingPolicy?,triggeringPolicy?,connectionSource?,layout?,filter*,appender-ref*)".
log4j: reset attribute= "false".
log4j: Threshold ="null".
log4j: Level value for root is  [DEBUG].
log4j: root level set to DEBUG
log4j: Class name: [org.apache.log4j.RollingFileAppender]
log4j: Setting property [append] to [false].
log4j: Setting property [maxFileSize] to [1MB].
log4j: Setting property [maxBackupIndex] to [100].
log4j: Setting property [threshold] to [DEBUG].
log4j: Setting property [file] to [SWET.log].
log4j: Parsing layout of class: "org.apache.log4j.PatternLayout"
log4j: Setting property [conversionPattern] to [%-5p %d{yyyy-MM-dd HH:mm:ss} %F [%c{1}] - [%M] - %m%n].
log4j: setFile called: SWET.log, false
log4j: setFile ended
log4j: Adding appender named [file] to category [root].
log4j: Class name: [org.apache.log4j.ConsoleAppender]
log4j: Parsing layout of class: "org.apache.log4j.PatternLayout"
log4j: Setting property [conversionPattern] to [%-5p - %m%n].
log4j: Setting property [threshold] to [INFO].
log4j: Setting property [target] to [System.err].
log4j: Adding appender named [console] to category [root].
N11["Accept Submission?"]
N66["?"]
N12["?"]
...
```
### See Also

   * https://logging.apache.org/log4j/2.12.x/manual/configuration.html

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
