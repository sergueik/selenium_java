### Info

This directory contains CommandLineParser class.

### Usage

To see how the code operates, consider running the provided unit tests first:
```sh
mvn test
```
this will exercise code in `src/test/java`:
```sh
Tests run: 26, Failures: 0, Errors: 0, Skipped: 2
```
that cover the class methods extensively and updated more frequently than this __README.md__.

#### Internals
#### Standard Argument
* Flags with value
```java
CommandLineParser c = new CommandLineParser();

c.flagsWithValues.add("option");
boolean debug = true;
c.setDebug(debug);
c.parse(args);

c.getFlagValue("option");
```
alternatively, in early revisions one could call
```java
option = c.flags.get("option");
```
* Switches
to introduce the boolean variables (switches) the syyntax is even simpler: no need to `add` to `flagsWithValues`:

```java
CommandLineParser c = new CommandLineParser();
c.parse(args);
boolean switch = c.hasFlag("switch");
```

alternatively, in early revisions one could call
```java
if (c.flags.containsKey("switch")) {
  boolean switch = true;
}
```
####  Extended Arguments
##### Environment values

* the following sytnac command line option indicates the environent variable reference:
```sh
export MY_ENVIRONMENT=value
java Application -flag env:MY_ENVIRONMENT
```
where inside the `Appilcation` class the value of the `flag`
will be
```java
System.getenv("MY_ENVIRONMENT")
```

##### File with lists of data (or JSON or YAML)
* Command line parser understands the file path reference through the following syntax:
```sh
java Application -option @filepath
```
this will load the non-blank lines of `filepath` contents and join via `,` to become the value of `option`. 

NOTE: there is a  work in progress to  provide the path to a file or URI using the standard syntax:
```sh
java ApplicationClass -data http://echc.jsontest.com/key/value/one/two
java ApplicationClass -data file:///<filepath>
```
The notation may change in the future release

#### JSON, YAML
The JSON format is recognized too, and is read using either [org.json.JSONObject](https://stleary.github.io/JSON-java/org/json/JSONObject.html) or [com.google.gson.Gson](https://javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/module-summary.html). 
Which one, is governed by invoking method:
```java
c.setValueFormat("GSON")
```
The file or URI contents are still  returned as a string data, but internally it is validated  to be serializalbe valid json object.
the Kubernetes-stye YAML support is a work in progres, see the details in the code.
### See Also
 * [introduction to Creational Design Patterns](https://www.baeldung.com/creational-design-patterns)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoc.com)
