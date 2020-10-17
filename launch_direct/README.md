### Usage

```sh
mvn package
java -cp target/app-0.3-SNAPSHOT.jar:target/lib/* example.LaunchDirect -role integration-server -dc wec -env test -op dump
```
this will produce
```sh
Using default input argument: /home/sergueik/src/selenium_java/launch_direct/src/main/resources/classification.yaml
```
and then
```sh
host:
{datacenter=oxdc, role=integration-server-0, environment=test, unused1=null, unused2=null}

host:
{datacenter=oxdc, role=integration-server-3, environment=test}

host:
{datacenter=oxdc, role=integration-server-1, environment=test, unused1=null}

host:
{datacenter=oxdc, role=integration-server-2, environment=test, unused1=null}```
```
```cmd
java -cp target\app-0.3-SNAPSHOT.jar;target\lib\* example.LaunchDirect -role integration-server -dc oxdc -env test -op typed
```
this will produce
```sh
node:
node:
Hostname: a517535c
Role: integration-server-3
Environment: test
DC: oxdc


node:
Hostname: e577775d
Role: integration-server-1
Environment: test
DC: oxdc


node:
Hostname: e57fd0e12
Role: integration-server-2
Environment: test
DC: oxdc...
```

The operation `op` has to be supplied and the following is supported:
  * `dump`
  * `typed`
  * `excel`

When a specified yaml file path is provided, the specified file is read:

```sh
java -cp target/app-0.2-SNAPSHOT.jar:target/lib/*  example.LaunchDirect -role integration-server- -input src/main/resources/classification.yaml -dc wec -env test -op dump
```

```cmd
java -cp target\app-0.2-SNAPSHOT.jar;target\lib\* example.LaunchDirect -role integration-server -dc wec -env test -op excel -output test1.xlsx
```
will generate specified file
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
