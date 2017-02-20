### Info
Selenium WebDriver Eclipse Toolkit is a OS-independent successor to the
[Selenium WebDriver Page Recorder](https://github.com/dzharii/swd-recorder) of
Dmytro Zharii based on [The Standard Widget Toolkit](https://www.eclipse.org/swt/), and [SWT new widgets library Opal Project by Laurent Caron](https://github.com/lcaron/opal)

![OSX Example](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/capture1.png)

![Ubuntu Example](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/capture2.png)
### Goals

Eventually the full functionality of SWD is to be achieved.

UI is a work in progress: adding more form elements and the code generator is a work in progress.

A number of very basic examples from the Standard Widget Toolkit study project
[lshamsutdinov/study_swt](https://github.com/lshamsutdinov/study_swt)
(which in turn is based on SWT examples from Jan Bodnar's [website](zetcode.com)) is currently checked in but will eventually disappear.

Also, the standalone SWT application jar might be converted to an Eclipse plugin.

The application can be run on a 32 or 64 bit Windows, Mac or Linux, or in Virtual Box.
For Mac testing the [Sierra Final 10.12 Virtual Box image vdi by TechReviews](https://techsviewer.com/install-macos-sierra-virtualbox-windows/)
have been used.

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
on Linuxor Mac. The runner script downloads those dependency jar(s), that are not hosted on Maven Central repository, compiles and packages the project in maven
and runs it it from the `target` directory.

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

There is also a demo button that does these actions automatically:
![demo](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/src/main/resources/demo.png)

Currently project is hardcoded to use Chrome browser on Windows, and Firefox on the rest of platforms.
The configuration will be added shotly - it will use a yaml, json or java properties file.

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
| SELENIUM_VERSION     | 2.53.1       |
| FIREFOX_VERSION      | 45.0.1       |
| CHROME_VERSION       | 54.0.2840.71 |
| CHROMEDRIVER_VERSION | 2.24         |

One can download virtually every old build of Firefox from
https://ftp.mozilla.org/pub/firefox/releases, and selected old builds of Chrome from
http://www.slimjetbrowser.com/chrome/, for other browsers locations vary.

This is why it may be worthwhile setting up Virtual Box to compile and run an instance in.

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
  * [Examples](http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/CatalogSWT-JFace-Eclipse.htm)
  * [Examples](https://github.com/ReadyTalk/avian-swt-examples)

#### Code Generation

  * [Jtwig](http://jtwig.org/)
  * [Thymeleaf](http://www.thymeleaf.org/)
  * [StringTemplate](http://www.stringtemplate.org/)

### Javascript injection
  * [Keymaster](https://github.com/madrobby/keymaster)

#### Misc.

  * [how to define conditional properties in maven](http://stackoverflow.com/questions/14430122/how-to-define-conditional-properties-in-maven)
  * [eclipse xwt](https://wiki.eclipse.org/XWT_Documentation)
  * [mono/xwt](https://github.com/mono/xwt)
  * [json2](https://github.com/douglascrockford/JSON-js)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

