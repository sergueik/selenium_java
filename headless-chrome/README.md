### Info

This directory contains headless Chrome wrapper Java helper class and example
based on [ldaume/headless-chrome](https://github.com/ldaume/headless-chrome)

The project has been stale for some time, then tested with Chrome __101__ (64 bit) running on Windows __10__ VM. To detect the headless run, an environment variable `WINDOWS_NO_DISPLAY` is set by the launcher script which itself is run by [Windowws Scheduled Task](https://www.windowscentral.com/how-create-automated-task-using-task-scheduler-windows-10) with one minute delay after the reboot event.


![visual run](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-visual.png)

![task actions](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-task.png)

![task trigger](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-task-trigger.png)

![processes](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-processes.png)

![results](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-results.png)

the log of the headless run ( from the logfile). 
```txt
running by app 
checking display 
setting WINDOWS_NO_DISPLAY to true
1 
setting WINDOWS_NO_DISPLAY to true
WINDOWS_NO_DISPLAY=true  

Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-resources-plugin/2.6/maven-resources-plugin-2.6.pom (8.1 kB at 8.7 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-plugins/23/maven-plugins-23.pom
Progress (1): 2.7/9.2 kBProgress (1): 5.5/9.2 kBProgress (1): 8.2/9.2 kBProgress (1): 9.2 kB    
...
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
NOTE: - the  maven dependencies will be downloaded into the user profile in the first run
If the scheduled task is configured to run under `NT AUTHORIRY\SYSTEM` account 

![task account](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-task-user2.png)

the jars will go into `c:\Windows\SysWOW64\config\systemprofile\.m2\repository` for 32 bit task on 64 bit system and into 
`c:\Windows\System32\config\systemprofile\.m2\repository` otherwise.


The log from the call of the launcher interactively:
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
### Note About autoadminlogon setting:

[Winlogon](https://docs.microsoft.com/en-us/windows/win32/secauthn/winlogon-and-gina), the GINA, and network providers are the parts of the interactive logon model. The interactive logon procedure is normally controlled by Winlogon, MSGina.dll, and network providers. To change the interactive logon procedure, MSGina.dll can be replaced with a customized GINA DLL.

[GINA](https://en.wikipedia.org/wiki/Graphical_identification_and_authentication) is a replaceable dynamically linked library that is loaded early in the boot process in the context of Winlogon

![bypass credential](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-bypass.png)

The [Windows shell](https://en.wikipedia.org/wiki/Windows_shell), as it is known today, is an evolution of what began with Windows 95, released in 1995. It is intimately identified with File Explorer, 
a Windows component that can browse the whole shell namespace.

The `headless_detector.cmd` script used in this project is checking if the shell process is running by the account that runs the detector script:
```cmd
tasklist.exe /FI "USERNAME eq %USERNAME%" /FI "IMAGENAME EQ explorer.exe"
```
when the output shows the process:
```text
Image Name                     PID Session Name        Session#    Mem Usage
========================= ======== ================ =========== ============
explorer.exe                  3912 Console                    1    143,648 K
```
the environment variable `WINDOWS_NO_DISPLAY` is cleared. Otherwise it is set. There are  two alternative ways to do it in Powershell. One requires elevation:
```powershell
$processname = 'explorer'
get-process -name $processname -includeusername
Get-Process : The 'IncludeUserName' parameter requires elevated user rights.
Try running the command again in a session that has been opened with elevated user rights (that is, Run as Administrator).
```
the other uses [performs p/invoke](https://www.codeproject.com/Articles/14828/How-To-Get-Process-Owner-ID-and-Current-User-SID) into `advapi32.dll` and contains substantial amount of inline C# code (see `headless_detector.ps1` [script](https://github.com/sergueik/powershell_ui_samples/blob/master/headless_detector.ps1)).

The password for the account [can](https://docs.microsoft.com/en-us/windows/win32/secauthn/msgina-dll-features) be specified in one of two ways. 
For computers running one of the Windows Server 2003 or Windows XP operating systems, 
the password should be stored as a secret using the LsaStorePrivateData function. 
For details, see Protecting the Automatic Logon Password. The other way to store the password [plaintext](https://docs.microsoft.com/en-us/troubleshoot/windows-server/user-profiles-and-logon/turn-on-automatic-logon) in the Registry.



If no DefaultPassword string is specified, Windows automatically changes the value of the AutoAdminLogon key from 1 (true) to 0 (false), disabling the AutoAdminLogon feature.

The __SAS__ (Secure Attention Sequence) in __Reactos__
and __CSRSS__ ([Client Server Runtime Subsystem](https://en.wikipedia.org/wiki/Client/Server_Runtime_Subsystem)) in Windows are similar [roles](https://www.howtogeek.com/321581/what-is-client-server-runtime-process-csrss.exe-and-why-is-it-running-on-my-pc/)
[sas.c](https://github.com/reactos/reactos/blob/master/base/system/winlogon/sas.c#L80) 
```c
static BOOL
 StartUserShell(
     IN OUT PWLSESSION Session)
```
[msgina.c](https://github.com/reactos/reactos/blob/master/dll/win32/msgina/msgina.c#L167)
```c
static BOOL GetRegistrySettings(PGINA_CONTEXT pgContext) {    
 rc = RegOpenKeyExW(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Winlogon", 0, KEY_QUERY_VALUE, &hKey);
  rc = ReadRegSzValue(hKey, L"AutoAdminLogon", &lpAutoAdminLogon);
  if (rc == ERROR_SUCCESS)
    if (wcscmp(lpAutoAdminLogon, L"1") == 0)
      pgContext->bAutoAdminLogon = TRUE;
```
[msgina.c](https://github.com/reactos/reactos/blob/master/dll/win32/msgina/msgina.c#L907)
```c
if (pgContext->bAutoAdminLogon) {
  if (pgContext->bIgnoreShiftOverride || (GetKeyState(VK_SHIFT) >= 0)) {
     /* Don't display the window, we want to do an automatic logon */
     pgContext->pWlxFuncs->WlxSasNotify(pgContext->hWlx, WLX_SAS_TYPE_CTRL_ALT_DEL);
```            
```text
214794369
The process hosting the driver for this device has been terminated. (Exception from HRESULT: 0x8007050B)
```
### Grid


![grid task actions](https://github.com/sergueik/selenium_java/blob/master/headless-chrome/screenshots/capture-task-grid.png)

these tasks launch hub
```cmd
hub4.cmd
```

node

```
node4.cmd
```

and test

```
client.cmd
```


Note:  only tested with Selenium standalone __4.0__ this far - unknown if works with downlevel Selenium __3.x__

for the sake of passing all options  through selenium hub pipeline:
```java
ChromeOptions options = new ChromeOptions();
options.setBinary(osName.equals("windows") ? (new File(
	(System.getenv().containsKey("PROCESSOR_ARCHITECTURE")
	&& System.getenv("PROCESSOR_ARCHITECTURE").matches("(?:AMD64)")) ?
	"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe" : "C:/Program Files/Google/Chrome/Application/chrome.exe")) .getAbsolutePath() : "/usr/bin/chromium-browser" /* "/usr/bin/google-chrome" */ );
if (headless) {
	for (String optionAgrument : (new String[] { "headless", "--window-size=1200x800", "disable-gpu" })) {
	options.addArguments(optionAgrument);
}

driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);

```
the legacy invocation
```java
DesiredCapabilities capabilities = DesiredCapabilities.chrome();
capabilities.setCapability("headless", true);
capabilities.setCapability("platform", "WINDOWS");
capabilities.setCapability("version", "latest");
driver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"), capabilities);
```
was not found to work (more testing is pending)

The error with Selenium Server __3.x__ called with `options`:
```text
software.MinimalTest: Unable to create new remote session.
Caused by: org.openqa.selenium.SessionNotCreatedException: 
Unable to create new remote session. desired capabilities = 
Capabilities [{goog:chromeOptions={args=[headless, --window-size=1200x800, disable-gpu], extensions=[], 
binary=C:\Program Files\Google\Chrome\Application\chrome.exe}, browserName=chrome}], 
required capabilities = Capabilities [{}]
```
the error with Selenium Server __3.x__ called with `capabilities`:
```text

org.openqa.selenium.remote.UnreachableBrowserException: 
Could not start a new session. 
Possible causes are invalid address of the remote server or browser start-up failure.

```
along with that error, the hub console logs the attempt:
```text
04:58:58.274 INFO - Got a request to create a new session: Capabilities [{headless=true, browserName=chrome, version=latest, platform=WINDOWS}]
```

while if in foreground
```text
```
#### See also

  * [getting started with headless Chrome](developers.google.com/web/updates/2017/04/headless-chrome)
  * [in russian, Javascript port](https://habrahabr.ru/post/329660/)
  * [minimal project](https://github.com/WeikeDu/HeadlessChrome)
  * [tips regarding reducing additioan lattency because of chomedriver (in Russian)](http://internetka.in.ua/selenium-chrome-driver/)
  * [Selenium JsonWire Protocol](https://github.com/SeleniumHQ/selenium/wiki/JsonWireProtocol)
  * [Chrome Devkit Protocol](https://github.com/ChromeDevTools/awesome-chrome-devtools)
  * [article](https://www.codeproject.com/Articles/14828/How-To-Get-Process-Owner-ID-and-Current-User-SID) on how to get process owner
  * known [SID](http://support.microsoft.com/kb/243330) list
  * [blog](https://www.toolsqa.com/selenium-webdriver/selenium-headless-browser-testing) on headless switch -  does not cover `RemoteWebDriver`
  * https://www.browserstack.com/guide/selenium-grid-4-tutorial - the 'headless' option is ifnored
### Author


[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
