@echo off

REM based on: https://raw.githubusercontent.com/melk0r64/Resampler-Script/main/ReSampler.bat
REM the original code scaned subbolders
REM NOTE: the original code relied on Window title scan by tasklist.exe 
REM and failed to discover the processes to wait for
REM see also: flac downsampling
REM see also: https://forums.stevehoffman.tv/threads/high-res-downsampling-for-dummies.1146789/
REM see also: https://sox.sourceforge.net
REM see also: http://sox.sourceforge.net/SoX/Resampling
REM see also: https://github.com/melk0r64/Resampler-Script
REM see also: https://github.com/jniemann66/ReSampler
REM see also: https://github.com/tchnmf/sox/tree/master/scripts

SETLOCAL ENABLEDELAYEDEXPANSION

set "SOURCPATH=%CD%"
if not "%~1" == "" set "SOURCPATH=%~1"
set TIMEOUT=10
set PATH=%PATH%;c:\tools\resampler
set TOOL=resampler.exe
set FILESCAN_OPTIONS=
if not "%2" equ "" set FILESCAN_OPTIONS=/R "%SOURCEPATH%"

set "MAX_PROCESSES=2"
set "PROCESS_COUNTER=0"
for /F "tokens=2 delims==" %%. in ('wmic.exe cpu get NumberOfCores /format:list') do set /A "MAX_PROCESSES=%%. * 2"
if not "%3" == "" set "MAX_PROCESSES=%3"
@echo Will run !MAX_PROCESSES! in parallel
cd /d "%SOURCPATH%"
REM no need for "tokens=*" 
 
REM set CONVERT_OPTIONS=-r 44100 -b 16 --minphase --relaxedLPF --showStages --tempDir "%TEMP%"
set CONVERT_OPTIONS=-r 44100 -b 16 --dither 3 --minphase --relaxedLPF --showStages --tempDir "%TEMP%"
REM Relies on

REM Walks the directory tree %SOURCEPATH%, executing the FOR statement in each directory of the tree
REM or just the current directory when the option is not set
for  %FILESCAN_OPTIONS% %%_ in (*.flac) do (
    set "INPUTFILE=%%_"
    set "OUTPUTFILE=%%~dp_%%~n_ - 01%%~x_"
    REM NOTE dealing with quotes
    set "COMMAND=!TOOL! -i "!INPUTFILE!" -o "!OUTPUTFILE!" !CONVERT_OPTIONS!"
    @echo Launch the resampler tool in a separate process: !COMMAND!
    start /B CMD /C "!COMMAND!"

    set /A PROCESS_COUNTER+=1
    @echo Running !PROCESS_COUNTER! processes
    if !PROCESS_COUNTER! geq !MAX_PROCESSES! (
        REM Wait for any of the launched processes to finish before launching more
        call :COUNT_FOR_PROCESS
    )
)

:WAIT_FOR_PROCESS

rem Wait untill all processes finish
C:\Windows\System32\timeout.exe /T !TIMEOUT! /nobreak 
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !TOOL!" /NH') do (
    if "%%_" == "!TOOL!" (
        REM There is still at least one instance of the !TOOL! running
        goto :WAIT_FOR_PROCESS
    )
)
set "PROCESS_COUNTER=0"
cls
exit /B
goto :EOF

REM use trick from https://devblogs.microsoft.com/oldnewthing/20110825-00/?p=980xi3
:COUNT_FOR_PROCESS

REM Wait untill at least one !TOOL! process finishes
C:\Windows\System32\timeout.exe /T !TIMEOUT! /nobreak 
set /A RUNNING_PPOCESS_COUNT=0
for /F "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq !TOOL!" /NH ^| C:\Windows\System32\find.exe /i "!TOOL!" ^|C:\Windows\System32\find.exe/c /v "" ') do (
    set /A RUNNING_PPOCESS_COUNT=%%_
    @echo !RUNNING_PPOCESS_COUNT! !TOOL! processes are running
    if !RUNNING_PPOCESS_COUNT! equ !MAX_PROCESSES! (
       goto :COUNT_FOR_PROCESS
    )
)
set /A "PROCESS_COUNTER=!RUNNING_PPOCESS_COUNT!"
@echo set PROCESS_COUNTER to !PROCESS_COUNTER!
exit /B
