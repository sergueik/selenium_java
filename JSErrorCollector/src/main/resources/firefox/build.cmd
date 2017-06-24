
@echo off
 
setlocal

pushd %~dp0
 
set APP_NAME="JSErrorCollector"
set CHROME_PROVIDERS="content"
 
set ROOT_DIR=%CD%
set TMP_DIR="build"
 
rem remove any left-over files from previous build
del /Q %APP_NAME%.xpi
del /S /Q %TMP_DIR%
 
mkdir %TMP_DIR%\chrome\content
 
robocopy.exe content %TMP_DIR%\chrome\content /E
robocopy.exe locale %TMP_DIR%\chrome\locale /E
robocopy.exe skin %TMP_DIR%\chrome\skin /E
robocopy.exe defaults %TMP_DIR%\defaults /E
copy install.rdf %TMP_DIR%
copy chrome.manifest.production %TMP_DIR%\chrome.manifest
 
rem Package the XPI file
cd %TMP_DIR%
echo "Generating %APP_NAME%.xpi..."
PATH=%PATH%;%ProgramFiles%\7-Zip;%ProgramFiles(x86)%\7-Zip

7z.exe a -r -y -tzip ../%APP_NAME%.zip *
 
cd %ROOT_DIR%
rename %APP_NAME%.zip %APP_NAME%.xpi
 
endlocal