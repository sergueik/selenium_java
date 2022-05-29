/**
 * Find selected option elements in the select implemented via repeater without a model.
 *
 * arguments[0] {Element} The scope of the search.
 * arguments[1] {string} The repeater name.
 *
 * @return {Array.WebElement} The matching select elements.
 */
var findSelectedRepeaterOption = function(repeater, using) {
    var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:'];
    for (var p = 0; p < prefixes.length; ++p) {
        var selector = 'option[' + prefixes[p] + 'repeat="' + repeater + '"]:checked';        
        var elements = using.querySelectorAll(selector);
        if (elements.length) {
            return elements;
        }
    }
};
var using = arguments[0] || document;
var repeater = arguments[1];
return findSelectedRepeaterOption(repeater, using);