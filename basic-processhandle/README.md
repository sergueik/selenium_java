### Info


This directory contains example code based on [Java 9 Process API Improvements](https://www.baeldung.com/java-9-process-api)
The [jna](https://en.wikipedia.org/wiki/Java_Native_Access) is still in the dependencies to have the legacy JNA based code snippets for

* `GetProcessId` [example](https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId)
* `getCurrentProcessId` [example](https://www.tabnine.com/code/java/methods/com.metamx.metrics.SigarUtil/getCurrentProcessId)
* `isProcessIdRunningOnWindows` [example](https://stackoverflow.com/questions/2533984/java-checking-if-any-process-id-is-currently-running-on-windows/41489635)

examples available in the same application  for comparison

### Usage 

```sh
mvn package
```
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

