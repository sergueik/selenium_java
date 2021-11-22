### Info


![launch capture](https://github.com/sergueik/selenium_java/blob/master/basic-processhandle/screenshots/capture-launch.jpg)

This directory contains example code to launch the java jar in background process and get the process PID. The Java 11 `ProcessHandle.of` method was found to be unreliable, so th get the Java process pid, a small inline Powershell script is launched
and java PID is written to a pidfile by that script allowing the calling Java program to read the pid.

The [jna](https://en.wikipedia.org/wiki/Java_Native_Access) is still added the dependencies to have the legacy JNA based code snippets for

* `GetProcessId` [example](https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId)
* `getCurrentProcessId` [example](https://www.tabnine.com/code/java/methods/com.metamx.metrics.SigarUtil/getCurrentProcessId)
* `isProcessIdRunningOnWindows` [example](https://stackoverflow.com/questions/2533984/java-checking-if-any-process-id-is-currently-running-on-windows/41489635)

examples available in the same application  for comparison

### Usage 

```sh
mvn package
```

```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App --action powershell --wait 30000
```
This will launch a mini Swing App to test itself. It passes a lot of Java options (to test ability). It waits a  litle bit (configurable) then reads the java process PID from `pidfile` and kills that process. It will log the following to the console:
```cmd
2021-09-19 18:41:39  [main] - INFO  processing command: java.exe -cp target\example.processhandle.jar;target\lib\* -XX:+UseG1GC -Xms512m -Xmx512m -XX:ParallelGCThreads=6 -Xlog:gc* -XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default example.Dialog
2021-09-19 18:41:39  [main] - INFO  New Command Arguments:['-cp', 'target\example.processhandle.jar;target\lib\*', '-XX:+UseG1GC', '-Xms512m', '-Xmx512m', '-XX:ParallelGCThreads=6', '-Xlog:gc*', '-XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default', 'example.Dialog']
2021-09-19 18:41:39  [main] - INFO  New Command: "-filepath 'java.exe' -argumentlist '-cp', 'target\example.processhandle.jar;target\lib\*', '-XX:+UseG1GC', '-Xms512m', '-Xmx512m', '-XX:ParallelGCThreads=6', '-Xlog:gc*', '-XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default', 'example.Dialog'"
2021-09-19 18:41:39  [main] - INFO  processing command: java.exe -cp target\example.processhandle.jar;target\lib\* -XX:+UseG1GC -Xms512m -Xmx512m -XX:ParallelGCThreads=6 -Xlog:gc* -XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default example.Dialog
2021-09-19 18:41:39  [main] - INFO  New Command Arguments:['-cp', 'target\example.processhandle.jar;target\lib\*', '-XX:+UseG1GC', '-Xms512m', '-Xmx512m', '-XX:ParallelGCThreads=6', '-Xlog:gc*', '-XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default', 'example.Dialog']
2021-09-19 18:41:39  [main] - INFO  New Command: "-filepath 'java.exe' -argumentlist '-cp', 'target\example.processhandle.jar;target\lib\*', '-XX:+UseG1GC', '-Xms512m', '-Xmx512m', '-XX:ParallelGCThreads=6', '-Xlog:gc*', '-XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default', 'example.Dialog'"
2021-09-19 18:41:39  [main] - INFO  ProcessBuilder arguments: 
powershell.exe,
/noprofile,
/executionpolicy,
bypass,
"&{,
$info,
=,
start-process,
-filepath,
'java.exe',
-argumentlist,
'-cp',,
'target\example.processhandle.jar;target\lib\*',,
'-XX:+UseG1GC',,
'-Xms512m',,
'-Xmx512m',,
'-XX:ParallelGCThreads=6',,
'-Xlog:gc*',,
'-XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default',,
'example.Dialog',
-passthru;,
$info.PriorityClass=[System.Diagnostics.ProcessPriorityClass]::BelowNormal;,
write-output,
('Pid={0}',
-f,
$info.id),
|,
out-file,
-FilePath,
'C:\TEMP\pidfile.txt',
-encoding,
ascii;,
}"
2021-09-19 18:41:39  [main] - INFO  Launching command: powershell.exe /noprofile /executionpolicy bypass "&{ $info = start-process -filepath 'java.exe' -argumentlist '-cp', 'target\example.processhandle.jar;target\lib\*', '-XX:+UseG1GC', '-Xms512m', '-Xmx512m', '-XX:ParallelGCThreads=6', '-Xlog:gc*', '-XX:StartFlightRecording=disk=true,delay=30s,maxsize=100m,name=continuous,settings=default', 'example.Dialog' -passthru; $info.PriorityClass=[System.Diagnostics.ProcessPriorityClass]::BelowNormal; write-output ('Pid={0}' -f $info.id) | out-file -FilePath 'C:\TEMP\a123.txt' -encoding ascii; }"
2021-09-19 18:41:39  [main] - INFO  Waiting for 30000 millisecond
2021-09-19 18:42:09  [main] - INFO  C:\TEMP\pidfile.txt data:2832
2021-09-19 18:42:09  [main] - INFO  is alive: true
2021-09-19 18:42:09  [main] - INFO  Killing the process pid: 2832
```
NOTE the doubled commas in the `ProcessBuilder arguments` above is expected - needed to build Powershell's `start-process` `ArgumentListt` part:
```cmd
    Start-Process [-FilePath] <string> [[-ArgumentList] <string[]>]
    [-WorkingDirectory <string>] [-PassThru] [-Verb <string>] [-WindowStyle
    <ProcessWindowStyle> {Normal | Hidden | Minimized | Maximized}] [-Wait]
    [<CommonParameters>]
```
An alternative option (WIP) is to use `invoke-expression`
### Testing in Cygwin Environment

Due to the limitations of simulating a POSIX environment under Windows, there is little information one can get from built-in `ps` command. In particular no command line args.

To get that, use the following Powershell command from cygwin:

```sh
PID=956
powershell.exe '&{$id = $args[0]; $o = Get-CimInstance Win32_Process -Filter ("processid = {0}" -f $id ); $o.CommandLine }' $PID
```
NOTE: Cannot use variable `$pid` because variable PID because it is special, 
read-only or constant and script would fail detecting attempt to override it

this will print
```text
"C:\java\jdk1.8.0_101\bin\java.exe" -cp target\example.processhandle.jar;target\lib\* -Xms512m -Xmx512m example.Dialog
```

```
NOTE: Unrecognized option e.g. -Xmm512m will lead to hard to debug failures
* launch
```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App -a powershell -w 5000 -file %TEMP%\pidfile.txt
```
NOTE: specify the pid file path, using Windows environment notation e.g. `%TEMP%`
### See Aso

	* https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.html
	* https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.Info.html
	* https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId
	* https://www.tabnine.com/code/java/classes/java.lang.ProcessHandle
	* https://dev.to/harithay/introduction-to-java-processhandle-5950
	* https://www.baeldung.com/java-process-api (legacy)
	* https://www.baeldung.com/java-9-process-api (improvements)


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

