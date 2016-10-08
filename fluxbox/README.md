### Info

Vagrantfile for standalone Ubuntu box with tmux-driven Fluxbox autologin and Selenium Server, Chrome and Firefox
based on [Anomen/vagrant-selenium](https://github.com/Anomen/vagrant-selenium/blob/master/script.sh).

![box](https://raw.githubusercontent.com/sergueik/selenium_java/fluxbox/master/screenshots/box.png)

### Usage
```
export PROVISION_SELENIUM=true
vagrant up
```

Specific versions of Selenium Server, Firefox, Chrome and Chromedriver can be set through
`SELENIUM_VERSION`, `FIREFOX_VERSION`,`CHROME_VERSION`, `CHROMEDRIVER_VERSION`. Currently, the versions of Chrome browser are downloaded from
[http://www.slimjetbrowser.com](http://www.slimjetbrowser.com) - check if desired version is available.

Note:

### Technical details
The hub is available on `http://127.0.0.1:4444/wd/hub/static/resource/hub.html` shortly after the Virtual Box had rebooted.
If the screen resolution is too low, run
```
vboxmanage controlvm "Selenium Fluxbox" setvideomodehint 1280 900 32
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
