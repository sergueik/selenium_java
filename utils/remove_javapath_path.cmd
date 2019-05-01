@echo OFF
setlocal enabledelayedexpansion

REM for /F %%a in ('echo %PATH%') do echo %%a
REM s\Lenovo\FusionEngine was unexpected at this time.
for /F "tokens=2 delims==" %%_ in ('set PATH^|findstr -i "PATH="') do set p=%%_

REM
if "%DEBUG%" equ "true" call c:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe -executionpolicy bypass "$p = ($env:path -split ';' |  where-object {$_ -match 'Oracle' -and $_ -match 'ProgramData' });write-output $p[0]"
set PATH=%PATH%;c:\Windows\System32\WindowsPowerShell\v1.0
for /F "usebackq tokens=*" %%_ in (`call powershell.exe -executionpolicy bypass "$p = $env:path -split ';' |  where-object {$_ -match 'Oracle' -and $_ -match 'ProgramData' };write-output $p"`)  do set b=%%_
if NOT "!b!" equ "" echo Need to remove the following from the PATH: "!b!"

goto :EOF
REM NOTE: the path need to be fixed front way - appending does not resolve legacy javaw.exe from being used
REM where.exe javaw.exe
REM C:\ProgramData\Oracle\Java\javapath\javaw.exe
REM C:\java\jdk1.8.0_101\bin\javaw.exe
REM attempt to run C:\ProgramData\Oracle\Java\javapath\javaw.exe
REM  leads to "could not find java.dll"  and "could not find Java SE runtile environment" dialogs
REM
REM  Directory of c:\ProgramData\Oracle\Java
REM
REM 12/13/2018  05:31 PM    <DIR>          .
REM 12/13/2018  05:31 PM    <DIR>          ..
REM 12/13/2018  06:29 PM    <DIR>          .oracle_jre_usage
REM 12/13/2018  05:28 PM    <DIR>          installcache_x64
REM 11/20/2016  04:27 PM                14 java.settings.cfg
REM 12/13/2018  05:28 PM    <JUNCTION>     javapath [C:\ProgramData\Oracle\Java\java
REM path_target_259475906]
REM 12/13/2018  05:28 PM    <DIR>          javapath_target_259475906
REM                1 File(s)             14 bytes
REM                6 Dir(s)  96,275,009,536 bytes free
REM 	

call :GET_FIRST_TOKEN !EXAMPLE!

goto :EOF

set EXAMPLE=C:\Users\Serguei\bin;C:\Program Files\Git\mingw64\bin;C:\Program Files\Git\usr\local\bin;C:\Program Files\Git\usr\bin;C:\Program Files\Git\usr\bin
call :GET_FIRST_TOKEN !EXAMPLE!

goto :EOF

:GET_FIRST_TOKEN

REM NOTE: the "for" loop with "delims=;" will not work as expected.
REM for /F "tokens=1,2 delims= " %a in ('echo a1 b2 c3 ') do echo %b
REM echo b2
REM c:\java\eclipse>for /F "tokens=1,2 delims=;" %a in ('echo a1;b2;c3 ') do echo %b
REM echo
REM ECHO is on.

set p1=%1

if  "!p1!" equ "" goto :EOF
echo !p1!

for /F "tokens=1,2 delims=;" %%i in ('echo !p1!') do (
echo "i="%%i""
echo "j="%%j""
set d=%%i

set p1=%%j
echo "d="!d!""
echo "p1="!p1!""
call :GET_FIRST_TOKEN !p1!

)
goto :EOF

