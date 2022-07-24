### Info

This directory contains project converted from [grafana-rrd-cachedb](https://github.com/sergueik/selenium_java/tree/master/rrd-cachedb) to handle legacy plaintext metric directories


### Usage

* prepare directory
```sh
./mkdatafiles.sh 2022 07 15
./mkdatafiles.sh 2022 07 14
./mkdatafiles.sh 2022 07 13
```
```sh
mkdir host1
mv 20220714/  20220713/ 20220715/ host1

```
* make few empty dirs to exercise the scripts

```sh
mkdir host1/202206{27,26,25}
```
NOTE: building a one day worth of data takes approx 5 minute on Windows machine, git bash. It is significantly faster to touch files instead of storing a metric in each. The directory tree / file creation is instant under ubuntu
* run the scanner to inventory
```cmd
mvn package
cp src/main/resources/cache.db ~
java -cp target/example.datatxt-cachedb.jar:target/lib/* example.App -p host1 -s --hostname hostt1
```
can use relative path: `--path ..\rrd-cachedb\host1`.

to select / reject folders,use the `-i` and `-r` arguments:
```sh
java -cp target\example.datatxt-cachedb.jar;target\lib\* example.App -p host1 -s -i 20220713,20220714,20220715 --hostname host1
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

### Connecting to mySQL server container

* assuming the container named `mysql-server` was created earlier for some other task
```sh
NAME='mysql-server'
docker container start $NAME
```
```sh
NAME='mysql-server'
docker container inspect $NAME | jq '.[]|.Config.Env'
```

this will show environment used when container was launched:
```json
[
  "MYSQL_DATABASE=test",
  "MYSQL_PASSWORD=password",
  "MYSQL_ROOT_PASSWORD=password",
  "MYSQL_USER=java",
  "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
  "GOSU_VERSION=1.7",
  "MYSQL_MAJOR=8.0",
  "MYSQL_VERSION=8.0.18-1debian9"
]
```
- details may vary, point of interest is `MYSQL_PASSWORD`, `MYSQL_USER`, `MYSQL_ROOT_PASSWORD`
```sh
docker container inspect mysql-server | jq '.[]|.Config.ExposedPorts'
```
```JSON
{
  "3306/tcp": {},
  "33060/tcp": {}
}
```
```sh
docker container inspect mysql-server | jq '.[]|.NetworkSettings.Ports'
```
```JSON
{
  "3306/tcp": [
    {
      "HostIp": "0.0.0.0",
      "HostPort": "3306"
    }
  ],
  "33060/tcp": null
}
```

* update the arguments in `App.java` run command accordingly - there currently is no `application.properties`, but all connection specific can be set on the command line

if the error
```text
driverObject=class com.mysql.cj.jdbc.Driver
Connected to product: MySQL
Connected to catalog: test
Exception: Unknown table 'test.cache_table'
java.sql.SQLSyntaxErrorException: Unknown table 'test.cache_table'
```

is observed

* run the commands
```sh
docker exec -it mysql-server mysql -u root -ppassword
```
then in
```sh
mysql>
```
```text
use test

CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TEXT, `memory` TEXT, `cpu` TEXT, `disk` TEXT, `load_average` TEXT, PRIMARY KEY(`id`) );
```
```text
\q
```
* run the application with the `-vendor mysql`  option added:
```sh
java -cp target/example.datatxt-cachedb.jar:target/lib/* example.App -p host1 -s --hostname host1 -vendor mysql
```
* modify the command to Windows path and separator if testing on Windows:
```cmd
java -cp target\example.datatxt-cachedb.jar;target\lib\* example.App -p host1 -s --hostname host1
```
after it completes connect to database node and count inserved rows
```sh
docker exec -it mysql-server mysql -u java -ppassword
```
in
```sh
mysql>
```
run

```text

use test
select count(1) from metric_table;
```
this will show
```text
+----------+
| count(1) |
+----------+
|     2880 |
+----------+

```
after confirming thati dummy metric information is in
there 
next step is to attach the `mysql-server` node to the `grafana` one in a MySQL Data source [plugin](https://grafana.com/grafana/plugins/mysql/) (built in, native).

### Connecting to Grafana

* build the `basic-grafana` container as covered in [basic-grafana](https://github.com/sergueik/springboot_study/tree/master/basic-grafana)
```sh
pushd ~/src/springboot_study/basic-grafana
IMAGE=basic-grafana
docker build -f Dockerfile -t $IMAGE .
popd
```


* run `basic-grafana` container linked to `mysql-server` one
```
IMAGE=basic-grafana
docker container run --name $IMAGE --link mysql-server -d -p 3000:3000 $IMAGE
```

* configure MySQL data source through Grafana web interface using `mysql-server:3306` host and the user / password seen in the container configuration earlier.

![data source](https://github.com/sergueik/selenium_java/blob/master/datatxt-cachedb/screenshots/capture-datasource.png)

After filling the connection details, and "Save & Test" it will respond with "Database Connection OK"


To address the error
```text
invalid type for column time, must be of type timestamp or unix timestamp, got: string 1656388800
```

when inserting to handle the
```text
ERROR 1292 (22007): Incorrect datetime value: '1656584700'
```

need to change the database schema on MySQL table definition:


```SQL
DROP TABLE IF EXISTS `metric_table`;
CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TIMESTAMP, `memory` TEXT, `cpu` TEXT, `disk` TEXT, `load_average` TEXT, PRIMARY KEY(`id`) );
```

and the insert string to
```SQL
INSERT INTO `metric_table` ( `id`, `hostname`,`timestamp`,`memory`, `cpu`,`disk`,`load_average`) VALUES (?, ?, FROM_UNIXTIME(?), ?, ?, ?, ?);
```

to address the error

```text
Value column must have numeric datatype, column: cpu type: string value: 12
```

need to change the database schema on MySQL table definition:


```SQL
DROP TABLE IF EXISTS `metric_table`;
CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TIMESTAMP, `memory` INTEGER, `cpu` INTEGER, `disk` FLOAT(6), `load_average` INTEGER, PRIMARY KEY(`id`) );
```
```text
describe metric_table ;
```
```text
+--------------+------------+------+-----+---------+-------+
| Field        | Type       | Null | Key | Default | Extra |
+--------------+------------+------+-----+---------+-------+
| id           | bigint(20) | NO   | PRI | NULL    |       |
| hostname     | text       | NO   |     | NULL    |       |
| timestamp    | timestamp  | YES  |     | NULL    |       |
| memory       | int(11)    | YES  |     | NULL    |       |
| cpu          | int(11)    | YES  |     | NULL    |       |
| disk         | float      | YES  |     | NULL    |       |
| load_average | int(11)    | YES  |     | NULL    |       |
+--------------+------------+------+-----+---------+-------+
```
and the insert placeholder fillers to
```java
preparedStatement.setInt(4, Integer.parseInt(memory));
preparedStatement.setInt(5, Integer.parseInt(cpu));
preparedStatement.setFloat(6, Float.parseFloat(disk));
preparedStatement.setInt(7, Integer.parseInt(load_average));
```

the tag(s?) wll be picked in "metric column":

![tag column selection](https://github.com/sergueik/selenium_java/blob/master/datatxt-cachedb/screenshots/capture-tag-selection.png)

the values will be offered in "column":

![value column selection](https://github.com/sergueik/selenium_java/blob/master/datatxt-cachedb/screenshots/capture-value-selection.png)

the data will show in the selected date range

![metric data](https://github.com/sergueik/selenium_java/blob/master/datatxt-cachedb/screenshots/capture-metric-data.png)


the data can be also viewed /  schema improved in a regular desktop DB management tool, e.g. SQLite viewer (note the minor schema differences between database vendors):

![metric db](https://github.com/sergueik/selenium_java/blob/master/datatxt-cachedb/screenshots/capture-db.png)

### Cleanup

```sh
docker container stop $IMAGE
docker container stop mysql-server
```
### See Also


  * https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
  * [Walking the File Tree - Essential Java Classes](https://docs.oracle.com/javase/tutorial/essential/io/walk.html)
  * [list files in a directory in Java](https://www.baeldung.com/java-list-directory-files) with `File.list`
  * [copy directory in Java](https://www.baeldung.com/java-copy-directory) with `File.walk`
  * [documentation](https://commons.apache.org/proper/commons-csv/) of database vendor specific csv formats supported by `apache.commons-csv` - only essential for reading
  * [MySQL Data Source plugin] (https://grafana.com/grafana/plugins/mysql/)
  * [Using MySQL in Grafana as Data Source](https://grafana.com/docs/grafana/v7.5/datasources/mysql/)
  * https://stackoverflow.com/questions/4125947/what-is-the-data-type-for-unix-timestamp-mysql
  * https://stackoverflow.com/questions/12333461/insert-unix-timestamp-in-mysql
  * https://www.w3schools.com/mysql/mysql_datatypes.asp
  * [introduction to Apache Commons CSV](https://www.baeldung.com/apache-commons-csv) - does not cover multiple columns export
  * alternative [fast CSV serializer](https://github.com/osiegmar/FastCSV)

### Youtube Videos

* [Using MySQL to Create a Grafana Dashboard](https://www.youtube.com/watch?v=aUq85rp7yQU)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

