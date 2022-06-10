### Info

This directory contains headless Chrome wrapper Java helper class and example
based on [ldaume/headless-chrome](https://github.com/ldaume/headless-chrome)

![visual run](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-visual.png)

![task actions](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-task.png)

![task trigger](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-task-trigger.png)

![processes](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-processes.png)

![results](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-results.png)

the log of the headless run ( from the logfile)
```txt
running by app 
checking display 
setting WINDOWS_NO_DISPLAY to true
1 
setting WINDOWS_NO_DISPLAY to true
WINDOWS_NO_DISPLAY=true  
[INFO] Scanning for projects...
...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running software.MinimalTest

Observed environment keys: [PATH, PROCESSOR_LEVEL, SYSTEMDRIVE, M2, ALLUSERSPROFILE, JAVA_OPTS, PROCESSOR_ARCHITECTURE, DRIVERDATA, MAVEN_HOME, PROGRAMFILES, PSMODULEPATH, PROGRAMDATA, USERNAME, WDIR, GROOVY_VERSION, PATHEXT, GRADLE_VERSION, LOG, PROCESSOR_ARCHITEW6432, CLASSWORLDS_JAR, MAVEN_VERSION, TOOL_HOME, WINDOWS_NO_DISPLAY, WINDIR, PROCESSOR_IDENTIFIER, M2_HOME, IVY_VERSION, PUBLIC, IVY_HOME, LOCALAPPDATA, COMMONPROGRAMFILES(X86), USERDOMAIN, JAVA_HOME, PROMPT, PROGRAMFILES(X86), ERROR_CODE, EXEC_DIR, GRADLE_HOME, MAVEN_OPTS, ANT_HOME, GROOVY_HOME, =C:, APPDATA, JAVA_TOOL_OPTIONS, PROGRAMW6432, SYSTEMROOT, JAVA_VERSION, MAVEN_CMD_LINE_ARGS, OS, COMMONPROGRAMW6432, COMPUTERNAME, COMMONPROGRAMFILES, COMSPEC, JAVACMD, MAVEN_PROJECTBASEDIR, PROCESSOR_REVISION, CLASSWORLDS_LAUNCHER, TEMP, USERPROFILE, ANT_VERSION, TMP, PHANTOMJS_HOME, NUMBER_OF_PROCESSORS, JVMCONFIG]
Checking environment JAVA_OPTS: -Xms256m -Xmx512m
Checking environment MAVEN_OPTS: -Xms256m -Xmx512m

Checking environment JAVA_TOOL_OPTIONS: -DWINDOWS_NO_DISPLAY=true  

Detected WINDOWS_NO_DISPLAY

Detected WINDOWS_NO_DISPLAY environment or property

Detected WINDOWS_NO_DISPLAY in JAVA_TOOL_OPTIONS

Switching to headless

Tests run: 3, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 24.27 sec

Picked up JAVA_TOOL_OPTIONS: -DWINDOWS_NO_DISPLAY=true  

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  38.452 s
[INFO] Finished at: 2022-06-10T01:30:21+03:00
[INFO] ------------------------------------------------------------------------
Done. 
```

the log from the call of the launcher interactively:
```cmd
client.cmd
```
```text

running by app 
checking display 
setting WINDOWS_NO_DISPLAY to true
0
clearing WINDOWS_NO_DISPLAY to true
[INFO] Scanning for projects...

...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running software.MinimalTest
checking environment: [PATH, USERDOMAIN_ROAMINGPROFILE, PROCESSOR_LEVEL, SYSTEMDRIVE, M2, SESSIONNAME, ALLUSERSPROFILE, JAVA_OPTS, PROCESSOR_ARCHITECTURE, DRIVERDATA, MAVEN_HOME, PROGRAMFILES, PSMODULEPATH, PROGRAMDATA, USERNAME, WDIR, GROOVY_VERSION, PATHEXT, GRADLE_VERSION, LOG, PROCESSOR_ARCHITEW6432, CLASSWORLDS_JAR, MAVEN_VERSION, TOOL_HOME, WINDIR, HOMEPATH, PROCESSOR_IDENTIFIER, M2_HOME, IVY_VERSION, PUBLIC, IVY_HOME, =::, =EXITCODE, LOCALAPPDATA, COMMONPROGRAMFILES(X86), USERDOMAIN, LOGONSERVER, JAVA_HOME, PROMPT, PROGRAMFILES(X86), ERROR_CODE, EXEC_DIR, GRADLE_HOME, MAVEN_OPTS, ANT_HOME, GROOVY_HOME, =C:, APPDATA, PROGRAMW6432, SYSTEMROOT, JAVA_VERSION, MAVEN_CMD_LINE_ARGS, OS, COMMONPROGRAMW6432, COMPUTERNAME, COMMONPROGRAMFILES, COMSPEC, JAVACMD, MAVEN_PROJECTBASEDIR, PROCESSOR_REVISION, CLASSWORLDS_LAUNCHER, RUBYOPT, TEMP, HOMEDRIVE, USERPROFILE, ANT_VERSION, TMP, PHANTOMJS_HOME, NUMBER_OF_PROCESSORS, JVMCONFIG]
Switching to / staying visible

Switching to / staying visible
```
#### See also

* [Getting Started with Headless Chrome] (developers.google.com/web/updates/2017/04/headless-chrome)
* [in russian, Javascript port](https://habrahabr.ru/post/329660/)
* [minimal project](https://github.com/WeikeDu/HeadlessChrome)
* [tips regarding reducing additioan lattency because of chomedriver (in Russian)](http://internetka.in.ua/selenium-chrome-driver/)
* [Selenium JsonWire Protocol](https://github.com/SeleniumHQ/selenium/wiki/JsonWireProtocol)
* [Chrome Devkit Protocol](https://github.com/ChromeDevTools/awesome-chrome-devtools)

### Author


[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
