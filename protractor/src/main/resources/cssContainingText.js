/**
 * Find elements by css selector and textual content.
 *
 * @param {string} cssSelector The css selector to match.
 * @param {string} searchText The exact text to match.
 * @param {Element} using The scope of the search.
 *
 * @return {Array.<Element>} An array of matching elements.
 */
var findByCssContainingText = function(cssSelector, searchText, using) {
  using = using || document;

  var elements = using.querySelectorAll(cssSelector);
  var matches = [];
  for (var i = 0; i < elements.length; ++i) {
    var element = elements[i];
    var elementText = element.textContent || element.innerText || '';
    if (elementText.indexOf(searchText) > -1) {
      matches.push(element);
    }
  }
  return matches;
};

var using = arguments[0] || document;
var cssSelector = arguments[1];
var searchText = arguments[2];
return findByCssContainingText(cssSelector, searchText, using);