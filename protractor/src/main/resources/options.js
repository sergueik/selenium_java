/**
 * Find elements by options.
 *
 * @param {string} optionsDescriptor The descriptor for the option
 *     (i.e. fruit for fruit in fruits).
 * @param {Element} using The scope of the search. Defaults to 'document'.
 *
 * @return {Array.<Element>} The matching elements.
 */
var findByOptions = function(optionsDescriptor, using) {
  using = using || document;
  var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:'];
  for (var p = 0; p < prefixes.length; ++p) {
    var selector = '[' + prefixes[p] + 'options="' + optionsDescriptor + '"] option';
    var elements = using.querySelectorAll(selector);
    if (elements.length) {
      return elements;
    }
  }
};
