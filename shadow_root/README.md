### Info

This directory contains a replica of [sukgu/shadow-automation-selenium](https://github.com/sukgu/shadow-automation-selenium) shadow ROOT DOM automation javascript API project with minor modifications:

![Shadow Root in Developer Pane Example](https://github.com/sergueik/selenium_java/blob/master/shadow_root/screenshots/capture_shadow_root.png)
 * the `#shadow-root` DOM element (leave alone its inner DOM tree) is not shown via `view-source:chrome://downloads/`

* downgraded to Junit 4 with required re-annotation of the test methods
* converted to classic maven directory layout, removed deployment-related part from maven project
* added some Selenium driver initialization boilerplate code 
* fixed the test landing page `https://www.virustotal.com` starting link `*[data-route='url']`

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
