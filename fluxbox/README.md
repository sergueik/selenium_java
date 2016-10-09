### Info

Vagrantfile for standalone Ubuntu box with Fluxbox, [tmux]](https://github.com/tmux/tmux) autologin, Selenium Server, Chrome and Firefox
based on [Anomen/vagrant-selenium](https://github.com/Anomen/vagrant-selenium/blob/master/script.sh).

![box](https://github.com/sergueik/selenium_java/blob/master/fluxbox/screenshots/box.png)

### Usage
```
export PROVISION_SELENIUM=true
```
optionally, specific versions of Selenium Server, Firefox, Chrome and Chromedriver can be set through
`SELENIUM_VERSION`, `FIREFOX_VERSION`,`CHROME_VERSION`, `CHROMEDRIVER_VERSION`, e.g.:
```
export SELENIUM_VERSION=2.47
export CHROMEDRIVER_VERSION=CHROMEDRIVER_VERSION=2.16
export FIREFOX_VERSION=40.0.3
export CHROME_VERSION=50.0.2661.75
```
followed by
```
vagrant up
```

The `CHROME_VERSION` can also set to  `stable`, `unstable` or `beta` - the relevant version of Chrome browser `.deb` package will be installed from the 
[google repository](https://www.google.com/linuxrepositories/).

A handful of old Chrome build debian packages can be downloaded from [http://www.slimjetbrowser.com](http://www.slimjetbrowser.com) - check if desired version is available. 

Accessing the `http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/` for a valid past build is work in progress. 

Note:

### Technical details
The hub is available on `http://127.0.0.1:4444/wd/hub/static/resource/hub.html` shortly after the Virtual Box had rebooted.
If the screen resolution is too low, run
```
vboxmanage controlvm "Selenium Fluxbox" setvideomodehint 1280 900 32
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
