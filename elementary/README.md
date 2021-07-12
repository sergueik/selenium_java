### Info

elementary standalone application intended to probe the grid health

### Usage
* start Selenium grid on some host or locally
* build application packge
```sh
mvn clean package
```
* run specifying the `browser`, the `host` and `port` of the hub (optionally) and the target `url` through commandline arguments
```sh
java -cp target/elementary-0.4-SNAPSHOT.jar;target/lib/* example.App --url https://www.google.com  -b chrome
```
on unix, replace `;` with `:`: 
```sh
java -cp target/elementary-0.4-SNAPSHOT.jar:target/lib/* example.App --url https://www.google.com  -b chrome -i
```
the `--local` option allows running chrome localy, in headless mode.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
