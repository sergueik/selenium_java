/**
 * Tests if an ngRepeat matches a repeater
 *
 * @param {string} ngRepeat The ngRepeat to test
 * @param {string} repeater The repeater to test against
 * @param {boolean} exact If the ngRepeat expression needs to match the whole
 *   repeater (not counting any `track by ...` modifier) or if it just needs to
 *   match a substring
 * @return {boolean} If the ngRepeat matched the repeater
 */
var repeaterMatch = function(ngRepeat, repeater, exact) {
  if (exact) {
    return ngRepeat.split(' track by ')[0].split(' as ')[0].split('|')[0].
    split('=')[0].trim() == repeater;
  } else {
    return ngRepeat.indexOf(repeater) != -1;
  }
}

/**
 * Find all rows of an ng-repeat.
 *
 * arguments[0] {Element} The scope of the search.
 * arguments[1] {string} The text of the repeater, e.g. 'cat in cats'.
 *
 * @return {Array.WebElement} All rows of the repeater.
 */

var findAllRepeaterRows = function(using, repeater) {

  var rows = [];
  var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:'];
  for (var p = 0; p < prefixes.length; ++p) {
    var attr = prefixes[p] + 'repeat';
    var repeatElems = using.querySelectorAll('[' + attr + ']');
    attr = attr.replace(/\\/g, '');
    for (var i = 0; i < repeatElems.length; ++i) {
      if (repeatElems[i].getAttribute(attr).indexOf(repeater) != -1) {
        rows.push(repeatElems[i]);
      }
    }
  }
  for (var p = 0; p < prefixes.length; ++p) {
    var attr = prefixes[p] + 'repeat-start';
    var repeatElems = using.querySelectorAll('[' + attr + ']');
    attr = attr.replace(/\\/g, '');
    for (var i = 0; i < repeatElems.length; ++i) {
      if (repeatElems[i].getAttribute(attr).indexOf(repeater) != -1) {
        var elem = repeatElems[i];
        while (elem.nodeType != 8 ||
          !(elem.nodeValue.indexOf(repeater) != -1)) {
          if (elem.nodeType == 1) {
            rows.push(elem);
          }
          elem = elem.nextSibling;
        }
      }
    }
  }
  return rows;
};
var using = arguments[0] || document;
var repeater = arguments[1];
return findAllRepeaterRows(using, repeater);