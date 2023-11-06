@echo OFF

SETLOCAL ENABLEDELAYEDEXPANSION
set RUN=script
for /L %%. in ( 1 1 4) do (
 call set /a N=!RANDOM!
 echo start cmd /c %RUN%  !N!
 set /a N=!N! %% 10
 echo start cmd /c %RUN%  !N!
)
goto :EOF
