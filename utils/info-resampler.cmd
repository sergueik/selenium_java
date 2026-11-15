@echo off
setlocal EnableDelayedExpansion

set "RESAMPLE_HOME=C:\tools\resampler"
set "PATH=%RESAMPLE_HOME%;%PATH%"
set "NEED_VERSION=2.1.2"
set result=
call :format !NEED_VERSION!
REM NOTE: if installed usind release assets download zip 
REM https://github.com/jniemann66/ReSampler/releases/download/v2.1.2/ReSampler-2.1.2-win64.zip
REM or the custom installer
REM https://github.com/jniemann66/ReSampler/releases/download/v2.1.2/ReSampler-2.1.2-win64.exe
REM the program cannot run on windows 11 - it calls the CRT in a bad ways:
REM The procedure entry point DeleteCriticalSection could not be located in the dynamic link library c:\tools\resampler\ReSampler.exe. 
REM  Directory of C:\tools\resampler

REM  11/15/2026  12:09 PM    <DIR>          .
REM  11/15/2026  12:09 PM    <DIR>          ..
REM  04/03/2026  02:21 AM         2,545,034 libfftw3-3.dll
REM  03/23/2020  06:22 AM         1,205,881 libgcc_s_seh-1.dll
REM  03/23/2020  06:22 AM        28,562,458 libstdc++-6.dll
REM  04/03/2026  02:21 AM         1,738,225 ReSampler.exe
REM  04/03/2026  02:21 AM         2,538,496 sndfile.dll

REM On modern Windows versions, it is routed through an API set file (api-ms-win-core-synch-l1-2-0.dll)
REM 
set need_result=!result!
set result=
where.exe resampler.exe
for /f "tokens=1" %%_ in ('resampler.exe --version') do (
    call :format %%_
)
if !result! lss !need_result!  echo Need !NEED_VERSION! && exit /b 1
goto :EOF

:format
set "param=%~1"
set "result="

for /f "tokens=1-3 delims=." %%A in ("%param%") do (
    set "a=0%%A"
    set "b=0%%B"
    set "c=0%%C"

    set "result=!a:~-2!!b:~-2!!c:~-2!"
)

echo Version: !param! ^> !result!
exit /b
goto :EOF
