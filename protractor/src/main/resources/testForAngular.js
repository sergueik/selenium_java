/**
 * Tests whether the angular global variable is present on a page. 
 * Retries in case the page is just loading slowly.
 *
 * arguments[0] {string} none.
 */
var attempts = arguments[0];
var callback = arguments[arguments.length - 1];
var TestForAngular = function(attempts) {
    if (window.angular && window.angular.resumeBootstrap) {
        callback(true);
    } else if (attempts < 1) {
        callback(false);
    } else {
        window.setTimeout(function() {
            check(attempts - 1)
        }, 1000);
    }
};
TestForAngular(attempts);