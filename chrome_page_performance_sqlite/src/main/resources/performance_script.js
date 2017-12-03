// see also: https://github.com/addyosmani/timing.js/blob/master/timing.js
// for timings.loadTime,timings.domReadyTime  etc.
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
      // NOTE: legacy conversion
      if (opt && opt['stringify']) {
        return JSON.stringify(timings);
      } else {
        return timings;
      }

    },

    getNetwork: function(opt) {
      var network = performance.getEntries() || {};
      if (opt && opt['stringify']) {
        return JSON.stringify(network);
      } else {
        return network;
      }
    }
  }
})(typeof window !== 'undefined' ? window : {});