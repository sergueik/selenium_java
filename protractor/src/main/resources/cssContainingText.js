/**
 * Find elements by css selector and textual content.
 *
 * @param {string} cssSelector The css selector to match.
 * @param {string} searchText exact text or a serialized RegExp to search for
 * @param {Element} using The scope of the search.
 *
 * @return {Array.<Element>} An array of matching elements.
 */
var findByCssContainingText = function(cssSelector, searchText, using) {
  using = using || document;

  if (searchText.indexOf('__REGEXP__') === 0) {
    var match = searchText.split('__REGEXP__')[1].match(/\/(.*)\/(.*)?/);
    searchText = new RegExp(match[1], match[2] || '');
  }
  var elements = using.querySelectorAll(cssSelector);
  var matches = [];
  for (var i = 0; i < elements.length; ++i) {
    var element = elements[i];
    var elementText = element.textContent || element.innerText || '';
    var elementMatches = searchText instanceof RegExp ?
      searchText.test(elementText) :
      elementText.indexOf(searchText) > -1;
    if (elementMatches) {
      matches.push(element);
    }
  }
  return matches;
};

var using = arguments[0] || document;
var cssSelector = arguments[1];
var searchText = arguments[2];
return findByCssContainingText(cssSelector, searchText, using);
