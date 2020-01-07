@echo OFF

call mvn clean package install
set TARGET=%CD%\target
set PACKAGE=sqlite_test
set CLASS=App
java -cp target\%PACKAGE%-0.2-SNAPSHOT.jar;target\lib\*  %PACKAGE%.%CLASS%
goto :EOF