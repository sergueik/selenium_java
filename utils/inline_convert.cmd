@echo OFF
REM  based on https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats

for /F "tokens=*" %%.  in ('dir /b *.mp4') do echo converting %%. && ffmpeg.exe -i "%%." -c:v vp9  -s hd480 -v 0 "%%~n..mkv"