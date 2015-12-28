/**
 * Continue to bootstrap Angular. 
 * 
 * arguments[0] {array} The module names to load.
 */

angular.resumeBootstrap(arguments[0].length ? arguments[0].split(',') : []);