@echo off
REM  replacing text of a specific node with CMD
setlocal enableextensions enabledelayedexpansion
set FILE=file.xml
set RESULT=file.xml.updated
set TAG=^<server^>
set TEXT=%COMPUTERNAME%
for /f "delims=" %%a in ('more %FILE%') do @(
  echo "%%~a"|find /i "%TAG%" >nul   
  if ERRORLEVEL 1 @echo %%a >> %RESULT% && goto :NEXTLINE
  for /f "tokens=1,3 delims=><" %%b in ("%%~a") do @echo:^<%%b^>%TEXT%^<%%c^> >> %RESULT%
:NEXTLINE
 )
move /y %RESULT% "%FILE%" > NUL
goto :EOF

REM somewhat weirdified syntax
REM origin http://forum.oszone.net/thread-322910.html
set "FILE=file.xml"
set "RESULT=file.xml.updated"
set "TAG=<server>"
set "TEXT=%COMPUTERNAME%"
<"%FILE%">%result% (for /f "delims=" %%a in ('more') do @(
  echo "%%~a"|>nul find /i "%TAG%" && (
   for /f "tokens=1,3 delims=><" %%b in ("%%~a") do @echo:^<%%b^>%TEXT%^<%%c^>
  ) || (
   echo %%a
  )
 )
)& >nul move %RESULT% "%FILE%"
goto :EOF
exit
