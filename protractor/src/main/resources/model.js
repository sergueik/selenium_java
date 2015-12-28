/**
 * Find elements by model name.
 *
 * arguments[0] {Element} The scope of the search.
 * arguments[1] {string} The model name.
 *
 * @return {Array.WebElement} The matching input elements.
 */
var findByModel = function(model, using, rootSelector) {
    var root = document.querySelector(rootSelector || 'body');
    using = using || document;
    if (angular.getTestability) {
        return angular.getTestability(root).
        findModels(using, model, true);
    }
    var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:'];
    for (var p = 0; p < prefixes.length; ++p) {
        var selector = '[' + prefixes[p] + 'model="' + model + '"]';
        var elements = using.querySelectorAll(selector);
        if (elements.length) {
            return elements;
        }
    }
};
var using = arguments[0] || document;
var model = arguments[1];
var rootSelector = arguments[2];
return findByModel(model, using, rootSelector);