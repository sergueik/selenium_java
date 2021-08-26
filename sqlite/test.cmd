@echo OFF

call mvn  -Dmaven.test.skip=true clean install
set TARGET=%CD%\target
set GROUP=example
set ARTIFACT=sqlite
set PACKAGE=%GROUP%.%ARTIFACT%

set CLASS=%1
if /I "%CLASS%" equ "" (  for %%. in  (MemoryDatabase FileDatabase) do call :TEST %%. ) else call :TEST %CLASS%

goto :EOF
:TEST
set CLASS=%1
echo testing %CLASS%
java -cp %TARGET%\%PACKAGE%.jar;%TARGET%\lib\*  %GROUP%.%CLASS%

goto :EOF