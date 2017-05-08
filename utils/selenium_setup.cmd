@echo OFF
setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

REM origin: https://raw.githubusercontent.com/BecauseQA/becauseQA-selenium/master/Selenium-Server-Standalone.bat
REM origin http://forum.oszone.net/thread-326552.html

REM uses
REM wget.exe https://eternallybored.org/misc/wget/
REM xml.exe http://xmlstar.sourceforge.net/download.php
REM 7z.exe http://www.7-zip.org/
REM jq.exe https://stedolan.github.io/jq/

REM define tool aliases
if defined PROGRAMFILES(X86) (
    set OS=64BIT
    set JQ="C:\TOOLS\jq-win64.exe"
    REM set WGET="C:\Program Files (x86)\GnuWin32\bin\wget.exe"
    set WGET="C:\TOOLS\wget.exe"
    REM set CURL="C:\Program Files (x86)\curl\curl.exe"
    REM set CURL="C:\TOOLS\curl.exe"
    set XMLSTARLET="C:\TOOLS\XMLSTARLET-1.6.1\xml.exe"
) else (
    set OS=32BIT
    set JQ="C:\TOOLS\jq-win32.exe"
    REM set WGET="C:\Program Files\GnuWin32\bin\wget.exe"
    set WGET="C:\TOOLS\wget.exe"
    REM set CURL="C:\Program Files\curl\curl.exe"
    REM set CURL="C:\TOOLS\curl.exe"
    set XMLSTARLET="C:\TOOLS\XMLSTARLET-1.6.1\xml.exe"
)
REM Alternatively may add to the PATH environment
set PATH=%PATH%;C:\TOOLS
set PATH=%PATH%;C:\TOOLS\XMLSTARLET-1.6.1
set PATH=%PATH%;C:\PROGRAM FILES\7-ZIP
REM set PATH=%PATH%;C:\C:\Program Files (x86)\curl
REM set PATH=%PATH%;C:\Program Files (x86)\GnuWin32\bin

REM Stop Selenium hub or standalone server
for /F "tokens=5 delims= " %%_ IN ('netstat -a -n -o ^| findstr :4444') do taskkill.exe /F /PID %%_

REM the old versions of Chrome are available on http://www.slimjetbrowser.com/chrome/win/
REM the currently installed Chrome version can be found from several registry locations
REM reg.exe query "HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Google\Update\Clients\{8A69D345-D564-463c-AFF1-A69D9E530F96}" -v pv
REM reg.exe query "HKEY_LOCAL_MACHINE\Software\Microsoft\Windows\CurrentVersion\App Paths\chrome.exe\"
REM dir /od /ad /b "C:\Program Files (x86)\Google\Chrome\Application"

:CHROMEDRIVER
set CHROMEDRIVER_URL=https://chromedriver.storage.googleapis.com
set CHROMEDRIVER_ZIPNAME=chromedriver_win32.zip
set CHROMEDRIVER_PROCESS_NAME=chromedriver
taskkill.exe /F /IM %CHROMEDRIVER_PROCESS_NAME%.exe
set RESPONSE_FILE=response.txt
echo Determine the latest Chrome driver version
wget.exe %CHROMEDRIVER_URL%/LATEST_RELEASE -O %RESPONSE_FILE%
for /f "delims=" %%_ in (%RESPONSE_FILE%) do set BUILD=%%_
del /q %RESPONSE_FILE%
echo The latest release of Chrome driver is %BUILD%

set CHROMEDRIVER_WIN32_URL=%CHROMEDRIVER_URL%/%BUILD%/%CHROMEDRIVER_ZIPNAME%
set CHROMEDRIVER_PACKAGE=%BUILD%\%CHROMEDRIVER_ZIPNAME%
set RENAMED_CHROMEDRIVER_NAME=%CHROMEDRIVER_PROCESS_NAME%-%BUILD%.exe

if not exist "%BUILD%" (mkdir %BUILD% )
if not exist "%CHROMEDRIVER_PACKAGE:"=%" (
  REM chromeDriver does not allow HEAD
  REM %CURL% --output NUL --silent --head --fail --url %CHROMEDRIVER_WIN32_URL% >NUL 2>&1 && echo %CHROMEDRIVER_WIN32_URL% is present
  REM %WGET% --spider --no-dns-cache --no-cache --quiet %CHROMEDRIVER_WIN32_URL% >NUL 2>&1 && echo %CHROMEDRIVER_WIN32_URL% is present
  REM powershell.exe -command "& {Param([string]$url) write-output ('url = {0}' -f $url); try { $o = Invoke-WebRequest -Uri $url -Method Head -UseBasicParsing -ErrorAction SilentlyContinue; echo $o.StatusCode; $status = ( $o.StatusCode -eq 200 ); $exitcode  =  [int]( -not ($status)); write-output ('will exit with {0}' -f ${exitcode}) ; exit $exitcode ; } catch [Exception] {exit -1} }"  -url "%CHROMEDRIVER_WIN32_URL%"&& echo %CHROMEDRIVER_WIN32_URL% is present
	echo Download Chrome driver %BUILD% from %CHROMEDRIVER_WIN32_URL%
	wget.exe -r -np -l 1 -A zip %CHROMEDRIVER_WIN32_URL% -O %CHROMEDRIVER_PACKAGE%
	7z.exe x %CHROMEDRIVER_PACKAGE% -y
  echo Chromedriver can be renamed to: %RENAMED_CHROMEDRIVER_NAME%
	REM optional - rename the exe file, and add to the path and system variables
	REM move %CHROMEDRIVER_PROCESS_NAME%.exe %RENAMED_CHROMEDRIVER_NAME%
  REM set webdriver.chrome.driver=%RENAMED_CHROMEDRIVER_NAME%
  REM setx PATH "%PATH%;%RENAMED_CHROMEDRIVER_NAME:"=%"
) else (
  echo Chrome driver %CHROMEDRIVER_PACKAGE:"=% already downloaded
)

:GECKODRIVER
set GECKODRIVER_URL=https://api.github.com/repos/mozilla/geckodriver/releases
echo Determine the latest Gecko driver version
set RESPONSE_FILE=response.json
wget.exe -O- -nv %GECKODRIVER_URL% > %RESPONSE_FILE%
REM TODO: correctly determine the browser_download_url
for /L %%_ in (1,1,4) do (
set ITEM=%%_
for /F "delims=" %%_ in ('%JQ:"=% [.[0].assets][0][!ITEM!].name ^< %RESPONSE_FILE%') do set x=%%_
  set GECKODRIVER_PLATFORM=!x:~-10,5!
  if "!GECKODRIVER_PLATFORM!" == "win32" (
    set GECKODRIVER_NAME=!x!
    for /F "delims=" %%_ in ('%JQ:"=% [.[0].assets][0][!ITEM!].browser_download_url ^< %RESPONSE_FILE%') do set GECKODRIVER_URL=%%_
  )
)
del /q %RESPONSE_FILE%

set GECKODRIVER_OUTPUT_FOLDER=!GECKODRIVER_NAME:~1,-11!
if not exist "%GECKODRIVER_OUTPUT_FOLDER%" (mkdir %GECKODRIVER_OUTPUT_FOLDER:"=% )
set GECKODRIVER_PACKAGE=%GECKODRIVER_OUTPUT_FOLDER%/!GECKODRIVER_NAME!

if not exist "%GECKODRIVER_PACKAGE:"=%" (
  REM %CURL% --output NUL --silent --head --fail --url %GECKODRIVER_URLL% >NUL 2>&1 && echo %GECKODRIVER_URL% is present
  REM %WGET% --spider --no-dns-cache --no-cache --quiet %GECKODRIVER_URL% >NUL 2>&1 && echo %GECKODRIVER_URL% is present
  REM powershell.exe -command "& {Param([string]$url) write-output ('url = {0}' -f $url); try { $o = Invoke-WebRequest -Uri $url -Method Head -UseBasicParsing -ErrorAction SilentlyContinue; echo $o.StatusCode; $status = ( $o.StatusCode -eq 200 ); $exitcode  =  [int]( -not ($status)); write-output ('will exit with {0}' -f ${exitcode}) ; exit $exitcode ; } catch [Exception] {exit -1} }"  -url "%GECKODRIVER_URL%" && echo %GECKODRIVER_URL% is present
	echo Download Gecko driver from %GECKODRIVER_URL:"=%
	wget.exe -r -np -l 1 -A zip !GECKODRIVER_URL! -O %GECKODRIVER_PACKAGE%
	7z.exe x %GECKODRIVER_PACKAGE%  -y
  echo Gecko driver local zip file path is: %GECKODRIVER_PACKAGE:"=%
) else (
  echo Gecko driver %GECKODRIVER_PACKAGE:"=% is already downloaded
)
set RENAMED_GECKODRIVER_NAME=%GECKODRIVER_OUTPUT_FOLDER:"=%.exe
echo Gecko driver can be renamed as %RENAMED_GECKODRIVER_NAME:"=%
::set webdriver.gecko.driver=%RENAMED_GECKODRIVER_NAME:"=%
::echo "webdriver.gecko.driver=%webdriver.gecko.driver:"=%"

:SELENIUM
set SELENIUM_RELEASE_URL=https://selenium-release.storage.googleapis.com
set SELENIUM_LATEST_RELEASE_URL="%SELENIUM_RELEASE_URL%/?delimiter=/&prefix="
echo Determine the latest Selenium version
set RESPONSE_FILE=response.xml
wget.exe -O- -nv %SELENIUM_LATEST_RELEASE_URL% > %RESPONSE_FILE%
for /F %%_ in ('xml.exe sel -N "x=http://doc.s3.amazonaws.com/2006-03-01" -T -t -v "//x:Prefix[last()-1]" %RESPONSE_FILE%') do set VERSION=%%_
set VERSIONNUMBER=%VERSION:/=%
echo Latest Selenium version is %VERSIONNUMBER%
set SELENIUM_FOLDER_NAME=%VERSIONNUMBER%
if not exist %SELENIUM_FOLDER_NAME% (mkdir %SELENIUM_FOLDER_NAME% )

set SELENIUM_LATEST_URL="%SELENIUM_RELEASE_URL%/?delimiter=/&prefix=%VERSION%"

for /F "tokens=1,2 delims=-" %%a in ("%VERSIONNUMBER%") do (
   set PREFIXVERSION=%%a
   set BETAVERSION=%%b
)
if /i "%BETAVERSION%"=="" (
	set SELENIUM_JAR_NAME=selenium-server-standalone-%PREFIXVERSION%.0.jar
) else (
  set SELENIUM_JAR_NAME=selenium-server-standalone-%PREFIXVERSION%.0-%BETAVERSION%.jar
)

set SELENIUM_STANDALONE_URL=%SELENIUM_RELEASE_URL%/%VERSION%%SELENIUM_JAR_NAME%
set SELENIUM_PACKAGE=%SELENIUM_FOLDER_NAME%\%SELENIUM_JAR_NAME%
if not exist "%SELENIUM_JAR_NAME%" (
  echo Downloading Selenium jar %SELENIUM_PACKAGE% from %SELENIUM_STANDALONE_URL%
	wget.exe -r -np -l 1 -A zip %SELENIUM_STANDALONE_URL% -O %SELENIUM_PACKAGE%
	echo f |xcopy /f /y %SELENIUM_PACKAGE% %SELENIUM_JAR_NAME%
  echo Selenium jar file is %SELENIUM_JAR_NAME%
)

:IEDRIVER
set IEDRIVERSERVER_NAME=IEDriverServer.exe
taskkill.exe /FI "IMAGENAME eq %IEDRIVERSERVER_NAME%*"

set RESPONSE_FILE=response.xml
echo Downloding the %IEDRIVERSERVER_NAME%
if not exist "%IEDRIVERSERVER_NAME%" (
  echo Determine the latest version of %IEDRIVERSERVER_NAME% for %OS%
	wget.exe -O- -nv %SELENIUM_LATEST_URL% > %RESPONSE_FILE%
	if /i "%OS%"=="32BIT" (
    set IEDRIVER_PREFIX=IEDriverServer_Win32
	) else (
    set IEDRIVER_PREFIX=IEDriverServer_x64
	)
  for /F  %%_ in ('xml.exe sel -T -N "x=http://doc.s3.amazonaws.com/2006-03-01" -t -c "//x:Key[contains(.,'!IEDRIVER_PREFIX!')]" %RESPONSE_FILE%') do set IEDRIVER_LOCATION=%%_
  del /q %RESPONSE_FILE%
  for /F "tokens=2 delims=/" %%_ in ("!IEDRIVER_LOCATION!") do set IEDRIVER_PACKAGE=%SELENIUM_FOLDER_NAME%\%%_
  set IEDRIVER_DOWNLOAD_URL=%SELENIUM_RELEASE_URL%/!IEDRIVER_LOCATION!
  echo Download !IEDRIVER_PACKAGE! from !IEDRIVER_DOWNLOAD_URL!
  wget.exe -r -np -l 1 -A zip !IEDRIVER_DOWNLOAD_URL! -O !IEDRIVER_PACKAGE!
  7z.exe x !IEDRIVER_PACKAGE! -y
) else (
  echo %IEDRIVERSERVER_NAME% is already downloaded
)

goto :EOF
