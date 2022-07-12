### Info

Invoking [rrd4j/rrd4j](https://github.com/rrd4j/rrd4j) `Converter` __API__ to generate catalog of `ds` entries in `rrd` files
[RRD](https://oss.oetiker.ch/rrdtool/) found  via directory file scan a.k.a `Files.walk`.
As a rule tools create just 2 data sources, __DS0__ and __DS1__ in an RRD file however there is no limit to the number of entries,
hence the following code

```java
List<String> dsList = new ArrayList<>();
RrdDb rrd = RrdDb.getBuilder().setPath("test").setRrdToolImporter(new URL(dataFileUri).getFile()).setBackendFactory(new RrdMemoryBackendFactory()).build();
for (int cnt = 0; cnt != rrd.getDsCount(); cnt++) {
  dsList.add(rrd.getDatasource(cnt).getName());
}
```
is applied to every file matching `*.rrd` filemask within the directory
The file processor code shown above can be replaced for other types of files, e.g. legacy plaintext metric stores `data.txt`


### Usage

* build
```cmd
mvn -Dmaven.test.skip=true package
```
* run via `CLASSPATH`:
on Windows machine
```cmd
java -cp target\example.rrd-cachedb.jar;target\lib\* example.App --path data --save
```
or on Linux machine
```sh
java -cp target/example.rrd-cachedb.jar:target/lib/* example.App -p data -s -i data,web,other
```
NOTE: using short options is acceptable. Use `-z` for `--password` and `-y` for `--port`.
Providing `--include` option trigger filtering the files. Providing `--noop` along with `--include` or `--reject` will skip rrd scanning inside the files after bulding the list of files.

alternatively
```sh
export CLASSPATH=target/example.rrd-cachedb.jar:target/lib/\*
java example.App --path data --save
```

it will log to console:

```text
Missing argument: vendor. Using default
Missing argument: sqliteDatabaseName. Using default
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
Opened database connection successfully: C:\Users\Serguei\cache.db
Connected to product: SQLite    catalog: null   schema: null
Running SQL: CREATE TABLE IF NOT EXISTS cache_table ( id INT PRIMARY KEY NOT NULL,ds CHAR(50) NOT NULL, fname TEXT NOT NULL,expose CHAR(50));
Saving data
Querying data : SQLite  catalog: null   schema: null
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

where the `sample.rrd` file was placed into `data\web`
(additional folders used to test the `collect` and `reject` processing are not shown)

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
* prepare

```sh
cd data/web/
ln -fs sample.rrd  link.rrd
touch none.rrd
ln -fs none.rrd  badlink.rrd
rm none.rrd
```
* run

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

### Legacy Data Files Inventory
* prepare directory
```sh
./mkdatafiles.sh 2022 06 28
./mkdatafiles.sh 2022 06 29
./mkdatafiles.sh 2022 06 30
```
```
mv 2022028/ host1
mv 2022029/ host1
mv 2022030/ host1
```
NOTE: building a one day worth of data takes approx 5 minute on Windows machine, git bash. It is significantly faster to touch files instead of storing a metric in each
* run the scanner to inventory 
```cmd
java -cp target\example.rrd-cachedb.jar;target\lib\* example.App -p 20220629 -s -i 20220629 -n
```
```
java -cp target\example.rrd-cachedb.jar;target\lib\* example.App -p host1 -s -i 20220628,20220629,20220630  -n -legacy --hostname host1
```

will print to console

```text
hostname: host1
Missing argument: vendor. Using default
Missing argument: sqliteDatabaseName. Using default
Scanning path: /C:/developer/sergueik/selenium_java/rrd-cachedb/host1/
inspect: host1
status: false
inspect: 20220629
status: true
inspect: 20220630
status: true
Ingesting 2880 files:
Opened database connection successfully: C:\Users\Serguei\cache.db
Connected to product: SQLite    catalog: null   schema: null
Running SQL: CREATE TABLE IF NOT EXISTS metric_table ( `id` INTEGER,`hostname` T
EXT NOT NULL,`timestamp` TEXT,`memory` TEXT,`cpu` TEXT,`disk` TEXT,`load_average
` TEXT,PRIMARY KEY(`id`));
Saving data
```

### See Also


  * https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
  * [Walking the File Tree - Essential Java Classes](https://docs.oracle.com/javase/tutorial/essential/io/walk.html)
  * [list files in a directory in Java](https://www.baeldung.com/java-list-directory-files) with `File.list`
  * [copy directory in Java](https://www.baeldung.com/java-copy-directory) with `File.walk`

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

