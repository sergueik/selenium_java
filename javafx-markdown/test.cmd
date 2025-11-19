@echo OFF

REM WARNING: avoid naming this script "run.cmd"
set JAVA_VERSION=1.8.0_202
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
call c:\java\init.cmd
java.exe -version 2>&1 | findstr -ic:"%JAVA_VERSION% > NUL
where.exe mvn.cmd 2>NUL 1>NUL

if errorlevel 1 goto :SKIP
call mvn clean package
REM echo done
:SKIP
REM the *.* mask used here, there are 80+ dependencies in target\lib
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
