### Info

This directory contains project converted from [grafana-rrd-cachedb](https://github.com/sergueik/selenium_java/tree/master/rrd-cachedb) to handle legacy plaintext metric directories


### Usage

* prepare directory
```sh
./mkdatafiles.sh 2022 06 28
./mkdatafiles.sh 2022 06 29
./mkdatafiles.sh 2022 06 30
```
```sh
mv 20220628/ host1
mv 20220629/ host1
mv 20220630/ host1

```
* make few empty dirs to exercise the scripts

```sh
mkdir host1/20220627
mkdir host1/20220626
mkdir host1/20220625

```
NOTE: building a one day worth of data takes approx 5 minute on Windows machine, git bash. It is significantly faster to touch files instead of storing a metric in each. The directory tree / file creation is instance under ubuntu
* run the scanner to inventory
```cmd
mvn package
cp src/main/resources/cache.db ~
java -cp target/example.datatxt-cachedb.jar:target/lib/* example.App -p host1 -s --hostname hostt1
```
can use relative path: `--path ..\rrd-cachedb\host1`.

to select / reject folders,use the `-i` and `-r` arguments:
```sh
java -cp target\example.datatxt-cachedb.jar;target\lib\* example.App -p 20220629 -s -i 20220629
```
```sh
java -cp target\example.datatxt-cachedb.jar;target\lib\* example.App -p host1 -s -i 20220628,20220629,20220630 --hostname host1
```
To estimate the run time with big directories  may omit the `-s` (`--save`) option.
The default run will print to console (output is truncated):

```text
hostname: host1
Missing argument: vendor. Using default
Missing argument: sqliteDatabaseName. Using default
Scanning path: /C:/developer/sergueik/selenium_java/rrd-cachedb/host1/
inspect: host1
status: false
inspect: 20220628
status: false
inspect: 20220629
status: true
inspect: 20220630
status: false
found file: data.txt.202206290000
found file: data.txt.202206290001
found file: data.txt.202206290002
found file: data.txt.202206290003
...
Ingesting 1440 files:
about to add data: [memory, cpu, disk, load_average]
reading data for metric cpu = 12
reading data for metric memory = 22
reading data for metric disk = 42.5
reading data for metric load_average = 6
reading data for metric rpm = 102
adding timestamp: 1656475200000
added data: [disk, memory, load_average, cpu, rpm]
...
Saving data
about to insert data row: [host1, 1656475200000, 22, 12, 42.5, 6]
about to insert data row: [host1, 1656475260000, 22, 12, 42.5, 6]
about to insert data row: [host1, 1656475320000, 22, 12, 42.5, 6]
about to insert data row: [host1, 1656475380000, 22, 12, 42.5, 6]
about to insert data row: [host1, 1656475440000, 22, 12, 42.5, 6]
...
Querying data : SQLite	catalog: null	schema: null
hostname = host1        timestamp = 1656561480000       disk = 42.5     cpu = 12
        memory = 22     load_average = 6
hostname = host1        timestamp = 1656561540000       disk = 42.5     cpu = 12
        memory = 22     load_average = 6
...
Done: host1
```
Verify in the sqlite console:
```sh
sqlite3 ~/cache.db
```
```text
SQLite version 3.22.0 2018-01-22 18:45:57
Enter ".help" for usage hints.
```
```sh
sqlite> .schema
```
```text
CREATE TABLE metric_table ( `id` INTEGER,`hostname` TEXT NOT NULL,`timestamp` TEXT,`memory` TEXT,`cpu` TEXT,`disk` TEXT,`load_average` TEXT,PRIMARY KEY(`id`));
```
```sh
sqlite> select count(1) from metric_table;
```
```text
4320
```
NOTE: during the execution of the program will be likely getting
```sh
sqlite> .schema
```
```text
Error: database is locked
```

the app itself supports `--query` option to run the SQL query, useful for debugging:
```sh
java -cp target\example.datatxt-cachedb.jar;target\lib\* example.App -p ..\rrd-cachedb\host1 -s --hostname hostt1 --query
```
this will prin (truncated):

```text
hostname = hostt1       timestamp = 1656647760000       disk = 42.5     cpu = 12
        memory = 22     load_average = 6
hostname = hostt1       timestamp = 1656647820000       disk = 42.5     cpu = 12
        memory = 22     load_average = 6
hostname = hostt1       timestamp = 1656647880000       disk = 42.5     cpu = 12
        memory = 22     load_average = 6
hostname = hostt1       timestamp = 1656647940000       disk = 42.5     cpu = 12
        memory = 22     load_average = 6
```
###  Preparing the wrapper

```sh
mkdir -p /tmp/basedir/{10,20,30,40}
mkdir -p /tmp/basedir/10/{1,2,3,4}
mkdir -p /tmp/basedir/20/{1,2}
./count_subdirs1.sh /tmp/basedir/
./count_subdirs2.sh /tmp/basedir/
```
running with timing the run time:
```sh
NUM=10
date && for D in $(tail -$NUM /tmp/result.txt); do ./example.sh $D ; done; date
```
where `example.sh` wraps the call to `target/example.datatxt-cachedb.jar` with all flags and switches (not shown here)

### See Also


  * https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
  * [Walking the File Tree - Essential Java Classes](https://docs.oracle.com/javase/tutorial/essential/io/walk.html)
  * [list files in a directory in Java](https://www.baeldung.com/java-list-directory-files) with `File.list`
  * [copy directory in Java](https://www.baeldung.com/java-copy-directory) with `File.walk`
  * [documentation](https://commons.apache.org/proper/commons-csv/) of database vendor specific csv formats supported by `apache.commons-csv` - only essential for reading

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

