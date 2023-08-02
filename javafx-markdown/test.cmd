@echo OFF

REM Does not work with JDK 11
set JAVA_HOME=c:\java\zulu11.45.27-ca-jdk11.0.10-win_x64
call c:\java\init.cmd
REM do not name this script run.cmd
where.exe mvn.cmd 2>NUL 1>NUL
if errorlevel 1 goto :SKIP
call mvn package
echo done
:SKIP
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
REM java -jar target\example.javafx_markdown.jar
