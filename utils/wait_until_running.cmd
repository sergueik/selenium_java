@echo off
REM use trick from https://devblogs.microsoft.com/oldnewthing/20110825-00/?p=9803
SETLOCAL ENABLEDELAYEDEXPANSION
set IMAGENAME=vivaldi.exe
if not "%~1" == "" set "IMAGENAME=%~1"

:WAIT_FOR_PROCESS
echo Wait untill all processes finish
rem Wait untill all processes finish
C:\Windows\System32\timeout.exe /T !TIMEOUT! /nobreak 
set IMAGENAME=ReSampler.exe
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !IMAGENAME!" /NH') do (
    if "%%_" == "!IMAGENAME!" (
        rem There is still at least one instance of the ReSampler.exe tool running
        goto :WAIT_FOR_PROCESS
    )
)
