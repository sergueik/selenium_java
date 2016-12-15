### Info

Vagrantfile for standalone Ubuntu box containing
 * Fluxbox
 * [tmux]](https://github.com/tmux/tmux) autologin
 * Selenium Server
 * Chrome and Chrome Driver
 * Firefox with optional Gecko Driver

Based on [Anomen/vagrant-selenium](https://github.com/Anomen/vagrant-selenium/blob/master/script.sh)

![box](https://github.com/sergueik/selenium_java/blob/master/fluxbox/screenshots/box.png)

### Usage

Download the box image [trusty-server-amd64-vagrant-selenium.box](https://atlas.hashicorp.com/ubuntu/boxes/trusty64) locally.
```bash
export PROVISION_SELENIUM=true
vagrant up
```
The "bleeding edge" versions of the drivers do not always work well together (e.g. throw errors like: `org.openqa.selenium.WebDriverException: unknown error: Chrome version must be >= 52.0.2743.0  (Driver info: chromedriver=2.24.417424...` ).
Therefore one may wish to provision instance with old versions of software.
Specific versions of Selenium Server, Firefox, Gecko Driver, Chrome, Chrome Driver can be set through the environment variables
`SELENIUM_VERSION`, `FIREFOX_VERSION`, `GECKODRIVER_VERSION`, `CHROME_VERSION`, `CHROMEDRIVER_VERSION`.

The error
![box](https://github.com/sergueik/selenium_java/blob/master/fluxbox/screenshots/session_error.png)
indicates a versions mismatch between Selenium, Geckodriver and Firefox, ChromeDriver and Chrome.

Few supported  combination of old versions are listed below:

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


For Chrome, the `CHROME_VERSION` can also set to `stable`, `unstable` or `beta` - forcing the `.deb` package of selected build of Chrome browser to be installed from the
[google repository](https://www.google.com/linuxrepositories/).

A handful of old Chrome build debian packages the box can download from [http://www.slimjetbrowser.com](http://www.slimjetbrowser.com/chrome/lnx/) - check if desired version is available. There is also a relatively recent 32-bit package on this site.
Note:  the error`Unsupported major.minor version 52.0` is a Java version mismatch, and you have to switch to JDK 8 by setting the `USE_ORACLE_JAVA` environment to `true`.

### Limitations
  * The hub is available on `http://127.0.0.1:4444/wd/hub/static/resource/hub.html` with some delay after the Virtual Box reboot - currently there is no visual cue on when the box is ready.

  * If the screen resolution is too low, run
```bash
vboxmanage controlvm "Selenium Fluxbox" setvideomodehint 1280 900 32
```

### Work in Progress
 * Probing [http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/](http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/) and /or [https://google-chrome.en.uptodown.com/ubuntu/old](https://google-chrome.en.uptodown.com/ubuntu/old) for a valid past Chrome build is a
 * Enabling the [Gecko Driver](https://developer.mozilla.org/en-US/docs/Mozilla/QA/Marionette/WebDriver)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)