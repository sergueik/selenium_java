### Info

This directory contains a replica of JavaFx project 
[tomoTaka01/LocalizationSample](https://github.com/tomoTaka01/LocalizationSample) demonstrating localization effort. The project directory structure converted to maven, and adding `ru-RU` localization is in progress.


### Testing
```sh
mvn clean package
```
```sh
java -cp target/lib/*:target/localized_example.jar  com.sample.Main
```
or
```cmd
java -cp target\lib\*;target/localized_example.jar  com.sample.Main
```
### Localizing

The individual localized resource strings apprently need to be in __UTF16__ encoding but written like
```java
day_label=\u0434\u0435\u043d\u044c
```
instead of

```java
day_label=день
```
The class `Util` with embedded static `Translit` was added to handle the conversion temporarily
### See Also

* [transliteraring example](https://programminghistorian.org/en/lessons/transliterating) in Python
* [stackoverflow resource](https://stackoverflow.com/questions/10143392/javafx-2-and-internationalization)
* [example](https://stackoverflow.com/questions/10143392/javafx-2-and-internationalization/10148224#10148224) changes the label text at the FXML loading stage
* [unicode codepoint lookup/search tool](http://unicode.scarfboy.com/?s=U%2B65e5)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
