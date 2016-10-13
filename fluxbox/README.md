### Info

Vagrantfile for standalone Ubuntu box containing
 * Fluxbox,
 * [tmux]](https://github.com/tmux/tmux) autologin,
 * Selenium Server,
 * Chrome/ Chrome Driver
 * Firefox with optional Gecko Driver

Based on [Anomen/vagrant-selenium](https://github.com/Anomen/vagrant-selenium/blob/master/script.sh)

![box](https://github.com/sergueik/selenium_java/blob/master/fluxbox/screenshots/box.png)

### Usage

Download the box image [trusty-server-amd64-vagrant-selenium.box](https://atlas.hashicorp.com/ubuntu/boxes/trusty64) locally.
```
export PROVISION_SELENIUM=true
vagrant up
```
The "bleeding edge" versions of the drivers do not always work well together.
Therefore one may wish to provision instance with old versions of software.
Specific versions of Selenium Server, Firefox, Chrome, Chrome Driver  etc. can be set through
`SELENIUM_VERSION`, `FIREFOX_VERSION`,`CHROME_VERSION`, `CHROMEDRIVER_VERSION`.

Below is an example of a supported  combination of old versions:
```
export SELENIUM_VERSION=2.47
export CHROMEDRIVER_VERSION=2.16
export FIREFOX_VERSION=40.0.3
export CHROME_VERSION=50.0.2661.75
```

For Chrome, the `CHROME_VERSION` can also set to  `stable`, `unstable` or `beta` - forcing the
specific version of Chrome browser `.deb` package to be installed from the
[google repository](https://www.google.com/linuxrepositories/).

A handful of old Chrome build debian packages the box can download ws found on [http://www.slimjetbrowser.com](http://www.slimjetbrowser.com).
It is quite limited - check if desired version is available.

Probing [http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/](http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/) and /or [https://google-chrome.en.uptodown.com/ubuntu/old](https://google-chrome.en.uptodown.com/ubuntu/old) for a valid past Chrome build is a
work in progress.

Note:

### Technical details
The hub is available on `http://127.0.0.1:4444/wd/hub/static/resource/hub.html` shortly after the Virtual Box had rebooted.
If the screen resolution is too low, run
```
vboxmanage controlvm "Selenium Fluxbox" setvideomodehint 1280 900 32
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
