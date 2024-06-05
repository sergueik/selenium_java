@echo OFF

SETLOCAL ENABLEDELAYEDEXPANSION

REM Convert high-res mp4 to low res mkv, directly
REM ffmpeg options taken from:
REM https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
REM https://ffmpeg.org/ffmpeg-utils.html#Video-size
REM e.g. nhd, svga, hd480, hd720
REM see also: https://github.com/nneeoo/PSffmpeg
if "!DEBUG!"=="" ( set DEBUG=false
) else (
  set DEBUG=true
)
set SIZE=%1
if "!SIZE!"=="" set SIZE=svga
set EXT=%2
if "!EXT!"=="" set EXT=mp4
REM echo EXT=!EXT!

set FLAG=%3
if "!FLAG!"=="" goto :BEGIN
REM echo FLAG=%FLAG%
goto :EOF
:BEGIN
set RUN=c:\temp\convert0374.cmd
echo @echo OFF >%RUN%

echo SETLOCAL ENABLEDELAYEDEXPANSION >>%RUN%
echo set DELAY=%%1 >>%RUN%
echo echo DELAY=^^!DELAY^^! >>%RUN%
echo echo timeout.exe /T ^^!DELAY^^! /nobreak >>%RUN%
echo if NOT "^^!DELAY^^!" equ "" timeout.exe /T ^^!DELAY^^! /nobreak >>%RUN%
echo echo Start >>%RUN%
echo PATH=%%PATH%%;C:\tools\ffmpeg-4.0.2-win64-static\bin >>%RUN%
for /F "tokens=*" %%. in ('dir /b/s *.!EXT!') do (
set DIRECTORY=%%~dp.
set INPUTFILE=%%~nx.
set OUTPUTFILE=%%~n..mkv
if NOT "!DIRECTORY!" equ "." echo 1^>NUL 2^>NUL pushd "!DIRECTORY!" >>%RUN%
echo if NOT exist "!OUTPUTFILE!" ^( >>%RUN%
echo C:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe -i "!INPUTFILE!" -c:v vp9 -s !SIZE! -v 0 "!OUTPUTFILE!" >>%RUN%
echo ^)  >>%RUN%
if NOT "!DIRECTORY!" equ "." echo 1^>NUL 2^>NUL popd >>%RUN%
)
for /L %%. in ( 1 1 4) do set /a DELAY=!RANDOM! %% 10 + 1 &&start cmd /c %RUN% !DELAY!
goto :EOF
