/**
 * Find elements by options.
 *
 * arguments[0] {Element} The scope of the search.
 * arguments[1] {string} The descriptor for the option
 * (i.e. fruit for fruit in fruits).
 *
 * @return {Array.WebElement} The matching elements.
 */
var findByOptions = function(options, using) {
    using = using || document;
    var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:'];
    for (var p = 0; p < prefixes.length; ++p) {
        var selector = '[' + prefixes[p] + 'options="' + options + '"] option';
        var elements = using.querySelectorAll(selector);
        if (elements.length) {
            return elements;
        }
    }
};

var using = arguments[0] || document;
var options = arguments[1];
return findByOptions(options, using);