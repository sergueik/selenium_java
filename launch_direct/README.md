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
java -cp target\launchdirect-0.6-SNAPSHOT.jar;target\lib\* example.LaunchDirect -inventory inventory.xlsx -op import -role * -dc * -env * -debug
```

will iterate over defined sheets and column headers, picking the spefic ones:
```sh
Using default inputFile: classification.yaml
Read column headers: [Device ID, Hostname, Location, IP, Network, Role, Notes]
Read sheet names: [PROD, TEST, SIT]
Reading Excel 2007 data sheet.
1 = B Hostname
2 = C Location
5 = F Role
6 = G Notes
=>abcprd01
=>EAST
=>CZK
=>Cassandra/Zookeeper

=>abcprd2
=>EAST
=>PG
=>Postgres

=>abcprd3
=>EAST
=>RMP
=>Router/Message Processor

=>abcpr5
=>EAST
=>RMP
=>Router/Message Processor

0 => abcprd01
1 => EAST
2 => CZK
3 => Cassandra/Zookeeper
---
0 => abcprd2
1 => EAST
2 => PG
3 => Postgres
---
0 => abcprd3
1 => EAST
2 => RMP
3 => Router/Message Processor
---
0 => abcpr5
1 => EAST
2 => RMP
3 => Router/Message Processor
---
```
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
