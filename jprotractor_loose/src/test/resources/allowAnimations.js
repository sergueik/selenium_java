/**
 * invoke allowAnimations on Angular elements
 *
 * arguments[0] {Element} The scope of the search.
 * arguments[1] {Bool} The animations setting.
 */

var elemetnt = arguments[0] || document;
var state = arguments[1];
try {
    return (function(element, state) {
        var ngElement = angular.element(element);
        if (ngElement.allowAnimations) {
            // AngularDart: $testability API.
            return ngElement.allowAnimations(state);
        } else {
            // AngularJS
            var enabledFn = ngElement.injector().get('$animate').enabled;
            return (state == null) ? enabledFn() : enabledFn(state);
        }
    }).apply(this, arguments);
} catch (e) {
    throw (e instanceof Error) ? e : new Error(e);
}