@echo OFF
set CLASS=ChartEx
set RESOURCE=%~1
if "%RESOURCE%" equ "" set RESOURCE=data.json
REM  e.g. invoke as
REM run.cmd "" gaps
REM to pass the second argument
set TARGET=%2
if "%TARGET%" equ "" set TARGET=test

call mvn package

REM if /I "%PROCESSOR_ARCHITECTURE%" neq "AMD64" echo OPENJAVAFX 32 bit is not working&& goto :EOF
java -cp target\treeview.jar;target\lib\* example.%CLASS% -target %TARGET% -resource %RESOURCE% -type json