### Info

elementary standalone application intended to probe the grid health

### Usage
* start Selenium grid on some host or locally
* build application packge
```sh
mvn package
```
* run specifying the `browser`, the `host` and `port` of the hub (optionally) and the target `url` through commandline arguments
```sh
java  -cp target/elementary-0.3-SNAPSHOT.jar;target/lib/* example.App --url https://www.google.com  -b chrome
```
(on unix, replace `;` with `:`
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
