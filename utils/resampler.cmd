@echo off

REM origin: https://raw.githubusercontent.com/melk0r64/Resampler-Script/main/ReSampler.bat
REM NOTE: the original code relied on Window title scan by tasklist.exe 
REM and does not correctly wait for the processes to terminate
REM see also: flac downsampling
REM see also: https://forums.stevehoffman.tv/threads/high-res-downsampling-for-dummies.1146789/
REM see also: https://sox.sourceforge.net
REM see also: http://sox.sourceforge.net/SoX/Resampling
REM see also: https://github.com/melk0r64/Resampler-Script
REM see also: https://github.com/jniemann66/ReSampler
REM see also: https://github.com/tchnmf/sox/tree/master/scripts

setlocal EnableExtensions EnableDelayedExpansion
set "SourcePath=%CD%\"
set "TargetPath=%CD%\..\resampled"
mkdir %TargetPath%
rem If the batch file was started with a string as
rem parameter, interpret this string as source path.
if not "%~1" == "" set "SourcePath=%~1"

rem If the batch file was started with one more string
rem as parameter, interpret this string as target path.
if not "%~2" == "" set "TargetPath=%~2"

rem Remove backslash at end of source and target path
rem in case of being specified with a backslash at end.
if "%SourcePath:~-1%" == "\" set "SourcePath=%SourcePath:~0,-1%"
if "%TargetPath:~-1%" == "\" set "TargetPath=%TargetPath:~0,-1%"

rem Determine length of source path by finding out at which
rem position in source path there is no more character.
set "PathLength=1"
:GetPathLength
if not "!SourcePath:~%PathLength%,1!" == "" (
    set /A PathLength+=1
    goto GetPathLength
)

rem Process each file not having hidden or system attribute set and
rem decrypt it to the target path relative to source path. The relative
rem path is determined by removing from full path of current file the
rem first PathLength characters and the last character which is the
rem directory separator (backslash).
set PATH=%PATH%;c:\tools\resampler
set "MAX_PROCESSES=2"
set "PROCESS_COUNTER=0"
for /F "tokens=2 delims==" %%. in ('wmic.exe cpu get NumberOfCores /format:list') do set /A "MAX_PROCESSES=%%. * 2"
if not "%3" == "" set "MAX_PROCESSES=%3"
echo Will run !MAX_PROCESSES! in parallel
REM seems to scan subbolders too
for /R "%SourcePath%" %%I in (*.flac) do (
    set "RelativePath=%%~dpI"
    set "RelativePath=!RelativePath:~%PathLength%,-1!"
    md "%TargetPath%!RelativePath!" 2>nul

    set "COMMAND=ReSampler.exe -i "%%I" -o "%TargetPath%!RelativePath!\%%~nxI" -r 44100 -b 16 --dither 3 --minphase --relaxedLPF --showStages --tempDir %TEMP%"

    rem Launch the resampler tool in a separate process
    echo Launch the resampler tool in a separate process: !COMMAND!
    START /B CMD /C "!COMMAND!"

    set /A PROCESS_COUNTER+=1
    echo Started !PROCESS_COUNTER! processes
    if !PROCESS_COUNTER! geq !MAX_PROCESSES! (
        rem Wait for any of the launched processes to finish before launching more
        CALL :WAIT_FOR_PROCESS
    )
)

:WAIT_FOR_PROCESS
echo Wait for process to finish
rem Wait for one process to finish
timeout.exe /T 2 /NOBREAK
REM PING -n 2 127.0.0.1 >NUL
for /f "tokens=1" %%_ in ('tasklist.exe /FI "IMAGENAME eq ReSampler.exe" /NH') do (
    if "%%_" == "ReSampler.exe" (
        rem There is still at least one instance of the ReSampler.exe tool running
        goto :WAIT_FOR_PROCESS
    )
)
set "PROCESS_COUNTER=0"
exit /B
