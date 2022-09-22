### Info

This directory contains modifications added to build the and prepare the PR with CDP wrapper class.
The oritnal project builds on gradle and has more depenencies than we can afford. Therefore the hisoric commit was used to build:
```text
commit 4d86e7e839d8d11d2d11a8479f2f90f03622e272 (HEAD)
Author: Andrei Solntsev <andrei.solntsev@gmail.com>
Date:   Tue Sep 28 09:11:08 2021 +0300

    release Selenide 5.25.0
```

and a number of comments added to the code and test code to allow linking with __Selenium__ __4.x__

The minimal injection was thought to follow the [discussion](https://github.com/selenide/selenide/issues/1486) around the `WebdriverPhotographer` class, but probably the better way is to add methods to `com.codeborne.selenide.Driver` class


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
