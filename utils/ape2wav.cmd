@echo OFF
SETLOCAL ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

REM https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
REM https://askubuntu.com/questions/376684/how-to-use-ffmpeg-to-convert-ape-to-mp3
REM simplest command, no options
REM for /F "tokens=*" %%.  in ('dir /b *.ape') do call ffmpeg.exe -i "%%." "%%~n..wav"
REM NOTE: many programs install the copy of ffmpeg.exe
REM "c:\Progrdam Files (x86)\Ffmpeg For Audacity\ffmpeg.exe" -v
REM "c:\Program Files\CamStudio 2.7\ffmpeg.exe" -h
REM "c:\tools\ffmpeg-20160714-f41e37b-win64-static\bin\ffmpeg.exe" -v
REM "c:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe"
REM download latest from https://www.ffmpeg.org/download.html#build-windows
REM https://www.gyan.dev/ffmpeg/builds/ffmpeg-git-essentials.7z
set EXTENSION=%1
if /i "%EXTENSION%" EQU "" set EXTENSION=ape

REM Visit subdirectories containg files  with EXTENSION, only once

for /d /r %%d in (*) do (
    if exist "%%d\*.%EXTENSION%" (
        echo Found .%EXTENSION% in: %%d
	pushd %%d
	call :CONVERT
	popd

    )
)
call :CONVERT
goto :EOF
REM
:CONVERT
REM Never use for /F to enumerate files
REM The for /F is one CMD construct that must never touch Unicode filenames:
REM dir outputs Unicode filenames in OEM code page
REM Unicode filename
REM converted to OEM code page (chcp)
REM printed to stdout
REM e.g. long dash U+2013 becomes a regular dash - 0x002D
REM or a visually similar glyph with wrong code point
REM on contrast, for %%F in (*)
REM does NT kernel enumeration
REM while the inner for /F
REM is parsing ASCII only
for %%. in (*.%EXTENSION%) do (
  echo Converting %%.
  call "c:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe" -i "%%." -v quiet "%%~n..wav
)

goto :EOF