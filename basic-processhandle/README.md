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
2021-09-19 18:42:20  [main] - INFO  Running the command: [cmd, /c, tasklist /FI "PID eq 2832"]
2021-09-19 18:42:29  [main] - INFO  is alive (Windows version): true
2021-09-19 18:42:49  [main] - INFO  Killing the process pid: 2832
```
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

### Debug Log

* when run un debug mode
```cmd
java -cp target\example.processhandle.jar;target\lib\* example.App --debug --action powershell --wait 30000 
```
it will log some Powerhell settings to debug log file:
```cmd
type c:\temp\debug.log
```
will display something like

```text
currentdir: C:\developer\sergueik\selenium_java\basic-processhandle
path:C:\Program Files\Git\mingw64\bin;C:\Program Files\Git\usr\local\bin;C:\Program Files\Git\usr\bin;C:\Program Files\Git\usr\bin;C:\Program Files\Git\mingw64\bin;C:\Program Files\Git\usr\bin;C:\Users\sergueik\bin;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0;c:\java\zulu11.45.27-ca-jdk11.0.10-win_x64\bin
```
without `--debug` flag there will be no logging

### TODO
<!-- invalid flag: --release 
TODO: profiles -->
can not use the XML comment:
```text
[ERROR]     Non-parseable POM c:\developer\sergueik\selenium_java\basic-processhandle\pom.xml: in comment after two dashes (--) next character must be > not r (  position: END_TAG seen ...<target>${java.version}</target>\r\n<!-- invalid flag:
 --r... @85:23)  @ line 85, column 23 -> [Help 2]
```

### NOTE

When developing the code one may use a longer version of the Poweshell Process Creation helper snippet, e.g.

```powershell

[System.Diagnostics.ProcessStartInfo] $startInfo = new-object System.Diagnostics.ProcessStartInfo
$startInfo.FileName = 'java.exe'
$startInfo.Arguments = ('-cp target\example.processhandle.jar;target\lib\* example.Dialog >> {0}' -f $logfile )
$startInfo.UseShellExecute = $true
# NOTE: The Process object must have the UseShellExecute property set to false in order to redirect IO streams.
$startInfo.RedirectStandardOutput = $false
$startInfo.WorkingDirectory = $process_workdir
$startInfo.WindowStyle = [System.Diagnostics.ProcessWindowStyle]::Hidden
$startInfo.ErrorDialog = $true
[System.Diagnostics.Process] $process = [System.Diagnostics.Process]::Start($startInfo)
# TODO: improve wait until exited
while (-not $process.HasExited) {
  start-sleep -seconds 1
}

get-content -path "${process_workdir}\${logfile}"

```
![launch capture](https://github.com/sergueik/selenium_java/blob/master/basic-processhandle/screenshots/capture-launch-noshell.jpg)

if there is an error reported by Java code in the console

```text
Exception in thread "main" java.lang.NumberFormatException: For input string: ""

        at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
        at java.base/java.lang.Integer.parseInt(Integer.java:662)
        at java.base/java.lang.Integer.parseInt(Integer.java:770)
        at example.Launcher.getPid(Launcher.java:173)
        at example.App.main(App.java:106)
```

which is because there is no PID information in the pid file `C:\TEMP\pidfile.txt`:
```text
Pid=
```
to find the root cause, extract and run directly the command from the log:

```txt
2024-03-21 14:40:48  [main] - INFO  Launching command: powershell.exe /noprofile /executionpolicy bypass "&{ $o = new-object System.Diagnostics.ProcessStartInfo;$o.FileName = 'java.exe';$o.Arguments = ' -cp target\example.processhandle.jar;target\lib\* example.Dialog';$o.UseShellExecute = $false;$o.RedirectStandardOutput = $false;$o.WindowStyle = [System.Diagnostics.ProcessWindowStyle]::Hidden;$o.ErrorDialog = $false;$o.PriorityClass=System.Diagnostics.ProcessPriorityClass]::BelowNormal; $p = [System.Diagnostics.Process]::Start($o);while ($p.ID -eq 0) {start-sleep -millisecond 100;}write-output ('Pid={0}' -f $p.Id) | out-file -LiteralPath 'C:\TEMP\pidfile.txt' -encoding ascii; }"
```
The command will be
```cmd
powershell.exe /noprofile /executionpolicy bypass "&{ $o = new-object System.Diagnostics.ProcessStartInfo;$o.FileName = 'java.exe';$o.Arguments = ' -cp target\example.processhandle.jar;target\lib\* example.Dialog';$o.UseShellExecute = $false;$o.RedirectStandardOutput = $false;$o.WindowStyle = [System.Diagnostics.ProcessWindowStyle]::Hidden;$o.ErrorDialog = $false;$p = [System.Diagnostics.Process]::Start($o);$p.PriorityClass=System.Diagnostics.ProcessPriorityClass]::BelowNormal; while ($p.Id -eq 0) {start-sleep -millisecond 100;}write-output ('Pid={0}' -f $p.Id) | out-file -LiteralPath 'C:\T EMP\pidfile.txt' -encoding ascii; }"
```
the error will be displayed in clear text in the console output e.g.
```text
Exception calling "Start" with "1" argument(s): "The system cannot find the file specified"
At line:1 char:316
```
or 
```text
System.Diagnostics.ProcessPriorityClass]::BelowNormal : The term
'System.Diagnostics.ProcessPriorityClass]::BelowNormal' is not recognized as the name of a cmdlet, function, script file, or operable program. Check the spelling of the name, or if a path was included, verify that the path is correct and try again.
```
### NOTE

when JDK from the path containg special characters is used. using quotes in the evvironment variables corrupts the path

```cmd
JAVA_HOME="c:\java\jdk-11.0.12+7"
```


this leads to weird error:
```cmd
where.exe java
```
```text
INFO: Could not find files for the given pattern(s).

```

```cmd
java -version
```

```text
openjdk version "11.0.12" 2021-07-20
OpenJDK Runtime Environment Temurin-11.0.12+7 (build 11.0.12+7)
OpenJDK Client VM Temurin-11.0.12+7 (build 11.0.12+7, mixed mode)
```
### See Aso

	* https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.html
	* https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.Info.html
	* https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId
	* https://www.tabnine.com/code/java/classes/java.lang.ProcessHandle
	* https://dev.to/harithay/introduction-to-java-processhandle-5950
	* https://www.baeldung.com/java-process-api (legacy)
	* https://www.baeldung.com/java-9-process-api (improvements)
  * https://learn.microsoft.com/en-us/dotnet/api/system.diagnostics.process?view=netframework-4.5
  * https://learn.microsoft.com/en-us/dotnet/api/system.diagnostics.processstartinfo?view=netframework-4.5
    
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

