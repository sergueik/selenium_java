/**
 * Find elements by model name.
 *
 * @param {string} model The model name.
 * @param {Element} using The scope of the search.
 * @param {string} rootSelector The selector to use for the root app element.
 *
 * @return {Array.<Element>} The matching elements.
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
