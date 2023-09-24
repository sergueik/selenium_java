@echo OFF

SETLOCAL ENABLEDELAYEDEXPANSION

if "!DEBUG!"=="" ( set DEBUG=false 
) else (
  set DEBUG=true
)
set NAME=%1
if "!NAME!"=="" echo Usage: %0 [NAME] && exit /b 0
set EXT=%2
if "!EXT!"=="" set EXT=mp4
if /I "!DEBUG!"=="true" 1>&2 echo EXT=!EXT!
set FLAG=%3
if "!FLAG!"=="" goto :BEGIN
if /I "!DEBUG!"=="true" 1>&2 echo FLAG=%FLAG%
:BEGIN
REM Determine the video dimensions for conversion of high-res mp4 to lower res mkv, directly

REM dir /b/s *.!EXT!

PATH=%PATH%;C:\tools\ffmpeg-4.0.2-win64-static\bin

REM C:\tools\ffmpeg-4.0.2-win64-static\bin\ffprobe.exe
REM C:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe
set /A URL_COUNT=100
set /A RANDOM_INDEX=%RANDOM%
set /A RANDOM_INDEX=!RANDOM_INDEX! %% !URL_COUNT!
set /A RANDOM_INDEX=!RANDOM_INDEX! + 1

set TMPFILE=%TEMP%\a!RANDOM_INDEX!.txt
if /I "!DEBUG!"=="true" 1>&2 echo TMPFILE=%TMPFILE%
for /F "tokens=*" %%. in ('dir /b/s !NAME!.!EXT!') do (	
 >!TMPFILE! ffprobe.exe -select_streams v -show_streams "%%." 2>&1  
)
if /I "!DEBUG!"=="true"  (
1>&2 findstr.exe "\<width=" !TMPFILE!
1>&2 findstr.exe "\<height=" !TMPFILE!
)
set W=
set H=
for /F "tokens=2 delims==" %%. in ('findstr.exe "\<width=" !TMPFILE!') do set W=%%.

for /F "tokens=2 delims==" %%. in ('findstr.exe "\<height=" !TMPFILE!') do set H=%%.
set SIZE=!W!x!H!
if /I "!DEBUG!"=="true"  (
1>&2 echo SIZE=!SIZE!
)
1>&2 echo SIZE=!SIZE!
del /q %TMPFILE%
set SIZES="1024x768:svga 1024x576:hd480 1920x1080:qhd 800x600:spal 1278x718:qhd 1280x720:qhd 1280x800:852x532"
set SIZES=!SIZES:"=! 
if /I "!DEBUG!"=="true"  (
1>&2 echo !SIZES!
)
for %%. in  ( !SIZES! ) do (
for /F "tokens=1,2 delims=:" %%s in ('echo %%.') do (
if /I "!SIZE!" == "%%s" set NEWSIZE=%%t
)
)
echo !NEWSIZE!
REM 
REM Usage:
REM for /F %%. in ('call get_size.cmd !NAME! !EXT!' ) do set NEWSIZE=%%.
REM echo %NEWSIZE%
