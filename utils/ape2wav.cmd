@echo OFF
REM https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
REM https://askubuntu.com/questions/376684/how-to-use-ffmpeg-to-convert-ape-to-mp3
REM simplest command, no options
REM for /F "tokens=*" %%.  in ('dir /b *.ape') do call ffmpeg.exe -i "%%." "%%~n..wav"
REM NOTE: many programs install the copy of ffmpeg.exe
REM "c:\Progrdam Files (x86)\Ffmpeg For Audacity\ffmpeg.exe" -v
REM "c:\Program Files\CamStudio 2.7\ffmpeg.exe" -h
REM "c:\tools\ffmpeg-20160714-f41e37b-win64-static\bin\ffmpeg.exe" -v
REM "c:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe"

for /F "tokens=*" %%.  in ('dir /b *.ape') do call "c:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe" -i "%%." "%%~n..wav"
