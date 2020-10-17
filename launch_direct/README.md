### Usage 

```sh
mvn package
java -cp target/app-0.1-SNAPSHOT.jar:target/lib/* example.LaunchDirect -role integration-server -input b -dc wec -env test -op dump
```
this will produce
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
The operation `op` has to be supplied and the following is supported:
`dump`. The `excel` is a work in progress.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
