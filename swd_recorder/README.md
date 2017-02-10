### Info
This is a replica of the Standard Widget Toolkit study project [lshamsutdinov/study_swt](https://github.com/lshamsutdinov/study_swt)
which in turn is based on SWT examples from Jan Bodnar's [website](zetcode.com).

![example](https://github.com/sergueik/selenium_java/blob/master/swd_recorder/screenshots/capture2.png)

### Goals

The goal is to develop [The Standard Widget Toolkit](https://www.eclipse.org/swt/) based successor of [Selenium WebDriver Page Recorder](https://github.com/dzharii/swd-recorder) of Dmytro Zharii.
Eventually a simplified look and feel of is planned.
### Usage

The application can be run on a 32 or 64 bit Windows or Linux machine, or in Vagrantbox. It should run on OSX, but this has not been tested yet.
To start the application run
```cmd
run.cmd
```
or
```bash
./run.sh
```
It will compile and package the project and run it from the `target` directory. A number of sample classes from the "Study SWT" project will eventually disappear, and the Xtext support will be added. Also, the application may be converted to an Eclipse plugin.

### Technical Details

The `pom.xml` declares `swt.jar` dependency in a platform-specific fashion:

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
Due to some problem with JVM loader, these platform-dependent jars cannot be included simultaneously - one has to uncomment the `artifactId` specific to the host OS.

As usual with Selenium, Application is ommlycapable of runing when the right combination of versions of Selenium jar, browser drivers and browsers is used.

Few supported combination of old versions are listed below:
|                      |              |
|----------------------|--------------|
| SELENIUM_VERSION     | 2.53         |
| FIREFOX_VERSION      | 45.0.1       |
| CHROME_VERSION       | 54.0.2840.71 |
| CHROMEDRIVER_VERSION | 2.24         |

|                      |              |
|----------------------|--------------|
| SELENIUM_VERSION     | 2.47         |
| FIREFOX_VERSION      | 40.0.3       |
| CHROME_VERSION       | 50.0.2661.75 |
| CHROMEDRIVER_VERSION | 2.16         |

This is why it may be worthwhile setting up Virtualbox to compile and run an instance in.

Adding more form elements and code generator and providing the behavior is a work in progress.

### Mis. useful links
  * [main swt snippets directory](https://www.eclipse.org/swt/snippets/)
  * [swt examples on javased.com](http://www.javased.com/?api=org.eclipse.swt.widgets.FileDialog)
  * [lcaron/opal - custom swt dialogs ](https://github.com/lcaron/opal)
  * [danlucraft/jruby-swt-cookbook](https://github.com/danlucraft/jruby-swt-cookbook)
  * [danlucraft/swt](https://github.com/danlucraft/swt)
  * [fab1an/appkit toolkit for swt app design](https://github.com/fab1an/appkit)
  * [eclipse xwt](https://wiki.eclipse.org/XWT_Documentation)
  * [mono/xwt](https://github.com/mono/xwt)
  * [json2](https://github.com/douglascrockford/JSON-js)
  * [how to define conditional properties in maven](http://stackoverflow.com/questions/14430122/how-to-define-conditional-properties-in-maven)
  * [swt dependency repositories](http://stackoverflow.com/questions/5096299/maven-project-swt-3-5-dependency-any-official-public-repo)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
