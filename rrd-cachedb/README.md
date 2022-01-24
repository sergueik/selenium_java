### Info

Invoking [rrd4j/rrd4j](https://github.com/rrd4j/rrd4j) `Converter` __API__ to generate catalog of ds in [RRD](https://oss.oetiker.ch/rrdtool/)
within a directory. Many tools produce RRD files with just 2 data sources, __DS0__ and __DS1__ however there is no limit to the number of entries, hence the code
```java
RrdDb rrd = RrdDb.getBuilder()
   .setPath("test")
  .setRrdToolImporter(new URL(dataFileUri).getFile())
  .setBackendFactory(new RrdMemoryBackendFactory()).build();
for (int cnt = 0; cnt != rrd.getDsCount(); cnt++) {
  String ds = rrd.getDatasource(cnt).getName();
}
```
is iterated for every file matching `*.rrd` filemask
### Usage
* build
```cmd
mvn -Dmaven.test.skip=true package
```
* run via `CLASSPATH`:
```sh
java -cp target\example.rrd-cachedb.jar;target\lib\* example.App --path data --save
```
or just
```sh
java -cp target/example.rrd-cachedb.jar:target/lib/* example.App --path data --s
```
alternatively
```sh
export CLASSPATH=target/example.rrd-cachedb.jar:target/lib/\*
java example.App --path data --save
```
validate explcitly via
```sh
sqlite3 $HOME/cache.db "select count(*) from cache;"
```
returning the number of inserted rows
```sh
16
```
or to collect the subset of folders:
```sh
test -d data/web || cp -R data/* data/web
```
```
java -cp target\example.rrd-cachedb.jar;target\lib\* example.App --path data --save --collect web,app --reject util
```
followed with

```sh
sqlite3 $HOME/cache.db "select distinct(fname) from cache;"
```

where re the `sample.rrd` file was placed into `data\web` (additional folders used to test the `collect` and `reject` processing are not shown)

this outputs
```cmd
Scanning path: /C:/developer/sergueik/selenium_java/rrd-cachedb/data/
Reading RRD file: web:sample
ds[0]= "ClientGlideIdle"
ds[1]= "ClientGlideRunning"
ds[2]= "ClientGlideTotal"
ds[3]= "ClientInfoAge"
ds[4]= "ClientJobsIdle"
ds[5]= "ClientJobsRunning"
ds[6]= "ReqIdle"
ds[7]= "ReqMaxRun"
ds[8]= "StatusHeld"
ds[9]= "StatusIdle"
ds[10]= "StatusIdleOther"
ds[11]= "StatusPending"
ds[12]= "StatusRunning"
ds[13]= "StatusStageIn"
ds[14]= "StatusStageOut"
ds[15]= "StatusWait"
fname = web:sample      ds = ClientGlideIdle
fname = web:sample      ds = ClientGlideRunning
fname = web:sample      ds = ClientGlideTotal
fname = web:sample      ds = ClientInfoAge
fname = web:sample      ds = ClientJobsIdle
fname = web:sample      ds = ClientJobsRunning
fname = web:sample      ds = ReqIdle
fname = web:sample      ds = ReqMaxRun
fname = web:sample      ds = StatusHeld
fname = web:sample      ds = StatusIdle
fname = web:sample      ds = StatusIdleOther
fname = web:sample      ds = StatusPending
fname = web:sample      ds = StatusRunning
fname = web:sample      ds = StatusStageIn
fname = web:sample      ds = StatusStageOut
fname = web:sample      ds = StatusWait
```
* to specify alternative SQLite filename, use `--file` option
```sh
java -cp target/example.rrd-cachedb.jar:target/lib/* example.App --path data --save --file my.db
```
### Saving on  MySQL server

* start mysql server which access credentials are known:
```sh
docker start mysql-server
```
### Processing Symbolic Links
prepare
```sh
cd data/web/
ln -fs sample.rrd  link.rrd
touch none.rrd
ln -fs none.rrd  badlink.rrd
rm none.rrd
```
run
```sh
 java -cp target/example.rrd-cachedb.jar:target/lib/* example.App --path data --save
```
NOTE: even without any code changes the `data/web/link.rrd` willbe added and `data/web/badlink.rrd` will be not. However to try manual validation of the link target run as:
```sh
 java -cp target/example.rrd-cachedb.jar:target/lib/* example.App --path data --save --verifylinks
```
this will print among oher things,
```text
Testing link link.rrd target path sample.rrd
Valid link link.rrd target path /home/sergueik/rrd-cachedb/data/web/sample.rrd
Testing link badlink.rrd target path none.rrd
```
illustrating the process
### See Also


  * https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
  [Walking the File Tree - Essential Java Classes](https://docs.oracle.com/javase/tutorial/essential/io/walk.html)


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

