@echo OFF

call mvn -Dmaven.test.skip=true clean install
set TARGET=%CD%\target
set GROUP=example
set ARTIFACT=sqlite
set PACKAGE=%GROUP%.%ARTIFACT%

set CLASS=%1
echo CLASS=[%CLASS%]
if /I "%CLASS%" equ "" (  for %%. in  (MemoryDatabase FileDatabase) do call :TEST %%. ) else call :TEST %CLASS%

goto :EOF
:TEST
set CLASS=%1
echo testing %CLASS%
java -cp %TARGET%\%PACKAGE%.jar;%TARGET%\lib\*  %GROUP%.%CLASS%
if "%CLASS%" equ "FileDatabase" ( sqlite3.exe C:\Users\Serguei\test.db "SELECT ROWID, NAME, ID, AGE FROM COMPANY;" )
REM NOTE when closing parenthesis is absent 
REM Curiously it has effect on line 11: make the script never execute the following lines
echo Done
goto :EOF