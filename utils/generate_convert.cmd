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
echo EXT=!EXT!

set FLAG=%3
if "!FLAG!"=="" goto :BEGIN
echo FLAG=%FLAG%
:BEGIN
dir /b/s *.!EXT!
PATH=%PATH%;C:\tools\ffmpeg-4.0.2-win64-static\bin
REM C:\tools\ffmpeg-4.0.2-win64-static\bin\ffprobe.exe
REM C:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe
for /F "tokens=*" %%. in ('dir /b/s *.!EXT!') do (
set D=%%~dp.
set S=%%~nx.
set T=%%~n..mkv
if NOT "!D!" equ "." 1>NUL 2>NUL pushd !D!
if NOT exist !T! (
echo Converting "!S!" "!T!"
if /I "!DEBUG!"=="true" echo ffmpeg.exe -i "!S!" -c:v vp9 -s !SIZE! -v 0 "!T!"
REM ffmpeg.exe -i "!S!" -c:v vp9 -s !SIZE! -v 0 "!T!"
REM echo call :convert "!S!"
call :convert "!S!" "!T!" !SIZE!
)
if NOT "!D!" equ "." 1>NUL 2>NUL popd 

)
goto :EOF
REM Only captures part of the name
:convert
set S=%~1
set F=%~2
set SIZE=%3
if exist "%F%" goto :EOF
if /I "!DEBUG!"=="true" echo ffmpeg.exe -i "%S%" -c:v vp9 -s %SIZE% -v 0 "%F%"

ffmpeg.exe -i "%S%" -c:v vp9 -s %SIZE% -v 0 "%F%"
goto :EOF
