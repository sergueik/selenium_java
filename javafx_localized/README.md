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

```sh
// https://programminghistorian.org/en/lessons/transliterating

cyrillic_translit={
'\u0410': 'A', '\u0430': 'a',
'\u0411': 'B', '\u0431': 'b',
'\u0412': 'V', '\u0432': 'v',
'\u0413': 'G', '\u0433': 'g',
'\u0414': 'D', '\u0434': 'd',
'\u0415': 'E', '\u0435': 'e',
'\u0416': 'Zh', '\u0436': 'zh',
'\u0417': 'Z', '\u0437': 'z',
'\u0418': 'I', '\u0438': 'i',
'\u0419': 'I', '\u0439': 'i',
'\u041a': 'K', '\u043a': 'k',
'\u041b': 'L', '\u043b': 'l',
'\u041c': 'M', '\u043c': 'm',
'\u041d': 'N', '\u043d': 'n',
'\u041e': 'O', '\u043e': 'o',
'\u041f': 'P', '\u043f': 'p',
'\u0420': 'R', '\u0440': 'r',
'\u0421': 'S', '\u0441': 's',
'\u0422': 'T', '\u0442': 't',
'\u0423': 'U', '\u0443': 'u',
'\u0424': 'F', '\u0444': 'f',
'\u0425': 'Kh', '\u0445': 'kh',
'\u0426': 'Ts', '\u0446': 'ts',
'\u0427': 'Ch', '\u0447': 'ch',
'\u0428': 'Sh', '\u0448': 'sh',
'\u0429': 'Shch', '\u0449': 'shch',
'\u042a': '"', '\u044a': '"',
'\u042b': 'Y', '\u044b': 'y',
'\u042c': "'", '\u044c': "'",
'\u042d': 'E', '\u044d': 'e',
'\u042e': 'Iu', '\u044e': 'iu',
'\u042f': 'Ia', '\u044f': 'ia'}
 ```
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
