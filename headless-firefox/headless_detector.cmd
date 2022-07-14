@echo OFF
setlocal ENABLEDELAYEDEXPANSION

REM see also:
REM https://community.idera.com/database-tools/powershell/ask_the_experts/f/powershell_for_windows-12/19845/get-process-user-filtering
REM assume headless environment
set WINDOWS_NO_DISPLAY=true
echo setting WINDOWS_NO_DISPLAY to !WINDOWS_NO_DISPLAY!
REM check if explorer.exe is running for the current user
for /F %%. in ('tasklist.exe /FI "USERNAME eq %USERNAME%" /FI "IMAGENAME EQ explorer.exe"^| findstr.exe /ic:"explorer"') do set WINDOWS_NO_DISPLAY=&& echo clearing WINDOWS_NO_DISPLAY&& exit /b 0
REM alternatively set to false
exit /b 1

REM Usage:
REM
REM call headless_detector.cmd
REM echo %WINDOWS_NO_DISPLAY%

