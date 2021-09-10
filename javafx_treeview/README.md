### Info
This directory contains a basic project based on Oracle's [example](http://docs.oracle.com/javafx/2/ui_controls/tree-view.htm)

### Usage
```sh
mvn clean package
```
```sh
CLASS=TreeViewSample
java -cp target/treeview.jar:target/lib/* example.$CLASS -resource sample:web:server1,sample:web:server2,sample:web:server3
java -cp target/treeview.jar:target/lib/* example.$CLASS -r sample:web:server1,sample:web:server2,sample:web:server3,sample:db:server1,sample:db:server2,sample:app:server1,sample:app:server2,sample:app:server3,sample:app:server4
```

on Windows use different aray separators

```cmd
set CLASS=TreeViewSample
java -cp target/treeview.jar;target/lib/* example.%CLASS% -resource sample:web:server1,sample:web:server2,sample:web:server3
java -cp target/treeview.jar;target/lib/* example.%CLASS% -r sample:web:server1,sample:web:server2,sample:web:server3,sample:db:server1,sample:db:server2,sample:app:server1,sample:app:server2,sample:app:server3,sample:app:server4
```

### TODO

* after packaging in single jar is fixed should be able to run as
```sh
java -jar target/treeview.jar
```

### See Also


 * [creating simple javafx app in plain Java code and in Scene Builder](https://sites.google.com/a/athaydes.com/renato-athaydes/posts/usingautomatontotestajavafx8app)
 * https://github.com/ethanp/programming/tree/master/Educational/Java/JavaFX/OracleTreeView/src/sample

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
