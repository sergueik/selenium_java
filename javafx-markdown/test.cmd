@echo OFF
REM do not name this script run.cmd
where.exe mvn.cmd 2>NUL 1>NUL
if errorlevel 1 goto :SKIP
call mvn package
echo done
:SKIP
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
REM java -jar target\example.javafx_markdown.jar
