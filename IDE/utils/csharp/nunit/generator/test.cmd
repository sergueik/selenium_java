set NUNIT_VERSION=2.6.3
set PATH=%PATH%;c:\Windows\Microsoft.NET\Framework\v4.0.30319
set PATH=%PATH%;c:\Program Files\Microsoft Visual Studio 10.0\Common7\IDE
set PATH=%PATH%;c:\Program Files\NUnit %NUNIT_VERSION%\bin
call msbuild.exe generator.sln /t:clean,build
call nunit-console.exe Sample\bin\Debug\Sample.dll
goto :EOF
