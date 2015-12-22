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