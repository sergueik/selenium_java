### Info

This directory containes CommandLineParser class.

### Usage

#### Standard Argument
* Flags with value
```java
commandLineParser.flagsWithValues.add("option");
commandLineParser.parse(args);

option = commandLineParser.flags.get("option");
if (option == null) {
  option = "default value";
}
```

* Switches
no need to `add` to `flagsWithValues`:

```java
commandLineParser.parse(args);

if (commandLineParser.flags.containsKey("switch")) {
  switch = true;
}

```
####  Extended Arguments
##### Environment values

* no changes on Java sie during intialilztion. Command line provides environent reference through the following syntax:
```sh
export MY_ENVIRONMENT=value
java ApplictionClass -flag env:MY_ENVIRONMENT
```
##### File with lists of data (or JSON or YAML)
* no changes on Java sie during intialilztion. Command line provides environent reference through the following syntax:
```sh
java ApplicationClass -option @filepath
```
this will load the non-blank lines of `filepath` contents and join via `,` to become the value of `option`

NOTE: there is a  way to  provide the path to a file or URI using the starndard syntax, it is a work in progress, the notation may change in the future release
```sh
java ApplicationClass -data http://echo.jsontest.com/key/value/one/two
java ApplicationClass -data file:///<filepath>
```
### See Also
 * [introduction to Creational Design Patterns](https://www.baeldung.com/creational-design-patterns)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
