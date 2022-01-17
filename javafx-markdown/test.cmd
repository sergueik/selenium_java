@echo OFF
REM do not name this script run.cmd
call mvn package

echo done
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
REM java -jar target\example.javafx_markdown.jar