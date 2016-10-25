set PATH=%PATH%;c:\Windows\Microsoft.NET\Framework\v4.0.30319
set PATH=%PATH%;c:\Program Files\Microsoft Visual Studio 10.0\Common7\IDE
for /F "TOKENS=*" %%. in ('dir /b/ad "%USERNAME%_%COMPUTERNAME%*"') do @echo %%. & start cmd /c rd /s/q "%%."
call msbuild.exe generator.sln /t:clean,build
del /q %CD%\results.trx
call mstest.exe /testcontainer:Program\bin\Debug\Program.dll /resultsfile:%CD%\results.trx
goto :EOF