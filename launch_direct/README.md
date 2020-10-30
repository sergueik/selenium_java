### Usage

```sh
mvn package
java -cp target/launchdirect-0.5-SNAPSHOT.jar:target/lib/* example.LaunchDirect -role integration-server -dc westcoast -env test -op dump
```
this will produce
```sh
Using default input argument: /home/sergueik/src/selenium_java/launch_direct/src/main/resources/classification.yaml
```
and then
```sh
host:
{datacenter=westcoast, role=integration-server-0, environment=test, unused1=null, unused2=null}

host:
{datacenter=westcoast, role=integration-server-3, environment=test}

host:
{datacenter=westcoast, role=integration-server-1, environment=test, unused1=null}

host:
{datacenter=westcoast, role=integration-server-2, environment=test, unused1=null}```
```
```cmd
java -cp target\launchdirect-0.5-SNAPSHOT.jar;target\lib\* example.LaunchDirect -role server -dc westcoast -env test -op typed
```
this will produce
```sh
node:
node:
Hostname: a517535c
Role: integration-server-3
Environment: test
DC: westcoast


node:
Hostname: e577775d
Role: integration-server-1
Environment: test
DC: westcoast


node:
Hostname: e57fd0e12
Role: integration-server-2
Environment: test
DC: westcoast
...
```

The operation `op` has to be supplied and the following is supported:
  * `dump`
  * `typed`
  * `excel`
  * `known`
  * `unknown`

To provide arguments to those opetations, the following arguments are recognized: `role`, `env`, `dc`, `nodes`, `input`, `output`.
To provide the list of nodes to examine, pass the filename argument via `@<filename>`. 

When a specified yaml file path is provided, the specified file is read:

```sh
java -cp target/launchdirect-0.5-SNAPSHOT.jar:target/lib/*  example.LaunchDirect -role server -input src/main/resources/classification.yaml -dc westcoast -env test -op dump
```

```cmd
java -cp target\launchdirect-0.5-SNAPSHOT.jar;target\lib\* example.LaunchDirect -role integration-server -dc westcoast -env test -op excel -output test1.xlsx
```
will generate specified file


Saving the list of host names containing known and unknown entries  then showing either `known` or `unknown` based on their presence in the keys of `classificator.yaml` works like below:
```cmd
java -cp target\launchdirect-0.5-SNAPSHOT.jar;target\lib\* example.LaunchDirect -role integration-server -dc westcoast -env test -op unknown -nodes @nodes.txt
```
will respond with
```sh
host:
e5absent0
```
and
```cmd
java -cp target\launchdirect-0.5-SNAPSHOT.jar;target\lib\* example.LaunchDirect -role integration-server -dc westcoast -env test -op known -nodes @nodes.txt
```
will respond with
```sh
hostname:
e57fd0e12

hostname:
e57fd0e12
```

### Inventory  extraction

![Inventory Example](https://github.com/sergueik/selenium_java/blob/master/launch_direct/screenshots/inventory_capture.png)

```sh
java -cp target\launchdirect-0.7-SNAPSHOT.jar;target\lib\* example.LaunchDirect -inventory inventory.xlsx -op import -role * -dc * -env * -columns Hostname,Location,Role,Notes
```

will iterate over defined sheets and column headers, picking the spefic ones:
```sh
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd01
Location: EAST
Role: CZK
Notes: Cassandra/Zookeeper
Env: PROD
--------
Hostname: abcprd2
Location: EAST
Role: PG
Notes: Postgres
Env: PROD
--------
...
```

and 
```sh
java -cp target\launchdirect-0.7-SNAPSHOT.jar;target\lib\* example.LaunchDirect -inventory inventory.xlsx -op import -role * -dc * -env * -debug -columns Hostname,Location,Role,Missing
```
will discover
```sh
There is not enough data in the inventory file: inventory.xlsx
```

### Depedencies
```sh
commons-codec-1.10.jar
commons-collections4-4.1.jar
commons-configuration-1.10.jar
commons-exec-1.3.jar
commons-io-2.5.jar
commons-lang-2.6.jar
commons-logging-1.1.1.jar
poi-3.17.jar
poi-excelant-3.17.jar
poi-ooxml-3.17.jar
poi-ooxml-schemas-3.17.jar
xmlbeans-2.6.0.jar
```
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
