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
set "MAX_PROCESSES=2"
set "PROCESS_COUNTER=0"
for /F "tokens=2 delims==" %%. in ('wmic.exe cpu get NumberOfCores /format:list') do set /A "MAX_PROCESSES=%%. * 2"
if not "%3" == "" set "MAX_PROCESSES=%3"
echo Will run !MAX_PROCESSES! in parallel
cd /d "%SOURCPATH%"
REM no need for "tokens=*" 
REM set OPTIONS=-r 44100 -b 16 --minphase --relaxedLPF --showStages --tempDir "%TEMP%"
set OPTIONS=-r 44100 -b 16 --dither 3 --minphase --relaxedLPF --showStages --tempDir "%TEMP%"
for /R "%SOURCPATH%" %%_ in (*.flac) do (
    set "INPUTFILE=%%_"
    set "OUTPUTFILE=%%~dp_%%~n_ - 01%%~x_"
    rem NODE dealing with quotes
    set "COMMAND=ReSampler.exe -i "!INPUTFILE!" -o "!OUTPUTFILE!" !OPTIONS!"
    echo Launch the resampler tool in a separate process: !COMMAND!
    start /B CMD /C "!COMMAND!"

    set /A PROCESS_COUNTER+=1
    echo Running !PROCESS_COUNTER! processes
    if !PROCESS_COUNTER! geq !MAX_PROCESSES! (
        rem Wait for any of the launched processes to finish before launching more
        call :WAIT_FOR_PROCESS
    )
)

:WAIT_FOR_PROCESS
echo Wait for process to finish
rem Wait for one process to finish
Rem echo timeout.exe /T !TIMEOUT! /NOBREAK
C:\Windows\System32\timeout.exe /T !TIMEOUT! /nobreak 

for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq ReSampler.exe" /NH') do (
    if "%%_" == "ReSampler.exe" (
        rem There is still at least one instance of the ReSampler.exe tool running
        goto :WAIT_FOR_PROCESS
    )
)
set "PROCESS_COUNTER=0"
exit /B
