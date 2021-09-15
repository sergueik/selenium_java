### Info


This directory contains example code based on [Java 9 Process API Improvements](https://www.baeldung.com/java-9-process-api)
The jna is still in the dependencies to have the legacy JNA based code snippets for

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
### See Aso

	* https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.html
	* https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.Info.html
	* https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId
	* https://www.baeldung.com/java-process-api (legacy) 
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

