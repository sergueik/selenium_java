@echo OFF
set CLASS=Main

if /I "%PROCESSOR_ARCHITECTURE%" neq "AMD64" echo OPENJAVAFX 32 bit is not working&& goto :EOF
call mvn -Pjdk11 package
echo Launching with classpath ^+ modulepath semantics
java -Dprism.order=sw -cp target\app-0.2.0-SNAPSHOT.jar;target\lib.* ^
--module-path target/lib ^
--add-modules=javafx.controls,javafx.base,javafx.graphics ^
 example.applications.notaskbaricon.%CLASS%
goto :EOF
