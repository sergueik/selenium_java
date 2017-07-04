### Info

This directory contains replica of the dynamic element locator annotation class code discussed in [dynamic-using-in-findsby-with-selenium](http://stackoverflow.com/questions/26366094/dynamic-using-in-findsby-with-selenium)
The goal is to combine this technique with Protractor Page Objects [jpagefactory](https://github.<F12>com/henrrich/jpagefactory) - unlike 'core' Selenium, Protractor supports multiple `By` locators which consume several arguments:

* `By cssContainingText(final String cssSelector, String text)`
* `By repeaterRows(final String repeat, Integer index)`
* `By repeaterColumn(final String repeat, String binding)`
* `By repeaterElement(final String repeat, Integer index, String binding)`

JPageFactory currently only supports one extended annotation:

* `@FindAll({ @FindBy(how = How.REPEATER_COLUMN, using = "row in rows", column = "name") })`
To be useful such annotations must suport deferred configuration in the way Dynamic FindBy offers:

* `@FindBy(how = How.REPEATER_ELEMENT, using = "repeater" , index ="${index}", binding = "${binding}")`

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
