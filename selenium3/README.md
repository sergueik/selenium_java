Info
----
Skeleton [project](https://gist.github.com/eliasnogueira/069644a12e1021125446000115d47862o) to work with [Selenium 3.0](http://goo.gl/2lZ46z), 
[Firefox 45 x64](https://ftp.mozilla.org/pub/firefox/releases/45.0/) and [geckodriver 0.10.0](https://github.com/mozilla/geckodriver/releases).


Notes 
-----
* To prevent error in `plugin-container.exe` and `mozglue.dll`, have to run tests in an 
elevated promt, and explicitly add Firefox to the PATH: `PATH=%PATH%;c:\Program Files\Mozilla Firefox`

* The latest geckodriver build for 32 bit is 0.8.0 and Selenium 3.0 compatible geckodriver is 0.10.0 )

* Gecko 0.10.0 does not support Firefox 48. It is a [known issue](https://groups.google.com/forum/#!topic/selenium-users/qBf07vob5dU). The error is:

```
Aborting on channel error.: file MessageChannel.cpp, line 2046 
Aborting on channel error.: file MessageChannel.cpp, line 2027 
```