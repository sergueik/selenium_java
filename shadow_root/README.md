### Info

This directory contains a replica of [sukgu/shadow-automation-selenium](https://github.com/sukgu/shadow-automation-selenium) __Shadow Root DOM Automation__ javascript API project with minor modifications:

* downgraded to Junit 4 with required re-annotation of the test methods
* converted to classic maven directory layout, removed deployment-related part from maven project
* added some Selenium driver initialization boilerplate code 
* fixed the test landing page `https://www.virustotal.com` starting link `*[data-route='url']`

![Shadow Root in Developer Pane Example](https://github.com/sergueik/selenium_java/blob/master/shadow_root/screenshots/capture_shadow_root.png)
 * the `#shadow-root` DOM element (leave alone its inner DOM tree) is not dislayed via menu`view-source:chrome://downloads/`

The __Shadow DOM__ is a web standard that offers component style and markup encapsulation. It is a critically important piece of the Web Components story as it ensures that a component will work in any environment even if other CSS or JavaScript is at play on the page.
A related topic is __Custom HTML tags__ . These can't be directly identified with core Selenium tools, but __Shadow Root DOM Automation__ can handle these.



### Usage

The __Shadow Root DOM Automation__  allows one get rid of fragile and Javasrcipt-heavy calls like
```java
String locator1 = "autohistory-card";
String locator2 = "button-ui";
String locator3 = "button";
// traversing nested Shadow Root elements, found quite often
JavascriptExecutor js = (JavascriptExecutor) driver;
WebElement element = (WebElement) (js.executeScript(String.format(
    "return document.querySelector('%s')"
  + ".shadowRoot.querySelector('%s')"
  + ".shadowRoot.querySelector('%s')",
  locator, locator2, locator3)));
assertThat(element, notNullValue());
```
and replace them with core Selenium-like chained methods like
```java
driver.navigate().to("https://avtokod.mos.ru/Autohistory#!/Home");
String locator1 = "autohistory-card";
String locator2 = "button-ui";
String locator3 = "button";


WebElement element1 = driver.findElement(By.tagName(locator1));
List<WebElement> elements2 = shadowDriver.getAllShadowElement(element1, locator2);
assertThat(elements2, notNullValue());
assertThat(elements2.size(), greaterThan(0));
WebElement element2 = elements2.get(0);
WebElement element3 = shadowDriver.getShadowElement(element2, locator3).get(0);
assertThat(element3, notNullValue());
```
and other methods listed below
Stream methods are also useful, helping one browsing the DOM tree inside the shadow root, from Java test.
```
driver.navigate().to("https://avtokod.mos.ru/Autohistory#!/Home");
String locator = "autohistory-card";
List<WebElement> elements = shadowDriver.findElements(locator);
  elements.stream()
    .map(o -> o.getAttribute("outerHTML"))
    .forEach(System.err::println);
```
### Features

The custom ShadowDriver class injects a Javascript library implementing a big number of API functions listed below
  * `getShadowElement`
  * `getAllShadowElement`
  * `getAttribute`
  * `isVisible`
  * `scrollTo`
  * `getParentElement`
  * `getChildElements`
  * `getSiblingElements`
  * `getSiblingElement`
  * `getNextSiblingElement`
  * `getPreviousSiblingElement`
  * `isChecked`
  * `isDisabled`
  * `findCheckboxWithLabel`
  * `findRadioWithLabel`
  * `selectCheckbox`
  * `selectRadio`
  * `selectDropdown`
  * `querySelectorAllDeep`
  * `querySelectorDeep`
  * `getObject`
  * `getAllObject`
  * `_querySelectorDeep(selector,`
  * `findMatchingElement(splitSelector,`
  * `splitByCharacterUnlessQuoted(selector,`
  * `findParentOrHost(element,`
  * `collectAllElementsDeep(selector`
  * `findAllElements`

from file `querySelector.js`.

### See Also:

  * a collection of [example projects](https://github.com/bonigarcia/mastering-junit5) for junit5 conversion
  * https://www.webcomponents.org/community/articles/introduction-to-shadow-dom
  * https://www.grapecity.com/blogs/what-is-shadow-dom-how-to-build-shadow-elements
  * https://ultimatecourses.com/blog/understanding-shadow-dom-in-web-components
  * https://www.html5rocks.com/en/tutorials/webcomponents/shadowdom/
  * https://ultimatecourses.com/blog/understanding-shadow-dom-in-web-components
  * Everything you need to know about Shadow DOM [gist](https://gist.github.com/praveenpuglia/0832da687ed5a5d7a0907046c9ef1813)
