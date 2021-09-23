@echo OFF
set CLASS=ChartEx
set RESOURCE=%~1
if "%RESOURCE%" equ "" set RESOURCE=data.json
REM  e.g. invoke as
REM run.cmd "" gaps
REM to pass the second argument
set TARGET=%2
if "%TARGET%" equ "" set TARGET=test
if "%TARGET%" equ "?" call :EXPLORE && exit /b 0

call mvn package

REM if /I "%PROCESSOR_ARCHITECTURE%" neq "AMD64" echo OPENJAVAFX 32 bit is not working&& goto :EOF
java -cp target\treeview.jar;target\lib\* example.%CLASS% -target %TARGET% -resource %RESOURCE% -type json
goto :EOF
:EXPLORE
REM based on: https://stackoverflow.com/questions/44547979/batch-parsing-json-file-with-colons-in-value

SETLOCAL enableDelayedExpansion

if "%DEBUG%" equ "" set DEBUG=false
if "%DEBUG%" equ "1" set DEBUG=true

set DATAFILE=%1
set DEFAULT_DATAFILE=data.json
if "%DATAFILE%" equ "" set DATAFILE=%DEFAULT_DATAFILE%
set CURRENT_DIR=%CD%

REM Extension should be "cmd"

if "%DEBUG%" equ "" set DEBUG=false

set GENERATED_SCRIPT=%TEMP%\script%RANDOM%.cmd
if /i "%DEBUG%" equ "true" 1>&2 echo Generating %GENERATED_SCRIPT%
REM https://docs.microsoft.com/en-us/previous-versions/ms536494(v=vs.85)
REM NOTE: the link https://samples.msdn.microsoft.com/workshop/samples/author/hta/hta_allex.hta is 404
echo. >%GENERATED_SCRIPT%
echo ^<HTA:Application ShowInTaskbar=no WindowsState=Minimize SysMenu=No ShowInTaskbar=No Caption=No Border=Thin^>>>%GENERATED_SCRIPT%
echo ^<^^!-- TODO^: switch IE to standards-mode by adding a valid doctype. --^>>>%GENERATED_SCRIPT%
echo ^<meta http-equiv="x-ua-compatible" content="ie=edge" /^>>>%GENERATED_SCRIPT%
echo ^<script language="javascript" type="text/javascript"^>>>%GENERATED_SCRIPT%
echo>>%GENERATED_SCRIPT% window.visible = false;
echo>>%GENERATED_SCRIPT% var debug = false;
echo>>%GENERATED_SCRIPT% var _out = new ActiveXObject('Scripting.FileSystemObject').GetStandardStream(1);
echo>>%GENERATED_SCRIPT% var _in = new ActiveXObject('Scripting.FileSystemObject').GetStandardStream(0).ReadLine();
echo>>%GENERATED_SCRIPT% var _fh = new ActiveXObject("Scripting.FileSystemObject").OpenTextFile(_in, 1);
echo>>%GENERATED_SCRIPT% var json = JSON.parse(_fh.ReadAll()); _fh.Close();
echo>>%GENERATED_SCRIPT% for (i in json) {
echo>>%GENERATED_SCRIPT% var target = json[i]['target'];
echo>>%GENERATED_SCRIPT%    _out.Write( 'target: ' + target + '\n');
echo>>%GENERATED_SCRIPT% }	
echo>>%GENERATED_SCRIPT% window.close();
echo ^</script^>>>%GENERATED_SCRIPT%
if /i "%DEBUG%" equ "true" 1>&2 type %GENERATED_SCRIPT%

if /i "%DEBUG%" equ "true" 1>&2 echo Parsing "%DATAFILE%"
if NOT EXIST "%DATAFILE%"  echo Report does not exist %DATAFILE% && exit /b 1
if /i "%DEBUG%" equ "true" 1>&2 echo echo %DATAFILE%^|mshta.exe "%GENERATED_SCRIPT%"

REM collect the output from mstha.exe
for /f "tokens=* delims=" %%_ in ('echo %DATAFILE%^|mshta.exe "%GENERATED_SCRIPT%"') do echo %%_
if ERRORLEVEL 1 echo Error processing %DATAFILE% && exit /b 1
del /q %GENERATED_SCRIPT%
goto :EOF
