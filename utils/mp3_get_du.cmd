@echo OFF
SETLOCAL ENABLEDELAYEDEXPANSION
REM see also https://qna.habr.com/q/1245564
REM for ffmpeg options see
REM https://gist.github.com/tayvano/6e2d456a9897f55025e25035478a3a50
REM https://ffmpeg.org/ffmpeg.html
if "!DEBUG!"=="" set DEBUG=false
set INPUTFILE=%~1
if "!INPUTFILE!"=="" set INPUTFILE=a.mp3
if /I "!DEBUG!"=="true" echo "!INPUTFILE!"
set IMAGEFILE=%~2
if "!IMAGEFILE!"=="" set IMAGEFILE=folder.jpg
if /I "!DEBUG!"=="true" echo "!IMAGEFILE!"
set RESULTFILE=%~3
if "!RESULTFILE!"=="" set RESULTFILE=result.mp3
if /I "!DEBUG!"=="true" echo ffmpeg.exe -i "!INPUTFILE!" 2^>^&1
REM if /I "!DEBUG!"=="true" ffmpeg.exe -i "!INPUTFILE!" 2>&1
if /I "!DEBUG!"=="true" ffmpeg.exe -i "!INPUTFILE!" 2>&1 | findstr "Duration"
REM if /I "!DEBUG!"=="true"  goto :EOF
REM example output
REM Duration: 00:05:22.17, start: 0.000000, bitrate: 326 kb/s

for /F "tokens=2" %%. in ('ffmpeg.exe -i "!INPUTFILE!" 2^>^&1 ^| findstr "Duration"') do SET RESULT=%%.
if /I "!DEBUG!"=="true" echo !RESULT!
set HOURS=!RESULT:~0,2!
set MINUTES=!RESULT:~3,2!
set SECONDS=!RESULT:~6,2!


if /I "!DEBUG!"=="true" echo !HOURS!
if /I "!DEBUG!"=="true" echo !MINUTES!
if /I "!DEBUG!"=="true" echo !SECONDS!

REM
set /A DURATION=!HOURS! * 3600 + !MINUTES! * 60 + !SECONDS!
if /I "!DEBUG!"=="true" echo !DURATION!
REM
echo ffmpeg.exe -y -loop 1 -i "!IMAGEFILE!" -i "!INPUTFILE!" -r 30 -c:v libx264 -crf 18 -c:a aac -b:a 256k -t !DURATION! "!RESULTFILE!"
REM

