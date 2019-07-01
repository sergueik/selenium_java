### Info 

This directory contains a replica of a sarter [cassandra java examples](https://github.com/oscerd/cassandra-java-example) project collapsed into a single test class with static "SimpleClient" connection method sugar class.

This tests require [DataStax Community Edition](https://downloads.datastax.com/#ddac) distribution of Apache Cassandra which no longer is distributed in theform of Windows standalone MSI installer package but as a plain tar archive. Forthe purpose of this test one can explode it in advance and start it directly - the only dependency on Windows 7 onward is the JDK:
```cmd
set JAVA_HOME=c:\java\jdk1.8.0_101
set CASSANDRA_HOME=c:\java\ddac-5.1.15
pushd %CASSANDRA_HOME%
bin\cassandra.bat
```
It is [said](https://www.datastax.com/2012/01/getting-started-with-apache-cassandra-on-windows-the-easy-way) that Docker containers [provide a much better](https://github.com/oscerd/cassandra-image) development experience on Windows.

### See Also
  * [connecting to cassandra from java](https://www.javaworld.com/article/2158807/connecting-to-cassandra-from-java.html)
  * [Virtual Machine images](http://julien.duponchelle.info/cassandra/mysql/Cassandra-MariaDB-Virtual-Box-images/) with Cassandra and MariaDB
  * [DataSTAX VirtualBox](https://www.datastax.com/2014/03/introducing-the-datastax-sandbox) VM
  * [Vagrantfiles](https://github.com/bcantoni/vagrant-cassandra) for Cassandra, including multinode

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

