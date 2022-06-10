#### Info

This directory contains an example headless Fifefox project
based on https://hacks.mozilla.org/2017/12/using-headless-mode-in-firefox/

The code originally tested with Ubuntu __16.04__ __x64__, Firefox __59__ geckoDriver __20__. 

The project has been stale for some time, then tested with Firefox __101__ (32 bit) running on Windows __10__ VM. To detect the headless run, an environment variable `WINDOWS_NO_DISPLAY` is set by the launcher script which itself is run by [Windowws Scheduled Task](https://www.windowscentral.com/how-create-automated-task-using-task-scheduler-windows-10) with one minute delay after the reboot event.

### Note

The success of the test is sensitive to combinaton of versions of Selenium and Firefox: old versions of the latter fail in runtime e.g. with `example.MinimalTest: Missing 'marionetteProtocol' field in handshake(..). Check the project history for `pom.xml` change to tailor this skeleton project to older Forefox versions.

#### See also

  * https://www.programcreek.com/java-api-examples/?api=org.openqa.selenium.firefox.FirefoxBinary
  * https://developer.mozilla.org/en-US/Firefox/Headless_mode


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
