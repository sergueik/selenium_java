# jpagefactory

## Summary:

JPageFactory is a set of Selenium Java `FindBy` annotations that eases the usage of Selenium page factory pattern for automating Angular based web application. 

JPageFactory is implemented based on [JProtractor fork from sergueik](https://github.com/sergueik/jProtractor) and provides the following web elements locators as original Selenium `FindBy` annotations:
* ID
* Name
* XPath
* CSS
* Class Name
* Tag Name
* Link Text / Partial Link Text
   
as well as the following locators from Protractor:
* Binding
* Input
* Model
* Repeater
* Button Text / Partial Button Text
* Options / Selected Options / Repeater Selected Options

JPageFactory also supports defining different locators of the same web element for both desktop web and mobile web applications (Angular applications running in mobile browsers).

## Initializing page object
Using the following page factory APIs to initializing the page object:
```
JPageFactory.initElements(SearchContext searchContext, Channel channel, Object page);

JPageFactory.initWebElements(SearchContext searchContext, Object page);

JPageFacotry.initMobileElements(SearchContext searchContext, Object page);
```
 
## Locator Usage:
Here are examples of using locators from Protractor:

**- Find element by Angular binding**  
_html_: `<h2>{{latest}}</h2>`  
_annotation example_: 
```
@FindBy(how = How.BINDING, using = "latest")
private WebElement latestResult;
```

**- Find input element with Angular ng-model**  
_html_: `<input ng-model="first" type="text" class="input-small"/>`  
_annotation example_: 
```
@FindBy(how = How.INPUT, using = "first")
private WebElement firstNumber;
```  

**- Find element with Angular ng-model**  
_html_: `<input ng-model="first" type="text" class="input-small"/>`  
_annotation example_: 
```
@FindBy(how = How.MODEL, using = "first")
private WebElement firstNumber;
```

**- Find elements of Angular ng-repeater**  
_html_: 
```
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
```
@FindAll({
    @FindBy(how = How.REPEATER, using = "result in memory")
})
private List<WebElement> history;
```

**- Find element with button text**  
_html_: `<button ng-click="doAddition()" id="gobutton" class="btn">Go!</button>`  
_annotation example_: 
```
@FindBy(how = How.BUTTON_TEXT, using = "Go!")
private WebElement goButton;
```

**- Find element with partial button text**  
_html_: `<button ng-click="doAddition()" id="gobutton" class="btn">Go!</button>`  
_annotation example_: 
```
@FindBy(how = How.PARTIAL_BUTTON_TEXT, using = "Go")
private WebElement goButton;
```

**- Find select options**  
_html_: 
```
<select ng-model="operator" class="span1"
    ng-options="value for (key, value) in operators">
</select>
```
_annotation example_:
```
@FindBy(how = How.MODEL, using = "operator")
private WebElement operatorSelect;

// find all option elements
@FindAll({
    @FindBy(how = How.OPTIONS, using = "value for (key, value) in operators")
})
private List<WebElement> operationSelectorOptions;

// find selected option
@FindBy(how = How.SELECTED_OPTION, using = "operator")
private WebElement selectedOption;

```

## Use annotation for both desktop and mobile web applications:

JPageFactory annotations work for both desktop and mobile web applications.
When defining the annotation using `how` and `using` fields, the same locator will be used for both channels.  

One can define the annotation in the follow way to use different locators for desktop and mobile channels:  
`@FindBy(howWeb = How.INPUT, usingWeb = "second", howMobile = How.XPATH, usingMobile = "//input[@ng-model='second']")`  
In the above example, JPageFactory will find the input element by its `ng-model` attribute for desktop channel, and use XPath locator to find the same input element when running on mobile devices.

When initializing the page object, one must specify the `channel` argument, so that it will use the corresponding locators:
```
Channel channel = Channel.WEB;
if (isMobile) {
    channel = Channel.MOBILE;
}
JPageFactory.initElements(ngDriver, channel, superCalculatorPage);
```

If one element only appears on certain channel, the `howXXX` and `usingXXX` fields for that channel can be skipped when defining the annotation.

## Code example

Some code examples are provided in the JUnit test cases in the project. One can get it from the downloaded source code.
The example tests automate the same [Super Calculator](http://juliemr.github.io/protractor-demo/) demo application as from Protractor official tutorial.

In order to run the example tests, Google Chrome browser is required.

To run example tests on Google Chrome emulator for checking how JPageFactory work on mobile channel, change the following flag to `true` in class `SuperCalculatorTest`:
```
// change this boolean flag to true to run on chrome emulator
private boolean isMobile = false;
```
