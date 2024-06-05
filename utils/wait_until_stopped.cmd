@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
set TIMEOUT=10
set SERVICENAME=Spooler
if not "%~1" == "" set "SERVICENAME=%~1"

:WAIT_FOR_PROCESS
echo Wait until service %SERVICENAME% gets into stopped state
sc.exe query !SERVICENAME! | c:\Windows\System32\find.exe "STOPPED" > NUL
if %ERRORLEVEL% NEQ 0 (
   REM The %SERVICENAME% has not complete stopping
   C:\Windows\System32\timeout.exe /T !TIMEOUT! /nobreak 
   goto :WAIT_FOR_PROCESS
)
