@echo OFF
set CLASS=Main

if /I "%PROCESSOR_ARCHITECTURE%" neq "AMD64" echo OPENJAVAFX 32 bit is not working&& goto :EOF
call mvn -Pjdk11 package
echo Launching with classpath ^+ modulepath semantics
java -Dprism.order=sw -cp target\app-0.2.0-SNAPSHOT.jar;target\lib.* ^
--module-path target/lib ^
--add-modules=javafx.base,javafx.graphics,javafx.controls ^
example.applications.notaskbaricon.%CLASS%

REM Error: JavaFX runtime components are missing, and are required to run this application
REM Cannot fix
REM module javafx.graphics does not export com.sun.javafx.application
REM via module arguments
goto :EOF
