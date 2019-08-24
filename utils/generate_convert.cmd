@echo OFF

SETLOCAL ENABLEDELAYEDEXPANSION

REM Convert high-res mp4 to low res mkv, directly
REM ffmpeg options taken from:
REM https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
REM https://ffmpeg.org/ffmpeg-utils.html#Video-size
REM e.g. nhd, svga, hd480, hd720
if "!DEBUG!"=="" set DEBUG=false
set SIZE=%1
if "!SIZE!"=="" set SIZE=svga

PATH=%PATH%;C:\tools\ffmpeg-4.0.2-win64-static\bin

for /F "tokens=*" %%. in ('dir /b/s *mp4') do (
set D=%%~dp.
set S=%%~nx.
set T=%%~n..mkv
if NOT "!D!" equ "." 1>NUL 2>NUL pushd %D%
if NOT exist !T! (
echo Converting "!S!" "!T!"
if /I "!DEBUG!"=="true" echo ffmpeg.exe -i "!S!" -c:v vp9 -s !SIZE! -v 0 "!T!"
ffmpeg.exe -i "!S!" -c:v vp9 -s !SIZE! -v 0 "!T!"
)
if NOT "!D!" equ "." 1>NUL 2>NUL popd 

)