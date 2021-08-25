@echo OFF

call mvn  -Dmaven.test.skip=true clean install
set TARGET=%CD%\target
set PACKAGE=sqlite_test
set CLASS=%1
if /I "%CLASS%" equ "" (  for %%. in  (MemoryDatabase FileDatabase) do call :TEST %%. ) else call :TEST %CLASS%

goto :EOF
:TEST
set CLASS=%1
echo testing %CLASS%
java -cp target\%PACKAGE%-0.2-SNAPSHOT.jar;target\lib\*  %PACKAGE%.%CLASS%

goto :EOF