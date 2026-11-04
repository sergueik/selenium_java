### Info

convert Visio VSDX to Mermaid
```cmd
mvn clean package
```
```cmd
java -jar target\example.read_xml.jar
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
```cmd
java -Dlog.level=DEBUG -jar target\example.read_xml.jar
```
```text
2026-11-04T16:29:04.291021500Z main WARN Posix file attribute permissions defined but it is not supported by this files system.
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 7, Text: Submit the registration form
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 8, Text: Form Complete?
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 3, Text:
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 4, Text: No
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 6, Text: Yes
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 9, Text: Verify Details of Submission
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 12, Text:
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 11, Text: Accept Submission?
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 14, Text: Yes
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 13, Text: Write Acceptance Letter
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 16, Text:
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 17, Text: Acceptance Letter
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 19, Text: No
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 18, Text: Notify Student on the Rejection
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 62, Text: Start
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 63, Text:
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 64, Text: End
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 65, Text:
2026-11-04 11:29:04 [main] - DEBUG Shape ID: 66, Text:
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
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 1, FromSheet: 19, FromCell: endx, ToSheet: 18, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 2, FromSheet: 19, FromCell: beginx, ToSheet: 11, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 3, FromSheet: 16, FromCell: endx, ToSheet: 17, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 4, FromSheet: 16, FromCell: beginx, ToSheet: 13, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 5, FromSheet: 3, FromCell: beginx, ToSheet: 7, ToCell: Connections.X3
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 6, FromSheet: 3, FromCell: endx, ToSheet: 8, ToCell: Connections.X3
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 7, FromSheet: 4, FromCell: beginx, ToSheet: 8, ToCell: Connections.X2
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 8, FromSheet: 4, FromCell: endx, ToSheet: 7, ToCell: Connections.X2
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 9, FromSheet: 66, FromCell: endx, ToSheet: 64, ToCell: Connections.X4
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 10, FromSheet: 66, FromCell: beginx, ToSheet: 17, ToCell: Connections.X1
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 11, FromSheet: 65, FromCell: endx, ToSheet: 64, ToCell: Connections.X1
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 12, FromSheet: 65, FromCell: beginx, ToSheet: 18, ToCell: Connections.X4
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 13, FromSheet: 63, FromCell: endx, ToSheet: 7, ToCell: Connections.X1
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 14, FromSheet: 63, FromCell: beginx, ToSheet: 62, ToCell: Connections.X4
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 15, FromSheet: 14, FromCell: endx, ToSheet: 13, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 16, FromSheet: 6, FromCell: beginx, ToSheet: 8, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 17, FromSheet: 6, FromCell: endx, ToSheet: 9, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 18, FromSheet: 12, FromCell: beginx, ToSheet: 9, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 19, FromSheet: 12, FromCell: endx, ToSheet: 11, ToCell: PinX
2026-11-04 11:29:04 [main] - DEBUG Connect ID: 20, FromSheet: 14, FromCell: beginx, ToSheet: 11, ToCell: PinX
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
