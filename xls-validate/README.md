### Info

 fork of https://github.com/codeborne/xls-test/tree/main, preparing to add PR

### NOTE 

* Under openjdk 11 Linux Ubuntu __18.04__
cannot build the proeject
```text
java.lang.UnsatisfiedLinkError: Can't load library: /usr/lib/jvm/java-11-openjdk-amd64/lib/libawt_xawt.so
java.lang.NoClassDefFoundError: Could not initialize class org.jopendocument.dom.StyleProperties
```
solved via
```sh
sudo apt install openjdk-11-jdk
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
