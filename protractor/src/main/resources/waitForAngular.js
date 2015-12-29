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
            throw new Error('window.angular is undefined. This could be either ' +
                'because this is a non-angular page or because your test involves ' +
                'client-side navigation, which can interfere with Protractor\'s ' +
                'bootstrapping. See http://git.io/v4gXM for details');
        }
        if (angular.getTestability) {
            angular.getTestability(el).whenStable(callback);
        } else {
            if (!angular.element(el).injector()) {
                throw new Error('root element (' + rootSelector + ') has no injector.' +
                    ' this may mean it is not inside ng-app.');
            }
            angular.element(el).injector().get('$browser').
            notifyWhenNoOutstandingRequests(callback);
        }
    } catch (err) {
        callback(err.message);
    }
};

var rootSelector = arguments[0];
var callback = arguments[1];

waitForAngular(rootSelector, callback);