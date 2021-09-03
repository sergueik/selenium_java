### Info
This directory contains a replica of [file dialog with treeview](https://github.com/tomoTaka01/FileTreeViewSample)

```sh
mvnc lean package
java -jar target/filetreeviewsample.jar
```
```sh
CLASS=TreeViewSample
java -cp target/filetreeviewsample.jar:target/lib/* example.$CLASS -resource sample:web:server1,sample:web:server2,sample:web:server3
java -cp target/filetreeviewsample.jar:target/lib/* example.$CLASS -r sample:web:server1,sample:web:server2,sample:web:server3,sample:db:server1,sample:db:server2,sample:app:server1,sample:app:server2,sample:app:server3,sample:app:server4
```
### See Also

 * [creating simple javafx app in plain Java code and in Scene Builder](https://sites.google.com/a/athaydes.com/renato-athaydes/posts/usingautomatontotestajavafx8app)
 * https://github.com/ethanp/programming/tree/master/Educational/Java/JavaFX/OracleTreeView/src/sample
