@echo OFF
REM WARNING: avoid naming this script "run.cmd"
REM The 32 or 64 bit Azul JDK will be installed in the same directory
if NOT EXIST "c:\java\zulu-jdk11" (
    echo Directory c:\java\zulu-jdk11 does not exist.
    goto :USE_JDK8
)

REM the original launcher does not work with OpenJDK 11 due to 
REM its module boundaries
REM if JDK 11 is not the default on the host, set it to custom vendor JDK 
REM which has JAVAFX  now


set JAVA_VERSION=
set JAVA_HOME=c:\java\zulu-jdk11

REM if JAVA_HOME points to an empty / non existend directory 
REM script fails with error:
REM The JAVA_HOME environment variable is not defined correctly
REM This environment variable is needed to run this program
REM NB: JAVA_HOME should point to a JDK not a JRE
goto :INIT
:USE_JDK8

set JAVA_HOME=c:\java\jdk1.8.0_202
:INIT
set JAVA_VERSION=1.8.0_202
call c:\java\init.cmd
java.exe -version
where.exe mvn.cmd 2>NUL 1>NUL

call mvn -f %~dp0pom-java11.xml clean package
echo done
:SKIP

echo Launching with classpath ^+ modulepath semantics
java -Dprism.order=sw -cp target\example.javafx_markdown.jar;target\lib\*.* ^
--module-path target/lib ^
--add-modules=javafx.base,javafx.graphics,javafx.controls ^
example.Example
REM java.lang.module.FindException: Module org.fxmisc.cssfx not found
REM Caused by: java.lang.NoClassDefFoundError: org/fxmisc/cssfx/CSSFX
REM fixing the Error:
REM JavaFX runtime components are missing, and are required to run this application

