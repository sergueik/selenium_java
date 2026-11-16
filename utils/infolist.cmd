@echo off
setlocal EnableDelayedExpansion

PATH=%PATH%;C:\tools\ffmpeg-4.0.2-win64-static\bin

set "CSV=inventory.csv"
set "LOG=%TEMP%\resampler-probe.txt"

echo name,codec_name,sample_rate,channels,bits_per_raw_sample,sample_fmt,duration,bit_rate>"%CSV%"

for %%F in (*.flac) do (
    call :info "%%~nxF"
)

goto :EOF

:info
set "FILENAME=%~1"

ffprobe.exe -v error -select_streams a:0 ^
  -show_entries stream=codec_name,sample_rate,channels,bits_per_raw_sample,sample_fmt ^
  -show_entries format=duration,bit_rate ^
  -of default=noprint_wrappers=1 "%FILENAME%" >"%LOG%" 2>&1

set "codec_name="
set "sample_rate="
set "channels="
set "bits_per_raw_sample="
set "sample_fmt="
set "duration="
set "bit_rate="

for /F "tokens=1,* delims==" %%A in (%LOG%) do (
    set "%%A=%%B"
)

>>"%CSV%" echo("%FILENAME%",!codec_name!,!sample_rate!,!channels!,!bits_per_raw_sample!,!sample_fmt!,!duration!,!bit_rate!
goto :EOF
