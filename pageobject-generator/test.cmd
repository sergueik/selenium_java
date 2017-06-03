@echo OFF
call mvn clean package install
set TARGET=%CD%\target

java -cp target\generator-1.0.0-SNAPSHOT.jar;c:\java\selenium\selenium-server-standalone-2.53.0.jar;target\lib\*  io.pageobject.generator.PageObjectGeneratorGui

goto :EOF

