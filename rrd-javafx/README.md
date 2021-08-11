#### Info

This directory contains a repica of [binjr](https://github.com/binjr/binjr)
at commit __9956df122676571704de89946dd31dbc927c4814__
with additional tweaks to allow build and run under JDK __1.8__
and lazy removal of 'distribution' support
At that age, the __binjr__ application did not support __RRD__ input file yet.

### Usage
build from source
```sh
mvn clean install
```
followed by
```sh
java -cp  target/binjr-2.0.0.beta-SNAPSHOT.jar:target/lib/* eu.fthevenet.binjr.Binjr
```

### See Also

  * __binjr__ binary [releases](https://binjr.eu/download/latest_release/)
  
