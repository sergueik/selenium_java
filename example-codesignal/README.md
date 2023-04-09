### Info

* https://app.codesignal.com/pre-screens

### Usage
* before commit
```sh

export SECRET=test
cd src/test/java/example/
base64 SawTest.java  > SawTest.java.base64
perl ../../../../jasypt.pl -value "$(cat SawTest.java.base64)" -operation encrypt -secret $SECRET >SawTest.java.base64.jasypt
base64 -d SawTest.java.base64 > SawTest.java
```

* after pull
```sh
export SECRET=test
cd src/test/java/example/
perl ../../../../jasypt.pl -value "$(cat SawTest.java.base64.jasypt)" -operation decrypt -secret $SECRET > SawTest.java.base64
base64 -d SawTest.java.base64 > SawTest.java
```
### Note
use `PBEWithMD5AndDES` alorithm 
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
