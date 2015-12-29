/**
 * Find buttons by textual content.
 *
 * arguments[0] {Element} The scope of the search.
 * arguments[1] {string} The partial text to match.
 *
 * @return {Array.Element} The matching elements.
 */
var findByPartialButtonText = function(searchText, using) {
    using = using || document;
    var elements = using.querySelectorAll('button, input[type="button"], input[type="submit"]');
    var matches = [];
    for (var i = 0; i < elements.length; ++i) {
        var element = elements[i];
        var elementText;
        if (element.tagName.toLowerCase() == 'button') {
            elementText = element.textContent || element.innerText || '';
        } else {
            elementText = element.value;
        }
        if (elementText.indexOf(searchText) > -1) {
            matches.push(element);
        }
    }
    return matches;
};
var using = arguments[0] || document;
var searchText = arguments[1];
return findByPartialButtonText(searchText, using);