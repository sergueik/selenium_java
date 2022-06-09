@echo OFF
set LOG=a.log
cd c:\developer\sergueik\selenium_java\headless-chrome
1>NUL 2>NUL copy NUL %LOG%
echo running by %USERNAME% 
echo running by %USERNAME% >>%LOG%
echo checking display 
echo checking display >>%LOG%
call headless_detector.cmd >>%LOG%
echo %errorlevel% >>%LOG%
call headless_detector.cmd >>%LOG%
if ERRORLEVEL 1 set WINDOWS_NO_DISPLAY=true
REM this is fragile. 
REM It the system environment exist is will be used
echo WINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY%
echo WINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY% >>%LOG% 
set JAVA_TOOL_OPTIONS=-DWINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY%
set JAVA_TOOL_OPTIONS=-DWINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY% >>%LOG% 
call c:\java\init.cmd >>%LOG%
REM exit /b 0
call mvn -DWINDOWS_NO_DISPLAY=%WINDOWS_NO_DISPLAY% test >>%LOG%
echo Done. >>%LOG%
