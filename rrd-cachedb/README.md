### Info

Invoking [rrd4j/rrd4j](https://github.com/rrd4j/rrd4j) `Converter` __API__ to generate catalog of ds in [RRD](https://oss.oetiker.ch/rrdtool/)
within a directory. Many tools produce RRD files with just 2 data sources, __DS0__ and __DS1__ however there is no limit to the number of entries, hence the code
```java
URL url = new URL(dataFileUri);
RrdDb rrd = RrdDb.getBuilder()
   .setPath("test")
  .setRrdToolImporter(url.getFile())
  .setBackendFactory(new RrdMemoryBackendFactory()).build();
for (int cnt = 0; cnt != rrd.getDsCount(); cnt++) {
  String ds = rrd.getDatasource(cnt).getName();
}
```
is iterated for every file matching `*.rrd` filemask
### Usage
* build
```cmd
mvn package
```
* run via `CLASSPATH`:
```
java -cp target\example.rrd-cachedb.jar;target\lib\* example.App -p rrdtool
```
where the `.rrd` files are put into `rrdtool`, `rrdool\web` and `rrdtool\app` folders
this outputs
```cmd
Scanning path: rrdtool
Reading RRD file: 0001b328
Exception (ignored): java.lang.IllegalArgumentException
Reading RRD file: 0001b648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: 0001l324
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: 0001l648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: 0003b328
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: 0003l324
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: 0003l328
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: 0003l648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0001b328
Exception (ignored): java.lang.IllegalArgumentException
Reading RRD file: app:0001b648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0001l324
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0001l648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0003b328
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0003l324
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0003l328
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:0003l648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: app:rrdtool
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: rrdtool
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0001b328
Exception (ignored): java.lang.IllegalArgumentException
Reading RRD file: web:0001b648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0001l324
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0001l648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0003b328
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0003l324
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0003l328
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:0003l648
ds[0]= "speed"
ds[1]= "weight"
Reading RRD file: web:rrdtool
ds[0]= "speed"
ds[1]= "weight"
```

### See Also

  * https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
  [Walking the File Tree - Essential Java Classes](https://docs.oracle.com/javase/tutorial/essential/io/walk.html)


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

