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
java -cp target/elementary-0.4-SNAPSHOT.jar:target/lib/* example.App --url https://www.google.com  -b chrome -l
```
the `--local` option allows running chrome localy, in headless mode.


the other urls to try are
`chrome://settings/help` (will generate white rectangle in heasdless a.k.a. local mode) and `http://$(hostname -i):4444/grid/console` - will show one instance of  browser "blurred" when run on X and all available when run in local mode.


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
