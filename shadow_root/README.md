### Info

This directory contains a replica of [sukgu/shadow-automation-selenium](https://github.com/sukgu/shadow-automation-selenium) shadow ROOT DOM automation javascript API project with minor modifications:

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
