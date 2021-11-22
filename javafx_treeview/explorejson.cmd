@echo OFF

REM based on https://stackoverflow.com/questions/6711002/how-can-i-get-javascript-to-read-from-a-json-file


:EXPLORE

set RESOURCE=%~1
set DEFAULT_RESOURCE=data.json
if "%RESOURCE%" equ "" set RESOURCE=%DEFAULT_RESOURCE%
if NOT EXIST "%RESOURCE%"  echo Report does not exist %RESOURCE% && exit /b 1
SETLOCAL enableDelayedExpansion

if "%DEBUG%" neq "" set DEBUG=true

set TMPFILE=%TEMP%\script%RANDOM%.html
set DATAFILE=%TEMP%\script%RANDOM%.json
echo>>%DATAFILE% var j =
type %RESOURCE% >>%DATAFILE%
echo ;>>%DATAFILE%
set DATAFILE_REF=%DATAFILE:\=\\%
echo. >%TMPFILE%
REM the HTML has to  have redirection at the end of the line
echo ^<script src="file://%DATAFILE_REF%"^>^</script^> >>%TMPFILE%
echo ^<script language="javascript" type="text/javascript"^> >>%TMPFILE%
echo>>%TMPFILE% var o = new ActiveXObject('Scripting.FileSystemObject').GetStandardStream(1);
echo>>%TMPFILE% for (i in j) { var t = j[i]['target']; o.Write( '"' + t + '"\n'); }	window.close();
echo ^</script^>>>%TMPFILE%
if /i "%DEBUG%" equ "true" 1>&2 echo TMPFILE=%TMPFILE%
if /i "%DEBUG%" equ "true" 1>&2 type %TMPFILE%


REM collect the output from mstha.exe
for /f "tokens=* delims=" %%_ in ('mshta.exe "%TMPFILE%"') do echo %%_
if ERRORLEVEL 1 echo Error processing %RESOURCE% && exit /b 1
del /q %TMPFILE%
del /q %DATAFILE%
goto :EOF
