### Info

elementary standalone application intended to probe the grid health

### Usage
* start Selenium grid on some host or locally
* build application packge
```sh
mvn package
```
* run specifying the `browser`, the `host` and `port`  of the hub (optionally) and the target `url` through commandline arguments
```sh
java -cp target/elementary-0.2-SNAPSHOT.jar;target/lib/* example.App -browser chrome -url www.hollandamerica.com/ -host 192.168.33.12
```
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
