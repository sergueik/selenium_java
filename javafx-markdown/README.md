### Info

This directory contains a minimal desktop application showcasing a standalone replica of the  
[`MarkdownView`](https://github.com/JPro-one/markdown-javafx-renderer) Markdown-to-HTML renderer running inside a simple [JavaFX](https://en.wikipedia.org/wiki/JavaFX) UI.

![application](https://github.com/sergueik/selenium_java/blob/master/javafx-markdown/screenshots/app.png)
<!-- use fully qualified path to the image for testing -->
The project uses:

* [`flexmark-java`](https://github.com/vsch/flexmark-java) — a Java implementation of the [CommonMark 0.28](https://spec.commonmark.org/0.28/) specification  
* [`cssfx`](https://github.com/mcfoggy/cssfx) — real-time CSS reloading  
* A classic Maven `pom.xml` (replacing the original custom JPro build system)  
* Java **1.8** and an **Azul Java 11** launcher for Windows and Linux  

This setup provides a clean, dependency-controlled JavaFX Markdown renderer suitable as a scaffold for future desktop tools.

---

### Usage

#### Launcher scripts

Windows (Java 8):
```cmd
test.cmd
```

Windows (Java 11):
```cmd
test-jdk11.cmd
```

#### Manual run

```cmd
mvn clean package
java -cp target/javafx_markdown.jar;target/lib/* com.sandec.mdfx.example.ExampleMDFX
```

---

### Author

[Serguei Kouzmine](mailto:kouzmine_serguei@yahoo.com)
