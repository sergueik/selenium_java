### Info

Selenium WebDriver Extension Toolkit (SWET) is a OS-independent successor to the
[Selenium WebDriver Page Recorder (SWD)](https://github.com/dzharii/swd-recorder) of
Dmytro Zharii. While the latter is .Net Windows Forms/ Razor application,
SWEET is based on the [Standard Widget Toolkit](https://www.eclipse.org/swt/) with some third party widgets (currently, [Opal](https://github.com/lcaron/opal)
therefore it can be run on Windows, Mac or Linux, 32 or 64 bit platforms. Eventually SWET might become an Eclipse plugin.

The application is developed in Ecipse with [SWT Designer/Window Builder](http://www.vogella.com/tutorials/EclipseWindowBuilder/article.html),
on Ubuntu 16.04 and Windows.
The [Jtwig](http://jtwig.org/) is the code generatsor used.

![OSX Example](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/capture1.png)

![Ubuntu Example](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/capture2.png)

![Windows Example](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/capture3.png)

### Goals

Eventually the full functionality of SWD is to be achieved.
SWEET isn't yet ready for users, but you can help! Continue reading for info on how to get the dev environment setup.

### Usage

To start the application run
```powershell
. .\run.ps1
```
or
```cmd
run.cmd
```
on Windows or
```bash
./run.sh
```
on Linux or a Mac.
The runner script downloads those dependency jar(s), that are not hosted on Maven Central repository,
compiles and packages the project using maven
and runs the application jar from the `target` directory.
The runner script can be used to launch individual basic examples from the Standard Widget Toolkit study
project [lshamsutdinov/study_swt](https://github.com/lshamsutdinov/study_swt),
which in turn is based on SWT examples from Jan Bodnar's [website](zetcode.com)
a number os such examples is still checked in in the project directory but will eventually disappear.

### Toolbar Buttons

![launch](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/src/main/resources/launch.png)
launches the browser

![launch](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/src/main/resources/find.png)
injects the [SWD Element Searcher script](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/src/main/resources/ElementSearch.js) into the page then
loops polling the page witing for user to select some element via right click and to fill and submit the form:
![SWD Table](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/swd_table.png)

The Java reads back the result once it available and adds a breadcrump button:
![breadcumps](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/breadcumps.png)

The breadcrump button opens the form dialog with the details of the element:
![form](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/form.png)

The flowchart button
![flowchart](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/src/main/resources/flowchart.png)

starts codegeneration using [Jtwig](http://jtwig.org/) tempate and `elementData` hash and opens result in a separate dialog:
![codegen](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/codegen.png)

There is also a demo button that executes these actions automatically (for one element):
![demo](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/src/main/resources/demo.png)

Currently project is hardcoded to use Chrome browser on Windows os, and Firefox on the rest of platforms.
The YAML configuration will be fuly integrated shotly.
Eventually other common formats: YAML, JSON, POI or Java properties file - will be supported.


### Operation
SWET and SWD inject certain Javascript code `ElementSearch.js` into the page, and waits polling for the speficic
`document.swdpr_command` object to appear on that page. This object is created  by the `ElementSearch.js`
when user selects the specific element on the page he is interested to access in the test script,
and confirms the selection by entering the name of the element and clicking.
The `document.swdpr_command` object contains certain properties of the selected element:

* Absolute XPath, that looks like `/html/body/*[@id = "www-wikipedia-org"]/div[1]/div[1]/img[1]`
* Attribute-extended XPath that looks like `//a[@href="/script/answers"]/img[@src="question.png"]`
* Firebug-style cssSelector (all classes attached to all parent nodes), that look like `ul.nav-links li.nav-links__item div.central.featured.logo-wrapper > img.central.featured-logo`
* Element text (transalted under the hood into XPath `[contains()]` expression).
* Input for Angular Protractor-specific locators `repeater`, `binding`, `model`, `repeaterRow` etc. (WIP)
* Element ID (when available)
Auto-generated locators often become unnecessarily long, e.g. for the facebook logo one may get:
```css
div#blueBarDOMInspector > div._53jh > div.loggedout_menubar_container >
div.clearfix.loggedout_menubar > div.lfloat._ohe >
h1 > a > i.fb_logo.img.sp_Mlxwn39jCAE.sx_896ebb
```
Currently SWET does not know a way of shortening them automatically. Adding smart locator generators is a work in progress.

### Platform-specific information

The project is written in java, but its
main `swt.jar` dependency is currently declared in the the `pom.xml` in a platform-specific fashion:

```xml
  <properties>
    <eclipse.swt.version>4.3</eclipse.swt.version>
    <!--
    <eclipse.swt.version>[3.6,4.5.0)</eclipse.swt.version>
    -->
    <eclipse.swt.artifactId>org.eclipse.swt.win32.win32.x86_64</eclipse.swt.artifactId>
    <!--
    <eclipse.swt.artifactId>org.eclipse.swt.gtk.linux.x86_64</eclipse.swt.artifactId>
    <eclipse.swt.artifactId>org.eclipse.swt.gtk.linux.x86</eclipse.swt.artifactId>
    -->
  </properties>
  <dependencies>
    <dependency>
			<groupId>org.eclipse.swt</groupId>
			<artifactId>${eclipse.swt.artifactId}</artifactId>
      <version>${eclipse.swt.version}</version>
			<!-- <version>[3.6,4.5.0)</version> -->
		</dependency>
    ...
```
Due to some problem with JVM loader, these platform-dependent jars cannot be included simultaneously - one has to uncomment the OS-specific `artifactId` an comment the rest.

### Compinent Versions
As usual with Selenium, Application only runnable with the matching combination of versions of Selenium jar, browser driver and browsers is used.

Currently supported combination of versions is

|                      |              |
|----------------------|--------------|
| SELENIUM_VERSION     | __2.53.1__       |
| FIREFOX_VERSION      | __45.0.1__       |
| CHROME_VERSION       | __54.0.X__ |
| CHROMEDRIVER_VERSION | __2.24__         |


One can download virtually every old build of Firefox from
https://ftp.mozilla.org/pub/firefox/releases, and selected old builds of Chrome from
http://www.slimjetbrowser.com/chrome/, for other browsers the download locations vary.

This is why it may be worthwhile setting up Virtual Box e.g. [selenium-fluxbox](https://github.com/sergueik/selenium-fluxbox) to run the appliation with fixed downlevel browser versions.

### Safari Testing
If you have Mac OSX 10.12.X Sierra / Safari 10.X , then the Apple Safari driver would be installed automatically,
but it does not seems to work with Selenium __2.53__.
For earlier releases, you have to downgrade the Selenium version in the `pom.xml` to __2.48__
then follow the [Running Selenium Tests in Safari Browser](http://toolsqa.com/selenium-webdriver/running-tests-in-safari-browser).
For Mac testing, [Sierra Final 10.12](https://techsviewer.com/install-macos-sierra-virtualbox-windows/) Virtual Box by TechReviews is being used.
Overall,s working with Safari browser is somewhat flaky.

### Configuration, saving and loading

The element locators collected by SWET may be saved in YAML format, using [snakeyaml](https://bitbucket.org/asomov/snakeyaml).
Example:
```yaml
version: '1.0'
created: '2017-02-21'
seleniumVersion: '2.53.1'

# Browser parameters
browser:
  name: firefox
  platform: linux
  version: '45.0.1'

# Browser parameters
browser:
  name: chrome
  platform: windows
  version: '54.0.2840.71'
  driverVersion: '2.24'
  driverPath: 'c:/java/selenium/chromedriver.exe'

# Selenium Browsers
browsers:
  - chrome
  - firefox

# Elements
elements:
  '9882f662-5593-4b8a-a21d-8e5a6d1bcc6a':
    ElementCodeName: logo
    ElementCssSelector: 'img#hplogo'
    ElementXPath: id("hplogo")
    Url: http://www.google.com
  'f26d01af-1':
    ElementCodeName: 'element name'
    ElementId: ''
    ElementCssSelector: 'article#home > section.homepage-content-container > div.homepage-parsys-container > div.farefinderwidget.parbase.section.specialofferswidget > div > farefinder-compact > div.section-wrapper > div.home-info.full-width > div > h3 > span.highlight'
    ElementXPath: "id(\"home\")/section[1]/div[2]/div[1]/div[1]/farefinder-compact[1]/div[1]/div[1]/div[1]/h3[1]/span[1]"
```

### Work in Progress
* UI improvements adding more form elements
* Testing with Safari and variety of IE / Edge browsers
* Adding the code generator templates
* Codebase cleanup

### Links

#### SWT

  * [main SWT snippets directory](https://www.eclipse.org/swt/snippets/)
  * [SWT examples on javased.com](http://www.javased.com/?api=org.eclipse.swt.widgets.FileDialog)
  * [SWT - Tutorial by Lars Vogel, Simon Scholz](http://www.vogella.com/tutorials/SWT/article.html)
  * [Opal Project (SWT new widgets library) by Laurent Caron](https://github.com/lcaron/opal)
  * [Nebula - Supplemental Widgets for SWT](https://github.com/eclipse/nebula)
  * [danlucraft/jruby-swt-cookbook](https://github.com/danlucraft/jruby-swt-cookbook)
  * [danlucraft/swt](https://github.com/danlucraft/swt)
  * [fab1an/appkit toolkit for swt app design](https://github.com/fab1an/appkit)
  * [SWT dependency repositories](http://stackoverflow.com/questions/5096299/maven-project-swt-3-5-dependency-any-official-public-repo)
  * [SWT jar ANT helper](http://mchr3k.github.io/swtjar/)
  * [Examples](http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/CatalogSWT-JFace-Eclipse.htm)
  * [Examples](https://github.com/ReadyTalk/avian-swt-examples)
  * [swt-bling](https://github.com/ReadyTalk/swt-bling)
  * [Multiplatform SWT](https://github.com/jendap/multiplatform-swt)
  * [SWT custom preference dialog](https://github.com/prasser/swtpreferences)
  * [SWT/JFace Utilities](https://github.com/Albertus82/JFaceUtils)
  * [SWT/WMI](https://github.com/ctron/wmisample)
  * [SWT Tools](https://github.com/bp-FLN/SWT-Tools)
  * [SWT choice dialog customization](https://github.com/prasser/swtchoices)
  * [SWT Browser component based recorder](https://github.com/itspanzi/swt-browser-recorder-spike)

#### Code Generation

  * [Jtwig](http://jtwig.org/)
  * [Thymeleaf](http://www.thymeleaf.org/)
  * [StringTemplate](http://www.stringtemplate.org/)

### Selenium Locator Strategies

  * [Choosing Effective XPaths](http://toolsqa.com/selenium-webdriver/choosing-effective-xpath/)
  * [Use XPath locators efficiently](http://bitbar.com/appium-tip-18-how-to-use-xpath-locators-efficiently/)

### YAML
  * [snakeyaml](https://bitbucket.org/asomov/snakeyaml)

### Javascript injection
  * [Keymaster](https://github.com/madrobby/keymaster)

#### Misc.

  * [how to define conditional properties in maven](http://stackoverflow.com/questions/14430122/how-to-define-conditional-properties-in-maven)
  * [eclipse xwt](https://wiki.eclipse.org/XWT_Documentation)
  * [mono/xwt](https://github.com/mono/xwt)
  * [json2](https://github.com/douglascrockford/JSON-js)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

