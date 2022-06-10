@echo OFF
set LOG=a.log
cd %~dp0
REM cd c:\developer\sergueik\selenium_java\headless-chrome
1>NUL 2>NUL copy NUL %LOG%
echo running by %USERNAME% >>%LOG%
echo checking display >>%LOG%
REM sourcing headless_detector.cmd
headless_detector.cmd >>%LOG%
echo WINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY% >>%LOG% 
set JAVA_TOOL_OPTIONS=-DWINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY% >>%LOG% 
call c:\java\init.cmd >>%LOG%
REM exit /b 0
REM https://docs.microsoft.com/en-US/troubleshoot/developer/visualstudio/cpp/language-compilers/redirecting-error-command-prompt
call mvn test >>%LOG% 2>&1
echo Done. >>%LOG%
