### Info

Noticed that [allure](https://github.com/allure-framework/allure2)
framework may have conflicting annotations with [junit4](https://github.com/junit-team/junit4).


### Usage
Take a project that contails some junit 4 tests (not necessarily passing)

```cmd
mvn clean test

Results :

Failed tests:   testDownload(software.MinimalTest): (..)

Tests run: 4, Failures: 1, Errors: 0, Skipped: 2

```
- rebuild after merging the project `pom.xml` with the allure
test framework  become invisible.

```cmd
mvn clean test

[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 0, Failures: 0, Errors: 0, Skipped: 0
[INFO]
```



### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
