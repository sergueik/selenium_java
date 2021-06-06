### Info

This directory contains the replica of
[markdown-javafx-renderer](https://github.com/JPro-one/markdown-javafx-renderer) `MarkdownView` class which uses
[vsch/flexmark-java](https://github.com/vsch/flexmark-java) Java implementation of markdown [spec](https://spec.commonmark.org/0.28/) and [real time CSS reloading](https://github.com/mcfoggy/cssfx)
converted temporarily to maven and Java 1.8. Also switched to classic `pom.xml` rom using [JPro custom  build process](https://github.com/JPro-one/JPro-from-Jars)

### Usage

```cmd
mvn clean package
java -cp target/javafx_markdown.jar;target/lib/* example.Example
```
