### Info

This directory contains a replica of [Areson/selenium-drivers-download-plugin](https://github.com/abhishek8908/selenium-drivers-download-plugin.git) 
project: 
maven plugin which generates drivers (chromedriver and geckodriver) .

It is intended to become a fork.

### Run application

For testing , use the original oriject [README](https://github.com/abhishek8908/selenium-drivers-download-plugin/blob/master/README.md), 
but replace the release `version` with snapshot `version` 
and release `groupId` with the actual project `groupId`
as below:
```xml
<plugin>
  <groupId>com.github.abhishek8908</groupId>
  <artifactId>selenium-drivers-download-plugin</artifactId>
  <version>1.0-SNAPSHOT</version>
```

and install the plugin locally.
One may also wish to comment the unneded driver(s) / version / os(es) otherwise every listed configuration 
will be downloaded locally a every compile.

See also the test-example directory included in this project.