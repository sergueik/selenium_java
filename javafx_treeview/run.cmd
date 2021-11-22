@echo OFF
set CLASS=ChartEx
set RESOURCE=%~1
if "%RESOURCE%" equ "" set RESOURCE=data.json
REM  e.g. invoke as
REM run.cmd "" gaps
REM to pass the second argument
set TARGET=%2
if "%TARGET%" equ "" set TARGET=test
if "%TARGET%" equ "?" call :EXPLORE %RESOURCE% && exit /b 0

call mvn package

REM if /I "%PROCESSOR_ARCHITECTURE%" neq "AMD64" echo OPENJAVAFX 32 bit is not working&& goto :EOF
java -cp target\treeview.jar;target\lib\* example.%CLASS% -target %TARGET% -resource %RESOURCE% -type json
goto :EOF

:EXPLORE
REM based on: https://stackoverflow.com/questions/44547979/batch-parsing-json-file-with-colons-in-value

set RESOURCE=%~1
set DEFAULT_RESOURCE=data.json
if "%RESOURCE%" equ "" set RESOURCE=%DEFAULT_RESOURCE%
if /i "%DEBUG%" equ "true" 1>&2 echo Parsing "%RESOURCE%"

REM if jq.exe is available use it
set JQ=jq-win64.exe
>NUL 2>NUL where.exe %JQ%
if ERRORLEVEL 1 goto :NOJQ
type %RESOURCE% | %JQ% ".[].target"
goto :EOF
:NOJQ

SETLOCAL enableDelayedExpansion
if "%DEBUG%" neq "" set DEBUG=true

set TMPFILE=%TEMP%\script%RANDOM%.html

REM NOTE temporaty page file extension does not have to be "cmd"

if /i "%DEBUG%" equ "true" 1>&2 echo Generating %TMPFILE%

REM https://docs.microsoft.com/en-us/previous-versions/ms536494(v=vs.85)
REM NOTE: the link https://samples.msdn.microsoft.com/workshop/samples/author/hta/hta_allex.hta is 404

echo. >%TMPFILE%
echo ^<HTA:Application ShowInTaskbar=no WindowsState=Minimize SysMenu=No ShowInTaskbar=No Caption=No Border=Thin^>>>%TMPFILE%
echo ^<^^!-- TODO^: switch IE to standards-mode by adding a valid doctype. --^>>>%TMPFILE%
echo ^<meta http-equiv="x-ua-compatible" content="ie=edge" /^>>>%TMPFILE%
echo ^<script language="javascript" type="text/javascript"^>>>%TMPFILE%
echo>>%TMPFILE% window.visible = false;
echo>>%TMPFILE% var debug = false;
echo>>%TMPFILE% var _out = new ActiveXObject('Scripting.FileSystemObject').GetStandardStream(1);
echo>>%TMPFILE% var _in = new ActiveXObject('Scripting.FileSystemObject').GetStandardStream(0).ReadLine();
echo>>%TMPFILE% var _fh = new ActiveXObject("Scripting.FileSystemObject").OpenTextFile(_in, 1);
echo>>%TMPFILE% var json = JSON.parse(_fh.ReadAll()); _fh.Close();
echo>>%TMPFILE% for (i in json) {
echo>>%TMPFILE% var target = json[i]['target'];
echo>>%TMPFILE%    _out.Write( '"' + target + '"\n');
echo>>%TMPFILE% }	
echo>>%TMPFILE% window.close();
echo ^</script^>>>%TMPFILE%
if /i "%DEBUG%" equ "true" 1>&2 type %TMPFILE%

if NOT EXIST "%RESOURCE%"  echo Report does not exist %RESOURCE% && exit /b 1
if /i "%DEBUG%" equ "true" 1>&2 echo echo %RESOURCE%^|mshta.exe "%TMPFILE%"

for /f "tokens=* delims=" %%_ in ('echo %RESOURCE%^|mshta.exe "%TMPFILE%"') do echo %%_
if ERRORLEVEL 1 echo Error processing %RESOURCE% && exit /b 1
del /q %TMPFILE%
goto :EOF
