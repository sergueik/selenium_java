rem origin: https://raw.githubusercontent.com/BecauseQA/becauseQA-selenium/master/Selenium-Server-Standalone.bat
rem ---------------------------------------------------------------------------
rem  2016.08.05  V 1.0.0
::     changed to download selenium 3 driver and firefox new driver
::     chromedirver.exe and geckodriver.exe path check 

:: Author  Alter Hu
rem ---------------------------------------------------------------------------

@setlocal enableextensions enabledelayedexpansion
@echo off
rem get 32 bit or 64 bit
rem reg Query "HKLM\Hardware\Description\System\CentralProcessor\0" | find /i "x86" > NUL && set OS=32BIT || set OS=64BIT

rem if %OS%==32BIT echo This is a 32bit operating system
rem if %OS%==64BIT echo This is a 64bit operating system
rem https://eternallybored.org/misc/wget/

SET FIREFOXDRIVER_URL="https://api.github.com/repos/mozilla/geckodriver/releases"
SET CHROMEDRIVER_URL="https://chromedriver.storage.googleapis.com"
SET SELENIUM_RELEASE_URL="https://selenium-release.storage.googleapis.com"

SET "ziptool=Tools\7z.exe"
SET "xmltool=Tools\xml.exe"

echo "Kill the localhost:4444 port for RemoteWebDriver..."
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :4444') DO TASKKILL /F /PID %%P

rem -------------------------------------------Chrome Driver-----------------------------------------------------------------------

echo "-------------------------------------------------------------------------------------------------------------------------------------------------"

SET LATEST_CHROMEDIRVER_RELEASE="%CHROMEDRIVER_URL%/LATEST_RELEASE"

SET "LATEST_CHROMEDRIVER_OPUTPUT_FILE=LATEST_RELEASE_CHROMEDRIVER.txt"

SET chromedriver_zipname="chromedriver_win32.zip"
SET CHROMEDRIVER_NAME_PREFIX="chromedriver"

ECHO "Check operating system is 32bit or 64bit..."
if defined PROGRAMFILES(X86) (
    SET OS=64BIT
    SET jqTool="Tools\jq-win64.exe"
    SET wgetFile="Tools\wget64.exe"
) else (
    SET OS=32BIT
    SET jqTool="Tools\jq-win32.exe"
    SET wgetFile="Tools\wget.exe"
)

echo "System bit is: %OS%, Then kill the iedriver and chrome driver from system."
rem kill the iedriver and chromedriver from system
TASKKILL /F /IM %CHROMEDRIVER_NAME_PREFIX%.exe

rem download the latest chrome driver
%wgetFile% %LATEST_CHROMEDIRVER_RELEASE% -O %LATEST_CHROMEDRIVER_OPUTPUT_FILE%
for /f "delims=" %%x in (%LATEST_CHROMEDRIVER_OPUTPUT_FILE%) do set Build=%%x
echo "Latest found chrome driver is: %Build%"

SET "CHROMEDRIVER_WIN32_URL=%CHROMEDRIVER_URL%/%Build%/%chromedriver_zipname%"
SET "CHROMEDRIVER_OUTPUT_FOLDER=%Build%/%chromedriver_zipname%"
rem download the latest ie driver
if not exist "%Build%" (mkdir %Build% )
SET "RENAMED_CHROMEDRIVER_NAME=%CHROMEDRIVER_NAME_PREFIX%-%Build%.exe"
echo "New Renamed chromedriver is: %RENAMED_CHROMEDRIVER_NAME%"
if not exist "%CHROMEDRIVER_OUTPUT_FOLDER:"=%" (
	echo "Chrome driver not download before ,will download from server: %CHROMEDRIVER_WIN32_URL%"
	%wgetFile% -r -np -l 1 -A zip %CHROMEDRIVER_WIN32_URL% -O %CHROMEDRIVER_OUTPUT_FOLDER%
	%ziptool% x %CHROMEDRIVER_OUTPUT_FOLDER%  -y
	:: Rename the exe file
	::move %CHROMEDRIVER_NAME_PREFIX%.exe %RENAMED_CHROMEDRIVER_NAME%
) else (
    echo "Chrome driver download before: %CHROMEDRIVER_OUTPUT_FOLDER:"=%,will not download again from server:  %CHROMEDRIVER_WIN32_URL%"
)
:: take to use the system environment variable to set the value,no need to set it now
::SET webdriver.chrome.driver=%RENAMED_CHROMEDRIVER_NAME%
::setx path "%PATH%;%RENAMED_CHROMEDRIVER_NAME:"=%" 

rem ---------------------------------------------------Firefox Driver for Selenium 3---------------------------------------------------------------
rem How to remove the double quote in the string 
SET LATEST_FIREFOX_RELEASE_FILENAME="Latest_Firefox.json"
%wgetFile% -O- -nv %FIREFOXDRIVER_URL% > %LATEST_FIREFOX_RELEASE_FILENAME:"=%
ECHO "Download the firefox release note into current place %LATEST_FIREFOX_RELEASE_FILENAME:"=% "
FOR /F "delims=" %%i in ('%jqTool:"=% [.[0].assets][0][3].name ^< %LATEST_FIREFOX_RELEASE_FILENAME:"=%') DO SET FIREFOX_WIN64_NAME=%%i
FOR /F "delims=" %%i in ('%jqTool:"=% [.[0].assets][0][3].browser_download_url ^< %LATEST_FIREFOX_RELEASE_FILENAME:"=%') DO SET FIREFOX_WIN64_URL=%%i
echo "Lastest firefox driver name is:%FIREFOX_WIN64_NAME:"=% ,download url is: %FIREFOX_WIN64_URL:"=%"
SET Firefox_driver_name="geckodriver.exe"

SET Firefox_output_folder=%FIREFOX_WIN64_NAME:~1,-11%
echo "Create Firefox folder is: %Firefox_output_folder:"=% "
SET RENAMED_FIREFOXDRIVER_NAME=%Firefox_output_folder:"=%.exe
echo "Renamed firefox driver name is: "%RENAMED_FIREFOXDRIVER_NAME:"=%
if not exist "%Firefox_output_folder%" (mkdir %Firefox_output_folder:"=% )
SET "FIREFOXDRIVER_OUTPUT_FOLDER=%Firefox_output_folder%/%FIREFOX_WIN64_NAME%"
echo "Firefox local zip file path is: %FIREFOXDRIVER_OUTPUT_FOLDER:"=%"
::
if not exist "%FIREFOXDRIVER_OUTPUT_FOLDER:"=%" (
	echo "Firefox driver not download before ,will download from server: %FIREFOX_WIN64_URL:"=%"
	%wgetFile% -r -np -l 1 -A zip %FIREFOX_WIN64_URL% -O %FIREFOXDRIVER_OUTPUT_FOLDER%
	%ziptool% x %FIREFOXDRIVER_OUTPUT_FOLDER%  -y
) else (
    echo "Firefox driver download before: %FIREFOXDRIVER_OUTPUT_FOLDER:"=%,will not download again from server:  %FIREFOX_WIN64_URL:"=%"
)
::SET webdriver.gecko.driver=%RENAMED_FIREFOXDRIVER_NAME:"=%
::echo "webdriver.gecko.driver=%webdriver.gecko.driver:"=%"

rem ---------------------------------------------------IE Driver---------------------------------------------------------------
rem download the lastest selenium-server-standalone 
echo "-------------------------------------------------------------------------------------------------------------------------------------------------"
SET LATEST_selenium_file="LATEST_RELEASE_SELENIUM.xml"
SET SELENIUM_LATEST_RELEASE_URL="%SELENIUM_RELEASE_URL%/?delimiter=/&prefix="
echo "download the selenium version list file to check the latest selenium version"
%wgetFile% -O- -nv %SELENIUM_LATEST_RELEASE_URL% > %LATEST_selenium_file%
FOR /F %%i IN ('%xmltool% sel -N "x=http://doc.s3.amazonaws.com/2006-03-01" -T -t -v "//x:Prefix[last()-1]" %LATEST_selenium_file%') DO SET "version=%%i"
SET "versionnumber=%version:/=%"
echo "lastest selenium version is: %versionnumber%"

SET SELENIUM_LATEST_URL="%SELENIUM_RELEASE_URL%/?delimiter=/&prefix=%version%"
SET SELENIUM_FOLDER_NAME=%versionnumber%
if not exist %SELENIUM_FOLDER_NAME% (mkdir %SELENIUM_FOLDER_NAME% )



rem --------------------------------------------------------------------
rem below is for IEDriver duplicated no ie driver now
echo "Begin to download the IEDriver..."
SET IEDriverServer_NAME="IEDriverServer"
TASKKILL /FI "IMAGENAME eq %IEDriverServer_NAME%*"

SET LATEST_selenium_filelist="LATEST_RELEASE_SELENIUMLIST.xml"

SET "IEDRIVER_NAME=%IEDriverServer_NAME%-%versionnumber%.0.exe"

SET "iedriver_win32_file=IEDriverServer_Win32_%versionnumber%.0.zip"
SET download_iedriver_win32="%SELENIUM_RELEASE_URL%/%versionnumber%/%iedriver_win32_file%"
SET iedriver_win32_location="%SELENIUM_FOLDER_NAME%\%iedriver_win32_file%"
SET "iedriver_x64_file=IEDriverServer_x64_%versionnumber%.0.zip"
SET download_iedriver_X64="%SELENIUM_RELEASE_URL%/%versionnumber%/%iedriver_x64_file%"
SET iedriver_x64_location="%SELENIUM_FOLDER_NAME%\%iedriver_x64_file%"




echo "Begin to download selenium server and iedrriver: %~dp0%IEDRIVER_NAME%.........."
rem ie driver 32 bit
rem if not exist "%~dp0%IEDRIVER_NAME%" (
rem 	 %wgetFile% -O- -nv %SELENIUM_LATEST_URL% > %LATEST_selenium_filelist%
rem     rem FOR /F  %%i IN ('XML.EXE sel -N "x=http://doc.s3.amazonaws.com/2006-03-01" -T -t -v "//x:Key[text()[contains(.,%iedriver_win32_prefix%)]]" %selenium_lastest_version%') DO (
rem     rem  echo %%i)
rem 	echo "Not Found IEDriver download before: %~dp0%IEDRIVER_NAME% ,will request to download from server."
rem 	IF /i "%OS%"=="32BIT" (
rem 		echo "Download 32 bit iedrriver into %iedriver_win32_location%"
rem 		%wgetFile% -r -np -l 1 -A zip %download_iedriver_win32% -O %iedriver_win32_location%
rem 		%ziptool% x %iedriver_win32_location% -y
		
rem 	) else (
rem 		 echo "Download 64 bit iedrriver into %iedriver_x64_location%"
rem 		 %wgetFile% -r -np -l 1 -A zip %download_iedriver_X64% -O %iedriver_x64_location%
rem 		 %ziptool% x %iedriver_x64_location% -y
	
rem 	)
rem 	move %IEDriverServer_NAME%.exe %IEDRIVER_NAME%
rem ) else (
rem     echo "Found IEDriver download before: %~dp0%IEDRIVER_NAME% ,will not request to download from server."
rem )


rem ----------------------------------------------Standalone server jar file--------------------------------------------------------------------
SET SELENIUM_SERVER_STANDALONE_PREFIX_NAME="selenium-server-standalone"

for /F "tokens=1,2 delims=-" %%a in ("%versionnumber%") do (
   SET prefixversion=%%a
   SET betaversion=%%b
)
IF /i "%betaversion%"=="" (
	SET "SELENIUM_SERVER_JAR_NAME=%SELENIUM_SERVER_STANDALONE_PREFIX_NAME:"=%-%prefixversion%.0.jar"
) else (
    SET "SELENIUM_SERVER_JAR_NAME=%SELENIUM_SERVER_STANDALONE_PREFIX_NAME:"=%-%prefixversion%.0-%betaversion%.jar"
)
echo "jar file is: %SELENIUM_SERVER_JAR_NAME%"
rem if /i "%versionnumber%"
rem SET "SELENIUM_NAME=%SELENIUM_SERVER_STANDALONE_NAME%-%versionnumber%.0.jar"
SET SELENIUM_STANDALONE_URL=%SELENIUM_RELEASE_URL%/%version%%SELENIUM_SERVER_JAR_NAME%
SET SELENIUM_STANDALONE_OUTPUT=%SELENIUM_FOLDER_NAME%\%SELENIUM_SERVER_JAR_NAME%
echo "Selenium download url is: %SELENIUM_STANDALONE_URL%"
echo "selenium output url is: %SELENIUM_STANDALONE_OUTPUT%"
rem selenium standalone server jar file
if not exist "%~dp0%SELENIUM_SERVER_JAR_NAME%" (
	%wgetFile% -r -np -l 1 -A zip %SELENIUM_STANDALONE_URL% -O %SELENIUM_STANDALONE_OUTPUT%
	echo f |xcopy /f /y %SELENIUM_STANDALONE_OUTPUT% %SELENIUM_SERVER_JAR_NAME% 
)

DEL %LATEST_CHROMEDRIVER_OPUTPUT_FILE% /Q
DEL %LATEST_selenium_file% /Q
DEL %LATEST_FIREFOX_RELEASE_FILENAME% /Q
::DEL %LATEST_selenium_filelist% /Q

rem \selenium-master\java\server\src\org\openqa\selenium\server\SeleniumServer.java
rem run the selenium-server with the chrome and ie chrome parameters
rem find the command line usage in this location file: 
java -jar %SELENIUM_SERVER_JAR_NAME%  -logLongForm -debug
rem -- -browserSessionReuse -trustAllSSLCertificates -Dwebdriver.chrome.driver=%CHROMEDRIVER_NAME% -Dwebdriver.ie.driver=%IEDRIVER_NAME% -debug