call mvn clean install
echo "Project Built."

copy "target\*.war" "%CATALINA_HOME%\webapps"
echo "Copying war file"