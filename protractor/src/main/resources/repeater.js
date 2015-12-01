 /**
* Find all rows of an ng-repeat.
*
* @param {string} repeater The text of the repeater, e.g. 'cat in cats'.
* @param {Element} using The scope of the search.
*
* @return {Array.<Element>} All rows of the repeater.
*/
var findAllRepeaterRows = function(repeater, using ) {
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
