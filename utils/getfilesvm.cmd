@echo OFF
@setlocal EnableDelayedExpansion EnableExtensions
set PROJECT=
for %%. in  (%CD%) do @call set PROJECT=%%~n.
echo Copying !PROJECT!
echo robocopy.exe \\vboxsrv\Downloads\!PROJECT! . /s  /XF %~nx0
robocopy.exe \\vboxsrv\Downloads\!PROJECT! . /s  /XF %~nx0
REM do not use console redirection
REM >NUL robocopy.exe \\vboxsrv\Downloads\!PROJECT! . /s /XF %~nx0
REM NOTE
REM 'opy' is not recognized as an internal or external command, operable program or batch file.
REM The system cannot find the path specified.
1>NUL 2>NUL call robocopy.exe \\vboxsrv\Downloads\!PROJECT! . /s
REM NOTE
REM copy *.*  E:\loadaverage-service
REM The system cannot find the drive specified.
REM copy : Cannot find drive. A drive with the name 'E' does not exist.
REM robocopy .  \\vboxsrv\Downloads\loadaverage-service /s


