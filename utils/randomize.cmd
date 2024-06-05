@echo OFF

SETLOCAL ENABLEDELAYEDEXPANSION
set RUN=script
for /L %%. in ( 1 1 4) do set /a N=!RANDOM! %% 10 + 1&& echo start cmd /c %RUN% !N!
goto :EOF
