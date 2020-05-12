@echo off

REM Extracting attribute from XML, without using ie 
REM http://www.cyberforum.ru/cmd-bat/thread1801597.html

setlocal enabledelayedexpansion

set /a fs=0
call :getDistFileSize "pom.xml"
echo FileSize: %fs%
goto end
 
:getDistFileSize
setlocal
for /f "usebackq delims=" %%i in ("%~1") do (
    set "xml=%%i"
    set "xml=!xml:>=>;!"
    rem echo xml=!xml!
    for %%j in ("!xml:;=";"!") do (
        echo:%%j|>nul findstr /irc:"artifactId" && echo %%i && (  for /f "tokens=1 delims=<" %%k in ( "%%~j" ) do set /a "fs=%%k" && endlocal  )
    )
)
echo fs=!fs!
goto :EOF
exit /b
 
:end

REM Looking for opening tag ignoring the identation
for /F "delims=" %%i in ( 'findstr "^ *<" pom.xml') do set "str=%%i"
    echo '1'
    echo str=!str!
    set "str=!str:>=>;!"
    echo str=!str!
    for %%j in ("!str:;=";"!") do (
      echo '2'
      echo:%%j | >nul findstr /irc:"/groupId" && (
        for /f "tokens=1 delims=<" %%k in ("%%~j" ) do set "len=%%k"
    echo len=!len!
      )
    )
    echo "!len!"
  endlocal
exit /b
