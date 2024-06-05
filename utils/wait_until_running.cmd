@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
set TIMEOUT=10
set IMAGENAME=vivaldi.exe
if not "%~1" == "" set "IMAGENAME=%~1"

:WAIT_FOR_PROCESS
echo Wait until all %IMAGENAME% processes finish
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !IMAGENAME!" /NH') do (
    if "%%_" == "!IMAGENAME!" (
        REM There is still at least one instance of the %IMAGENAME% running
        C:\Windows\System32\timeout.exe /T !TIMEOUT! /nobreak 
        goto :WAIT_FOR_PROCESS
    )
)
