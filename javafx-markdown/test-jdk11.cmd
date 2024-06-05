@echo OFF
REM avoid naming this script "run.cmd"
where.exe mvn.cmd 2>NUL 1>NUL
if errorlevel 1 goto :SKIP

REM the original launcher does not work with JDK 11 due to its module boundaries
REM if JDK 11 is not the default on the host, set it to custom vendor JDK now
set JAVA_HOME=c:\java\zulu11.45.27-ca-jdk11.0.10-win_x64
call c:\java\init.cmd

call mvn -f %~dp0pom-java11.xml clean package
echo done
:SKIP

echo Launching with classpath ^+ modulepath semantics
java -Dprism.order=sw -cp target\example.javafx_markdown.jar;target\lib\*.* ^
--module-path target/lib ^
--add-modules=javafx.base,javafx.graphics,javafx.controls ^
example.Example

REM fixing the Error:
REM JavaFX runtime components are missing, and are required to run this application

