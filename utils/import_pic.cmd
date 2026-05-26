@echo OFF
SETLOCAL ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

REM https://xiph.org/flac/download.html
REM curl -skLO ~/Downloads/flac-1.4.3-win.zip https://ftp.osuosl.org/pub/xiph/releases/flac/flac-1.4.3-win.zip
REM unzip -d /c/tools/flac -x ~/Downloads/flac-1.4.3-win.zip flac-1.4.3-win/Win64/*
REM Archive:  /c/Users/kouzm/Downloads/flac-1.4.3-win.zip
REM    creating: /c/tools/flac/flac-1.4.3-win/Win64/
REM   inflating: /c/tools/flac/flac-1.4.3-win/Win64/flac.exe
REM   inflating: /c/tools/flac/flac-1.4.3-win/Win64/libFLAC++.dll
REM   inflating: /c/tools/flac/flac-1.4.3-win/Win64/libFLAC.dll
REM   inflating: /c/tools/flac/flac-1.4.3-win/Win64/metaflac.exe
REM 
REM pushd /c/tools/flac
REM mv flac-1.4.3-win/Win64/* .
REM popd

set PICTURE=%1
if /i "%PICTURE%" EQU "" set PICTURE=folder.jpg
set EXTENSION=flac
set PATH=%PATH%;c:\TOOLS\FLAC
:CONVERT
for /F "tokens=*" %%.  in ('dir /b *.%EXTENSION%') do echo Importing picture into %%. && call metaflac.exe --import-picture-from=%PICTURE% "%%."
goto :EOF

