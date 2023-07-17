@echo OFF

SETLOCAL  ENABLEDELAYEDEXPANSION
:BEGIN
set SEARCH_PATH=%~1
set REPLACE=%~2
set SEARCH_PATH=!SEARCH_PATH: =__!
echo SEARCH_PATH=!SEARCH_PATH!
set REPLACE=!REPLACE: =__!
echo REPLACE="!REPLACE!"
echo powershell.exe -executionpolicy bypass "&{ param([String]$search_path,[String]$replace,[switch] $debug) write-output ('search_path={0} replace={1}' -f ($search_path -replace '__' , ' '), ($replace -replace '__', ' ') ) }" -search_path "!SEARCH_PATH!" -replace "!REPLACE!"

REM passing argument with spaces does not work, need to substitute with e.g. double underscore
call powershell.exe -executionpolicy bypass "&{ param([String]$search_path,[String]$replace,[switch] $debug) write-output ('search_path={0} replace={1}' -f ($search_path -replace '__' , ' '), ($replace -replace '__', ' ') ) }" -search_path "!SEARCH_PATH!" -replace "!REPLACE!"

REM replace does not work
powershell.exe -executionpolicy bypass "&{ param([String]$p,[String]$r,[switch] $d) $r = $r -replace '__', '' ; cd $p; get-childitem .|select-object -expandproperty name|foreach-object{ $n1 = $_; $n2 = $n1 -replace "$r",''; write-output ('{0} {1}' -f $n1, $n2) } }" -p "!SEARCH_PATH!" -r "!REPLACE!"

REM write the arguments into the script directly
set SEARCH_PATH=%~1
set REPLACE=%~2
powershell.exe -executionpolicy bypass "&{ cd '!SEARCH_PATH!'; get-childitem .|select-object -expandproperty name | foreach-object { $n1 = $_; $n2 = $n1 -replace '!REPLACE!',''; write-output ('{0} {1}' -f $n1, $n2); if ($n2 -ne $n1 ) { rename-item -newname $n2 -LiteralPath $n1 } } }" 

echo. 

goto :EOF

endlocal
