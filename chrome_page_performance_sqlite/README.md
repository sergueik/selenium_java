### Info ###

This project is based on [Szada30/Java-Selenium-LoadTime](https://github.com/Szada30/Java-Selenium-LoadTime) project and [c# version of the chrome page performancecollector](https://github.com/sergueik/chrome_page_performance_sqlite).

The Selenium driver is used to launch the Chrome browser (alternatively [Chome Devkit Protocol driver](https://github.com/ChromeDevTools/awesome-chrome-devtools) may be used)
and load the target page then collects the page performance timings offered by Chrome browser:
```javascript
var performance = window.performance;
var timings = performance.timing;
return timings;
```
or
```javascript
var performance =
window.performance ||
  window.mozPerformance ||
  window.msPerformance ||
  window.webkitPerformance || {};

if (ua.match(/Chrome/)) {
  var network = performance.getEntries() || {};
  return JSON.stringify(network);
} else {
  var timings = performance.timing || {};
  return JSON.stringify([timings]);
}
```
and dumps the results ino SQLite database using [JDBC](https://www.tutorialspoint.com/sqlite/sqlite_java.htm)

### See Also

https://github.com/sirensolutions/kibi/tree/master/native-bindings
https://www.elastic.co/blog/logstash-jdbc-input-plugin
https://www.elastic.co/guide/en/logstash/current/plugins-inputs-sqlite.html
https://developers.google.com/web/tools/chrome-devtools/evaluate-performance/
https://developers.google.com/web/tools/chrome-devtools/network-performance/understanding-resource-timing
https://chrome.google.com/webstore/detail/web-performance-timing-ap/nllipdabkglnhmanndddgcihbcmjpfej
https://github.com/addyosmani/timing.js/blob/master/timing.js

### Author

[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
