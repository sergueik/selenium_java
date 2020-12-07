@echo OFF
REM https://qna.habr.com/q/898855
REM https://stackoverflow.com/questions/355425/date-arithmetic-in-cmd-scripting/38531649
SETLOCAL ENABLEDELAYEDEXPANSION
SET delta=%1
IF "%delta%"=="" SET delta=-3
SET dir=%2
IF "%dir%"=="" SET dir=%TEMP%
FOR /f %%. IN ('cscript.exe /nologo add_days.js %delta%') DO SET CHECK_DATE=%%.
echo FORFILES /p %dir% /s /m *.* /d !CHECK_DATE! /c "cmd /c echo @FILE"
FORFILES /p %dir% /s /m *.* /d !CHECK_DATE! /c "cmd /c echo @FILE" 2>NUL 1>NUL
IF errorlevel 1 echo Skip Purge && GOTO :EOF
echo Call purge
echo FORFILES /p %dir% /s /m *.* /d %delta% /c "CMD /c echo del /Q @FILE
FORFILES /p %dir% /s /m *.* /d %delta% /c "CMD /c echo del /Q @FILE
GOTO :EOF

: /D    date Selects files with a last modified date greater
:            than or equal to (+), or less than or equal to
:            (-), the specified date using the
:            "MM/dd/yyyy" format; or selects files with a
:            last modified date greater than or equal to (+)
:            the current date plus "dd" days, or less than or
:            equal to (-) the current date minus "dd" days. A
:            valid "dd" number of days can be any number in
:            the range of 0 - 32768.
:            "+" is taken as default sign if not specified.
