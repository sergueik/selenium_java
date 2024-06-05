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
set RUN=c:\temp\convert0374.cmd
:BEGIN
echo @echo OFF
echo SETLOCAL ENABLEDELAYEDEXPANSION
echo set /a DELAY=%%RANDOM%%
echo set /a DELAY=^^!DELAY^^! %%%% 10 + 1
echo echo DELAY=^^!DELAY^^!
echo set DELAY=%%1
echo timeout.exe /T ^^!DELAY^^! /nobreak
echo goto :EOF
echo PATH=%%PATH%%;C:\tools\ffmpeg-4.0.2-win64-static\bin
REM Using
REM ffprobe.exe
REM ffmpeg.exe

REM dir /b/s *.!EXT!
for /F "tokens=*" %%. in ('dir /b/s *.!EXT!') do (
set DIRECTORY=%%~dp.
set INPUTFILE=%%~nx.
set OUTPUTFILE=%%~n..mkv
if NOT "!DIRECTORY!" equ "." echo 1^>NUL 2^>NUL pushd "!DIRECTORY!"
echo if NOT exist "!OUTPUTFILE!" ^(
echo ffmpeg.exe -i "!INPUTFILE!" -c:v vp9 -s !SIZE! -v 0 "!OUTPUTFILE!"
echo ^)
if NOT "!DIRECTORY!" equ "." echo 1^>NUL 2^>NUL popd
)
goto :EOF
