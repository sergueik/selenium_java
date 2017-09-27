### Info

![TestCase.xls](https://github.com/sergueik/selenium_java/blob/master/keyword_driven_framework/images/testcase.png)

This directory contains a skeleton keyword framework project based on
[ashokkhape/automation_framework_selenium](https://github.com/ashokkhape/automation_framework_selenium) and [ashokkhape/automation_framework_selenium](https://github.com/ashokkhape/automation_framework_selenium) and [selenium-webdriver-software-testing/keyword-driven-framework](https://github.com/selenium-webdriver-software-testing/keyword-driven-framework)

The project builds a runnable jar:
```bash
cp TestCase.xls ~/Desktop
mvn -Dmaven.test.skip=true clean install
java -jar target/keyword_framework-0.4-SNAPSHOT.jar
```
The launcher uses reflection to associate _keywords_ with *class methods* 
```java
private static Map<String, String> methodTable = new HashMap<>();
static {
  methodTable.put("CLICK", "clickButton");
  methodTable.put("CLICK_BUTTON", "clickButton");
...

```
- a single method may have several keywords pointing to it;
```java

String methodName = methodTable.get(keyword);
try {
  Class<?> _class = Class.forName("org.utils.KeywordLibrary");
  Method _method = _class.getMethod(methodName, Map.class);
  System.out.println(keyword + " call method: " + methodName + " with "
			+ String.join(",", params.values()));
  _method.invoke(null, params);
```

The test step arguments are passed as hash of parameters.  That is done so one does not care about the method signature. 
Also the [AngularJS](https://angularjs.org/) introduced `NgBy` 
locators which fequently require (multiple) additional arguments like e.g.
```java
@FindAll({ @FindBy(how = How.REPEATER_COLUMN, using = "row in rows", column = "name") })
private List<WebElement> friendNames;
```
The step status is returned via `params["status"]` entry, the step result (if any) is returned via `params["result"]`

### See Also

* [qaf](https://github.com/qmetry/qaf)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
