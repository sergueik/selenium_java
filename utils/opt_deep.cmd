@echo OFF
set DEEP=%1
set SOURCEPATH=.
set OPT=
if "%DEEP%" equ "1" set OPT=/R "%SOURCEPATH%"
echo OPT=%OPT%

REM Walks the directory tree %SOURCEPATH%, executing the FOR statement in each directory of the tree
REM or just the current directory when the DEEP option is not set
for %OPT% %%_ in (*.flac) do (
   echo %%_
)
