# jPageFactory

## Summary:

__JPageFactory___ is a set of Selenium Java `FindBy` annotations that eases the usage of [Selenium page factory pattern](http://toolsqa.com/selenium-webdriver/page-object-pattern-model-page-factory/) for automating __Angular__ based web application.

__JPageFactory__ is implemented on top of [JProtractor](https://github.com/caarlos0/jProtractor) and provides extended set of web elements locators annotations with a syntax following the core Selenium `FindBy`:

+ Core Selenum
  * ID
  * Name
  * XPath
  * CSS
  * Class Name
  * Tag Name
  * Link Text
  * Partial Link Text

+ Protractor
  * Binding
  * Input
  * Model
  * Repeater
  * Button Text
  * Partial Button Text
  * Options
  * Selected Options
  * Repeater Selected Options
  * CssContainingText
  * RepeaterColumn
  * RepeaterRow
  * RepeaterElement

__JPageFactory__ also supports defining different locators of the same web element for both desktop web and mobile web applications (Angular applications running in mobile browsers).

## Initializing page object
Using the following page factory APIs to initializing the page object:
```java
JPageFactory.initElements(SearchContext searchContext, Channel channel, Object page);

JPageFactory.initWebElements(SearchContext searchContext, Object page);

JPageFacotry.initMobileElements(SearchContext searchContext, Object page);
```

## Locator Usage:
Here are examples of using locators from Protractor:

**- Find element by Angular binding**

_html_:
```html
<h2>{{latest}}</h2>
```
_annotation example_:
```java
@FindBy(how = How.BINDING, using = "latest")
private WebElement latestResult;
```

**- Find input element with Angular ng-model**

_html_:
```html
<input ng-model="first" type="text" class="input-small"/>
```
_annotation example_:
```java
@FindBy(how = How.INPUT, using = "first")
private WebElement firstNumber;
```

**- Find Angular directive by ng-model**

_html_:
```html
<am-multiselect class="input-lg" ng-model="selectedCar" ms-header="Select Some Cars">
```
_annotation example_:
```java
@FindBy(how = How.MODEL, using = "selectedCar")
private WebElement _directive;
```

**- Find elements of Angular ng-repeater**

_html_:
```html
<tr ng-repeat="result in memory">
    <td>
        {{result.timestamp | date:'mediumTime'}}
    </td>
    <td>
        <span>{{result.first}}</span>
        <span>{{result.operator}}</span>
        <span>{{result.second}}</span>
    </td>
    <td>{{result.value}}</td>
    </tr>
```
_annotation example_:
```java
@FindAll({
    @FindBy(how = How.REPEATER, using = "result in memory")
})
private List<WebElement> history;
```

**- Find element in the repeater, binding by Angular repeater column**

_html_:
```html
 <table>
    <tr ng-repeat="row in rows | filter : search">
      <td>{{$index+1}}</td>
      <td>{{name}}</td>
    </tr>
  </table>
```
_annotation example_:
```java
@FindAll({ @FindBy(how = How.REPEATER_COLUMN, using = "row in rows", column = "name") })
private List<WebElement> friendNames;
```

**- Find element with button text**

_html_:
```html
<button ng-click="doAddition()" id="gobutton" class="btn">Go!</button>
```

_annotation example_:
```java
@FindBy(how = How.BUTTON_TEXT, using = "Go!")
private WebElement goButton;
```

**- Find Angular directive by partial button text**

_html_:
```html
<am-multiselect class="input-lg" multiple="true" ms-selected ="There are {{selectedCar.length}} car(s) selected" ng-model="selectedCar" ms-header="Select Some Cars">
```

_annotation example_:
```java
@FindBy(how = How.PARTIAL_BUTTON_TEXT, using = "Select Some Cars")
private WebElement _multiselect;
```

**- Find all select options elements**

_html_:
```html
<select ng-model="operator" class="span1"
    ng-options="value for (key, value) in operators">
</select>
```
_annotation example_:
```java
@FindBy(how = How.MODEL, using = "operator")
private WebElement operatorSelect;

@FindAll({
    @FindBy(how = How.OPTIONS, using = "value for (key, value) in operators")
})
private List<WebElement> operationSelectorOptions;
```
**- Find selected option element**

_html_:
```html
<select ng-model="operator" class="span1"
    ng-options="value for (key, value) in operators">
</select>
```

_annotation example_:
```java

@FindBy(how = How.SELECTED_OPTION, using = "operator")
private WebElement selectedOption;
```
**- Find element for CSS Selector and text**

_html_

http://dalelotts.github.io/angular-bootstrap-datetimepicker/
```html
<span class="hour" data-ng-repeat="dateObject in data.dates"
data-ng-click="changeView(data.nextView, dateObject, $event)">10:00 AM</span>
```
_annotation example_:
```java
String timeOfDay = "10:00 AM";
WebElement ng_hour = ng_element.findElements(
    NgBy.cssContainingText("span.hour", timeOfDay)).get(0);
```

_html_

http://jaykanakiya.com/demos/angular-js-todolist/
```html
<ul ng-repeat="todos in model track by $index">
  <li class="todoTask ng-scope" ng-repeat="todo in todos.list | filter:showFn | filter :todoSearch ">
    <span class="todoName ng-binding" ng-bind="value">Create an Angular-js TodoList</span>
    <button type="button" ng-click="deleteTodo(todo)">&#xD7;</button>
  </li>
  <li class="todoTask ng-scope" ng-repeat="todo in todos.list | filter:showFn | filter :todoSearch ">
    <span class="todoName ng-binding" ng-bind="value">Understanding Angular-js Directives</span>
    <button type="button" class="close pull-right" aria-hidden="true" ng-click="deleteTodo(todo)">&#xD7;</button>
  </li>
</ul>
```
_annotation example_:
```java
@FindAll({
	@FindBy(how = How.CSS_CONTAINING_TEXT, using = "span.todoName", 
    text = "Angular-js") })
	private List<WebElement> _rows;
```


## Use annotation for both desktop and mobile web applications:

__JPageFactory__ annotations work for both desktop and mobile web applications.
When defining the annotation using `how` and `using` fields, the same locator will be used for both channels.

One can define the annotation in the follow way to use different locators for desktop and mobile channels:
```java
@FindBy(howWeb = How.INPUT, usingWeb = "second", howMobile = How.XPATH, usingMobile = "//input[@ng-model='second']")
```
In the above example, __JPageFactory__ will find the input element by its `ng-model` attribute for desktop channel, and use XPath locator to find the same input element when running on mobile devices.

When initializing the page object, one must specify the `channel` argument, so that it will use the corresponding locators:
```java
Channel channel = Channel.WEB;
if (isMobile) {
    channel = Channel.MOBILE;
}
JPageFactory.initElements(ngDriver, channel, superCalculatorPage);
```

If one element only appears on certain channel, the `howXXX` and `usingXXX` fields for that channel can be skipped when defining the annotation.

## Code example

Some code examples are provided in the JUnit test cases in the project. One can get it from the downloaded source code.
One example automates the [Super Calculator](http://juliemr.github.io/protractor-demo/) demo application from official __Protractor__ tutorial.
Other use [Angular Todo List](http://jaykanakiya.com/demos/angular-js-todolist/), [qualityshepherd Protractor example](https://qualityshepherd.com/angular/friends/) and [Angular Multi-Select](http://amitava82.github.io/angular-multiselect/)

In order to run the example tests, Google Chrome browser is required.

Copy the `jprotractor-1.2-SNAPSHOT.jar` jar into `src/main/resources`.

Make sure to import:
```java
import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;

import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;
import com.jprotractor.NgBy;
```

Note that Eclipse _organize imports_ command would [remove](https://stackoverflow.com/questions/13485313/make-eclipse-remove-unused-imports-but-keep-unresolved) the unresolved `jprotractor` lines.

To check how __JPageFactory__ work on mobile channel, run example tests on Google Chrome emulator by setting the following flag to `true`:
```java
// change this boolean flag to true to run on chrome emulator
private boolean isMobile = false;
```

### Note
   * [announcement](https://github.com/angular/protractor/issues/5502) of end of the development of Protractor by the end of 2022 in conjunction with Angular v15.of end of the development of Protractor by the end of 2022 in conjunction with Angular v15. by Angular team 

### Authors

* [He Huang](huanghe389@gmail.com)
* [Serguei Kouzmine](kouzmine_serguei@yahoo.com)
