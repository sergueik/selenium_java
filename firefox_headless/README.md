#### Info

This directory contains an example
based on i

  * https://hacks.mozilla.org/2017/12/using-headless-mode-in-firefox/
  * https://developer.mozilla.org/en-US/Firefox/Headless_mode
  * https://www.programcreek.com/java-api-examples/?api=org.openqa.selenium.firefox.FirefoxBinary

The example google search page is not rendered correctly by Firefox 59 / geckoDriver 20 leading to sporadic exceptions:
```shell
org.openqa.selenium.WebDriverException: Element <input name="btnK" type="submit"> is not clickable at point (607,411) because another element <b> obscures it

```

in a successful run, page is printed to STDOUT:
```shell
...
<li><a href="/en-US/docs/Mozilla/Gecko" title="Gecko is the name of the layout engine developed by the Mozilla Project. It was originally named NGLayout. Gecko's function is to read web content, such as HTML, CSS, XUL, JavaScript, and render it on the user's screen or print it. In XUL-based applications Gecko is used to render the application's user interface as well.">Gecko</a></li>
...
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
