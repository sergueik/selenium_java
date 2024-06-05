@echo off

SETLOCAL ENABLEDELAYEDEXPANSION
set IMAGENAME=vivaldi.exe

if not "%~1" == "" set "IMAGENAME=%~1"

call :COUNT_RUNNING_PROCESSES1 !IMAGENAME!
call :COUNT_RUNNING_PROCESSES2 !IMAGENAME!
goto :EOF

:COUNT_RUNNING_PROCESSES1
REM use trick to count the number of lines via find.exe
REM from https://devblogs.microsoft.com/oldnewthing/20110825-00/?p=9803

set IMAGENAME=%1
set /A RUNNING_PPOCESS_COUNT=0
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !IMAGENAME!" /NH ^| C:\Windows\System32\find.exe /i "!IMAGENAME!" ^|C:\Windows\System32\find.exe/c /v "" ') do set /A RUNNING_PPOCESS_COUNT=%%_
echo !RUNNING_PPOCESS_COUNT! !IMAGENAME! is running
goto :EOF

:COUNT_RUNNING_PROCESSES2
set IMAGENAME=%1
set /A RUNNING_PPOCESS_COUNT=0
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !IMAGENAME!" /NH') do (
  if /I "%%_" == "!IMAGENAME!" (
      set /A RUNNING_PPOCESS_COUNT=!RUNNING_PPOCESS_COUNT! + 1
  )
)
echo !RUNNING_PPOCESS_COUNT! !IMAGENAME! is running
goto :EOF
