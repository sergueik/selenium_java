/**
 * Return the current url using $location.absUrl().
 * 
 * arguments[0] {string} The selector housing an ng-app
 */
var el = document.querySelector(arguments[0]);
return angular.element(el).injector().get('$location').absUrl();