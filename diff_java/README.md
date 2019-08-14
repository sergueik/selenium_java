### Note
This directory contains a replica of java2s source of diff (java implementation) intended to use with custom matcher or assertion of deep sets of objects via json or YAML serializartion.
### Usage

```cmd
mvn clean install
java -cp target\diff_java-0.1-SNAPSHOT.jar com.github.sergueik.example.Diff a.json b.json
```
(assuming the JSON files represent the test expected and real outcome
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
