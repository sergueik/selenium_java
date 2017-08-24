for /F %%. in ('dir /b /ad ') do call :build %%.
goto :run
:build 
pushd  %1 
call ant -keep-going 
popd
goto :EOF 
:run
java -classpath ^
CommonLib\dist\CommonLib.jar;FormatCLASS\dist\FormatCLASS.jar;JavaClassViewer\dist\JavaClassViewer.jar ^
 org.freeinternals.javaclassviewer.Main ^



