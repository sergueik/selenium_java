/**
 * Wait until Angular has finished rendering and has
 * no outstanding $http calls before continuing.
 *
 * arguments[0] {string} The selector housing an ng-app
 * arguments[1] {function} callback
 */
var waitForAngular = function(rootSelector, callback) {
    var el = document.querySelector(rootSelector);
    try {
        if (window.getAngularTestability) {
            window.getAngularTestability(el).whenStable(callback);
            return;
        }
        if (!window.angular) {
            throw new Error("window.angular is undefined");
        }
        if (angular.getTestability) {
            angular.getTestability(el).whenStable(callback);
        } else {
            if (!angular.element(el).injector()) {
                throw new Error("root element (" + rootSelector + ") has no injector.");
            }
            angular.element(el).injector().get('$browser').
            notifyWhenNoOutstandingRequests(callback);
        }
    } catch (err) {
        callback(err.message);
    }
};

var log = function(s){ console.log(s); }

var rootSelector = arguments[0] || 'document';
var callback = arguments[1] || log;

waitForAngular(rootSelector, callback);