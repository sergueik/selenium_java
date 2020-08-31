@echo OFF
REM # origin: http://www.outsidethebox.ms/19453/
REM # only relevant for one-liners - poor readability
REM cd /d %USERPROFILE%\Desktop\Private\Chess && powershell.exe -ExecutionPolicy Bypass -noprofile -command c:\tools\youtube-dl.exe $((get-clipboard).split('^&')[0])
REM NOTE: would fail when there is nothing in the clipboard buffer
cd /d %USERPROFILE%\Desktop\Private\Chess && powershell.exe -ExecutionPolicy Bypass -noprofile -command c:\tools\youtube-dl.exe "$((get-clipboard).split([char]38)[0])"
