@echo off
set EXTENSION=flac
set EXTENSION=wv
setlocal EnableDelayedExpansion

PATH=%PATH%;C:\tools\ffmpeg-4.0.2-win64-static\bin

for /F "tokens=*" %%_ in ('dir /b *.%EXTENSION%') do (
set F=%%_
echo name=!F!
call :info "!f!"
echo.
)
goto :EOF
:info
set FILENAME=%~1
call 2>%TEMP%\LOG.TXT 1>&2 ffprobe.exe -v error -select_streams a:0 ^
  -show_entries stream=codec_name,sample_rate,channels,bits_per_raw_sample,sample_fmt ^
  -show_entries format=duration,bit_rate ^
 -of default=noprint_wrappers=1 "%FILENAME%"
type %TEMP%\LOG.TXT
goto :EOF


