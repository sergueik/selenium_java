@echo OFF

REM WARNING: avoid naming this script "run.cmd"
REM if JDK 11 is not the default on the host, set it to JDK 1.8 now
set JAVA_HOME=c:\java\jdk1.8.0_202
set JAVA_VERSION=1.8.0_202
call c:\java\init.cmd
java.exe -version
where.exe mvn.cmd 2>NUL 1>NUL

if errorlevel 1 goto :SKIP
call mvn clean package
echo done
:SKIP
REM the *.* mask should not be used here
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
REM java -jar target\example.javafx_markdown.jar

