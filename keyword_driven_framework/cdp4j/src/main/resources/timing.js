// based on: https://github.com/addyosmani/timing.js/blob/master/timing.js
// NOTE: not computing timings.loadTime, timings.domReadyTime  etc.
(function(window) {
  'use strict';
  window.timing = window.timing || {
    getTimes: function(opt) {
      var performance = window.performance ||
        window.webkitPerformance || window.msPerformance ||
        window.mozPerformance;

      if (performance === undefined) {
        return '';
      }
      var timings = performance.timing || {};
        return JSON.stringify(timings);
    },

    getNetwork: function(opt) {
      var network = performance.getEntries() || {};
        return JSON.stringify(network);
    }
  }
})(typeof window !== 'undefined' ? window : {});