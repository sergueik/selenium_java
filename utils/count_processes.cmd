@echo off
REM use trick from https://devblogs.microsoft.com/oldnewthing/20110825-00/?p=9803
SETLOCAL ENABLEDELAYEDEXPANSION
set IMAGENAME=vivaldi.exe
if not "%~1" == "" set "IMAGENAME=%~1"
set /A RUNNING_PPOCESS_COUNT=0
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !IMAGENAME!" /NH ^| C:\Windows\System32\find.exe "!IMAGENAME!" ^|C:\Windows\System32\find.exe/c /v "" ') do set /A RUNNING_PPOCESS_COUNT=%%_
echo !RUNNING_PPOCESS_COUNT! !IMAGENAME! is running
goto :EOF
