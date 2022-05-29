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
List<WebElement> elements = (List<WebElement>) executeScript(script, getScriptContent("model.js"), "first", null);
assertThat(elements, notNullValue());
assertThat(elements.size(), greaterThan(0));

WebElement element = elements.get(0);
assertThat(element, notNullValue());
highlight(element);
element.clear();
element.sendKeys("42");
```
One can also define custom `FluentWait` by placing all the Protracror code inside the `apply` method like below
```java
try {
  wait.until(new ExpectedCondition<Boolean>() {
	  @Override
  	public Boolean apply(WebDriver d) {
	  	elements = (List<WebElement>) executeScript( getScriptContent("binding.js"), null, "latest", null);
		  Boolean result = false;
		  if (elements != null && elements.size() > 0) {
			  element = elements.get(0);
		  	highlight(element);
	  		String text = element.getText();
		  	result = !text.contains(".");
			  System.err.println("in apply " + cnt + ": Text = " + text + "\nresult = " + result.toString());
		  } else {
			  System.err.println("in apply " + cnt + ": element not yet found");
			  result = false;
		  }
		  return result;
  	}
  });
} catch (Exception e) {
  System.err.println("Exception in custom wait: " + e.toString());
}
```

### See Also
   * [announcement](https://github.com/angular/protractor/issues/5502) of end of the development of Protractor by the end of 2022 in conjunction with Angular v15.of end of the development of Protractor by the end of 2022 in conjunction with Angular v15. by Angular team 
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
