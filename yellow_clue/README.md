### Note
Deploying phantom seems an heavy requirement for a non it user when the app in question is installed on multiple machine

Selenlium based solution is to avoid, except if if do not use a browser installed on the machine

There is plenty of library as apache, jsoup, why do not try with them ?

```cmd
set travis=false
mvn test
```

```cmd
set travis=true
mvn test


```
and to have a `har`
```cmd
cd C:\developer\sergueik\selenium_java\browsermob_proxy
mvn install
java -cp target\app-0.4-SNAPSHOT.jar;target\lib\* com.github.sergueik.bmp.YellowClueApp
jq-win64.exe  '.' < test.har >yellow_pages.har
```
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
