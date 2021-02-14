### Info

This directory contains project using individual Protracror javascript snippets without switching to `NgWebDriver`. Note that most of the API except `waitForAngular` works but it not compact to call:
```java
NgWebElement element = ngDriver.findElement(NgBy.model("first"));
assertThat(element, notNullValue());
highlight(element);
element.sendKeys("42");
```
becomes
```java
String	script = getScriptContent("model.js");
List<WebElement> elements = (List<WebElement>) executeScript(script, null, "first", null);
assertThat(elements, notNullValue());
assertThat(elements.size(), greaterThan(0));

WebElement element = elements.get(0);
assertThat(element, notNullValue());
highlight(element);
element.clear();
element.sendKeys("42");
```
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
