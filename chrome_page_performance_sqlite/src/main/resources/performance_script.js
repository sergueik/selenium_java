// see also: https://github.com/addyosmani/timing.js/blob/master/timing.js
var ua = window.navigator.userAgent;
if (ua.match(/PhantomJS/)) {
  return [{}];
} else {
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
}

