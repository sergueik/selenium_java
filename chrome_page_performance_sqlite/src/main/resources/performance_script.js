// see also: https://github.com/addyosmani/timing.js/blob/master/timing.js
// for timings.loadTime,timings.domReadyTime  etc.
(function(window) {
    'use strict';
    window.timing = window.timing ||
        {
            getTimes: function() {
                var performance = window.performance ||
                    window.webkitPerformance || window.msPerformance ||
                    window.mozPerformance;

                if (performance === undefined) {
                    return '';
                }
                var timings = performance.timing || {};
                // NOTE: legacy conversion
                // return JSON.stringify(timings);
                return timings;
            },

            getNetwork: function() {
                var network = performance.getEntries() || {};
                return JSON.stringify(network);
            }
        }
})(typeof window !== 'undefined' ? window : {});