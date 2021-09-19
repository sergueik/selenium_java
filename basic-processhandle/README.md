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

### Old Testing of Challenges With Finding PID by Exploring Parent / Child Process from on Windows Java.
Note: this is obsolete and will be removed soon.
* building
```sh
mvn package
```
* running
```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 2562
```
```sh
2021-09-14 15:37:48  [main] - INFO  2562
2021-09-14 15:37:48  [main] - INFO  Process pid: 2562 is: alive(C:\Program Files\Git\git-bash.exe)
````

### Note

Some test errors: `ProcessHandle` reposting `active` pids which have no running processes.
* start with a pid that has a process running e.g. any from `tasklist` output
```cmd
tasklist /FO TABLE /NH /M kernel32.dll

taskhost.exe                   344 kernel32.dll
dwm.exe                       2008 kernel32.dll
explorer.exe                  1648 kernel32.dll
VBoxTray.exe                   872 kernel32.dll
powershell.exe                1140 kernel32.dll
conhost.exe                   1168 kernel32.dll
cmd.exe                       1028 kernel32.dll
conhost.exe                    944 kernel32.dll
notepad.exe                   3228 kernel32.dll
notepad++.exe                 3260 kernel32.dll
cmd.exe                       4092 kernel32.dll
conhost.exe                   1452 kernel32.dll
notepad.exe                   2500 kernel32.dll
tasklist.exe                  2340 kernel32.dll
```
* comfirm only one process e.g. `taskhost.exe` is executing:
```cmd
tasklist /fi "imagename eq taskhost.exe" /nh
```
```text
taskhost.exe                   344 Console                    1      6,892 K
```

if there are multiple, e.g. 
```cmd
tasklist /fi "imagename eq mintty.exe" /nh
```
```cmd
mintty.exe                    4664 Console                    1     12,784 K
mintty.exe                    1876 Console                    1     12,652 K
```
take note of PIDs.

* run positive check test of the known running PID from above command
```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 344
2021-09-14 20:11:32  [main] - INFO  looking pid 344
2021-09-14 20:11:32  [main] - INFO  344
2021-09-14 20:11:32  [main] - INFO  Process pid (via ProcessHandle): 344 is: alive (command: C:\Windows\System32\taskhost.exe started:2021-09-15T02:46:16.513Z pid:344)
2021-09-14 20:11:33  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 344"]
2021-09-14 20:11:33  [main] - INFO  Process pid (via tasklist): 344 is: alive
```
followed by same command but with a  different pid argument, e.g. by one
```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 345
2021-09-14 20:13:13  [main] - INFO  looking pid 345
2021-09-14 20:13:13  [main] - INFO  345
2021-09-14 20:13:13  [main] - INFO  Process pid (via ProcessHandle): 345 is: alive (command: C:\Windows\System32\taskhost.exe started:2021-09-15T02:46:16.513Z pid:345)
2021-09-14 20:13:13  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 345"]
2021-09-14 20:13:14  [main] - INFO  Process pid (via tasklist): 345 is: not alive
```
Clearly the `ProcessHandle` result is wrong. 

Similar observation for multiple processe present: for 3 invalid `pid`s have been shown as __alive__

```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 3600
2021-09-15 10:08:12  [main] - INFO  looking pid 3600
2021-09-15 10:08:12  [main] - INFO  3600
2021-09-15 10:08:12  [main] - INFO  Process pid (via ProcessHandle): 3600 is: al
ive (command: C:\Program Files\Git\usr\bin\mintty.exe started:2021-09-15T13:54:0
8.387Z pid:3600)
2021-09-15 10:08:12  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 3600"]
2021-09-15 10:08:13  [main] - INFO  Process pid (via tasklist): 3600 is: alive

java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 3601
2021-09-15 10:08:21  [main] - INFO  looking pid 3601
2021-09-15 10:08:21  [main] - INFO  3601
2021-09-15 10:08:21  [main] - INFO  Process pid (via ProcessHandle): 3601 is: alive (command: C:\Program Files\Git\usr\bin\mintty.exe started:2021-09-15T13:54:08.387Z pid:3601)
2021-09-15 10:08:22  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 3601"]
2021-09-15 10:08:22  [main] - INFO  Process pid (via tasklist): 3601 is: not alive

java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 3602
2021-09-15 10:09:34  [main] - INFO  looking pid 3602
2021-09-15 10:09:34  [main] - INFO  3602
2021-09-15 10:09:34  [main] - INFO  Process pid (via ProcessHandle): 3602 is: alive (command: C:\Program Files\Git\usr\bin\mintty.exe started:2021-09-15T13:54:08.387Z pid:3602)
2021-09-15 10:09:34  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 3602"]
2021-09-15 10:09:35  [main] - INFO  Process pid (via tasklist): 3602 is: not alive

java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 3603
2021-09-15 10:10:44  [main] - INFO  looking pid 3603
2021-09-15 10:10:44  [main] - INFO  3603
2021-09-15 10:10:44  [main] - INFO  Process pid (via ProcessHandle): 3603 is: alive (command: C:\Program Files\Git\usr\bin\mintty.exe started:2021-09-15T13:54:08.387Z pid:3603)
2021-09-15 10:10:44  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 3603"]
2021-09-15 10:10:45  [main] - INFO  Process pid (via tasklist): 3603 is: not alive

java -cp target\example.processhandle.jar;target\lib\* example.App -a  check -p 3604
2021-09-15 10:09:54  [main] - INFO  looking pid 3604
2021-09-15 10:09:54  [main] - INFO  null
2021-09-15 10:09:54  [main] - INFO  Process pid (via ProcessHandle): 3604 is: not alive null
2021-09-15 10:09:54  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 3604"]
2021-09-15 10:09:54  [main] - INFO  Process pid (via tasklist): 3604 is: not alive
```
Probably error in some properties not cleared (?), but how can it be true - the Java applicaton is finishing execution after each test

Tested on several environments, seen in all variations of OS / JDK on Windows, not reprooducedd on Linux (but maybe not tried enough):

   * Windows 10 64 bit Oracle JDK 11.0.x Enterprise
   * Windows 8 64 bit openjdk "11.0.10" "Azul Systems, Inc."
   * Windows 7 32 bit JDK 11.0.12 "Temurin-11.0.12+7" (Oracle is not offering 32 JDK starting with Java 9)
   * Windows 7 32 bit JDK 11.0.10 "Azul Systems, Inc."
   * Ubuntu 16.04 LTS Xenial openjdk version "11.0.2" OpenJDK Runtime Environment 18.9 (build 11.0.2+9)
   * Alpine 9 ZULU JDK 11 (details TBD)

This can be solved by simply not using
```java
ProcessHandle processHandle = ProcessHandle.allProcesses().filter(o -> o.pid() == pid).findFirst().orElse(null);
status = (processHandle == null) ? false : processHandle.isAlive();
```

and not using

```java
ProcessHandle processHandle = null;
try {
	result = ProcessHandle.of(pid);
	processHandle = result.isPresent() ? result.get() : null;
	logger.info(processHandle);
} catch (NoSuchElementException e) {
	processHandle = null;
}
```
to retrieve status of operating system process with pid `pid`.

### Windows Launcher Testing

* specify the JDK
```
set JAVA_HOME=C:\java\zulu11.45.27-ca-jdk11.0.10-win_x64
call ..\utils\setup.cmd
```

* write `application.properties`
```java
options=-XX:+UseG1GC -Xms512m -Xmx512m -XX:ParallelGCThreads=6 -Xlog:gc*

special_options=-XX:StartFlightRecording=disk=true,delay=3-s,maxsize=100m,name=continuous,settings=default
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

