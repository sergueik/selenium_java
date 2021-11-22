### Info

This directory contains the replica of
[markdown-javafx-renderer](https://github.com/JPro-one/markdown-javafx-renderer) `MarkdownView` class which uses
[vsch/flexmark-java](https://github.com/vsch/flexmark-java) Java implementation of markdown [spec](https://spec.commonmark.org/0.28/) and [real time CSS reloading](https://github.com/mcfoggy/cssfx)
converted temporarily to maven and Java 1.8. Also switched to classic `pom.xml` rom using [JPro custom  build process](https://github.com/JPro-one/JPro-from-Jars)

### Usage

```cmd
mvn clean package
java -jar target\example.javafx_markdown.jar
```
or if you need to explicitly load assemblies
```cmd
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
```
and
```sh
mvn clean package
java -jar target/example.javafx_markdown.jar
```
or if you need to explicitly load assemblies
```sh
java -cp target/example.javafx_markdown.jar:target/lib/* example.Example
```

NOTE: on Windows, uses `;` as class path argument separator. For unix do `:`

### See Also
   * javascript and JavaFX powered [Markdown Editor](https://github.com/arildyk/simple-markdown-editor) (need to convert from visual studio code project)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)


