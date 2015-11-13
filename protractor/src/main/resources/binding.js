/**
 * Find a list of elements in the page by their angular binding.
 *
 * @param {string} binding The binding, e.g. {{cat.name}}.
 * @param {boolean} exactMatch Whether the binding needs to be matched exactly
 * @param {Element} using The scope of the search.
 *
 * @return {Array.<Element>} The elements containing the binding.
 */
var findBindings = function(binding, exactMatch, using) {
  using = using || document;
  var bindings = using.getElementsByClassName('ng-binding');
  var matches = [];
  for (var i = 0; i < bindings.length; ++i) {
    var dataBinding = angular.element(bindings[i]).data('$binding');
    if(dataBinding) {
      var bindingName = dataBinding.exp || dataBinding[0].exp || dataBinding;
      if (exactMatch) {
        var matcher = new RegExp('({|\\s|$|\\|)' + binding + '(}|\\s|^|\\|)');
        if (matcher.test(bindingName)) {
          matches.push(bindings[i]);
        }
      } else {
        if (bindingName.indexOf(binding) != -1) {
          matches.push(bindings[i]);
        }
      }    
    }
  }
  return matches; /* Return the whole array for webdriver.findElements. */
};

