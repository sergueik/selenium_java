@echo off
set SCRIPT_NAME=%~dp0restore.ps1
powershell.exe -ExecutionPolicy Bypass -File %SCRIPT_NAME% %*
exit /b %errorlevel%
goto :EOF
echo restore.cmd sergueik springboot_study basic-karate-example3 master
