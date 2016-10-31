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
```
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

Below is an example of a supported  combination of old versions (except for `GECKODRIVER_VERSION`):
```
export SELENIUM_VERSION=2.47
export CHROMEDRIVER_VERSION=2.16
export FIREFOX_VERSION=40.0.3
export CHROME_VERSION=50.0.2661.75
```
For Chrome, the `CHROME_VERSION` can also set to `stable`, `unstable` or `beta` - forcing the
the `.deb` package of selected build of Chrome browser to be installed from the
[google repository](https://www.google.com/linuxrepositories/).

A handful of old Chrome build debian packages the box can download ws found on [http://www.slimjetbrowser.com](http://www.slimjetbrowser.com).
It is quite limited - check if desired version is available.


### Limitations
  * The hub is available on `http://127.0.0.1:4444/wd/hub/static/resource/hub.html` shortly after the Virtual Box had rebooted - currently there is no visual cue on when the box is ready.


  * If the screen resolution is too low, run
```
vboxmanage controlvm "Selenium Fluxbox" setvideomodehint 1280 900 32
```

### Work in Progress
 * Probing [http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/](http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/) and /or [https://google-chrome.en.uptodown.com/ubuntu/old](https://google-chrome.en.uptodown.com/ubuntu/old) for a valid past Chrome build is a
 * Enabling the [Gecko Driver](https://developer.mozilla.org/en-US/docs/Mozilla/QA/Marionette/WebDriver)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
