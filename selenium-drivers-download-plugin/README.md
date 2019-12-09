### Info

This directory contains a replica of [abhishek8908/selenium-drivers-download-plugin](https://github.com/abhishek8908/selenium-drivers-download-plugin)
project:
maven plugin which generates drivers (chromedriver, iedriverServer, edge and geckodriver) .

It was first intended to become a fork, but the changes are too substantial.

### Run application

For testing instructions, review the original project [README](https://github.com/abhishek8908/selenium-drivers-download-plugin/blob/master/README.md),
but use the snapshot `version` instead of release `version` and update the `groupId` with the actual project `groupId`
as below:
```xml
<plugin>
  <groupId>com.github.abhishek8908</groupId>
  <artifactId>selenium-drivers-download-plugin</artifactId>
  <version>1.0-SNAPSHOT</version>
```

First you may need to build and install the plugin locally:
```sh
mvn -Dmaven.test.skip=true install
```

Also, one is likely to comment the unneded driver(s) / version / os combination otherwise everything listed will be downloaded locally a every clean compile.

Instead one may utilize maven profiles to specify the same:
```xml
<profiles>
  <profile>
    <id>windows32</id>
    <activation>
      <os>
        <family>windows</family>
        <arch>x86</arch>
      </os>
    </activation>
    <properties>
      <host_os>win32</host_os>
      <driverPath>c:/temp</driverPath>
    </properties>
  </profile>
  <profile>
    <id>windows64</id>
    <activation>
      <os>
        <family>dos</family>
        <arch>amd64</arch>
      </os>
    </activation>
    <properties>
      <host_os>win64</host_os>
      <driverPath>c:/temp</driverPath>
    </properties>
  </profile>
  <!-- NOTE: the `profiles.profile.id` must be unique, some versions of Windows OS identify themselves to maven as windows or as dos -->
  <profile>
    <id>windows64_2</id>
    <activation>
      <os>
        <family>windows</family>
        <arch>amd64</arch>
      </os>
    </activation>
    <properties>
      <host_os>win64</host_os>
      <driverPath>c:/temp</driverPath>
    </properties>
  </profile>
  <profile>
    <id>mac64</id>
    <activation>
      <os>
        <family>mac</family>
        <arch>x86_64</arch>
      </os>
    </activation>
    <properties>
      <host_os>mac64</host_os>
      <selenium.version>2.48.0</selenium.version>
    </properties>
  </profile>
  <profile>
    <id>unix32</id>
    <activation>
      <os>
        <family>unix</family>
        <arch>i386</arch>
      </os>
    </activation>
    <properties>
      <host_os>linux32</host_os>
      <selenium.version>2.53.1</selenium.version>
      <driverPath>$HOME/Downloads</driverPath>
    </properties>
  </profile>
  <profile>
    <id>unix64</id>
    <activation>
      <os>
        <family>unix</family>
        <arch>amd64</arch>
      </os>
    </activation>
    <properties>
      <host_os>linux64</host_os>
      <driverPath>${HOME}/Downloads</driverPath>
    </properties>
  </profile>
</profiles>
```
and then pass the project configuration to plugin configuration:
```xml
<plugin>
  <groupId>com.github.abhishek8908</groupId>
  <artifactId>selenium-drivers-download-plugin</artifactId>
  <version>1.1-SNAPSHOT</version>
  <configuration>
    <driverPath>${driverPath}</driverPath>
    <drivers>
      <driver>
        <name>chromedriver</name>
        <version>2.40</version>
        <os>${host_os}</os>
      </driver>
    </drivers>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>generateDrivers</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```
See also the test-example directory included in this project.


### See Also

  * another WebDriver stand alone server executable download [maven plugin](https://github.com/Ardesco/driver-binary-downloader-maven-plugin)
  * [code of ru.stqa.selenium.webdriver-factory](https://github.com/barancev/webdriver-factory)
  * [some other selenium webdriver factory project](https://github.com/jflorez/webdriver-factory)
