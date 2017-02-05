cssSelectorOfElement = function(element) {
	var selector = '';

	if (element == document)
		selector = 'body';
	else {
		var attr = ['ng-model', 'ng-href', 'name', 'aria-label'].reduce(function(a, b) {
			return a || (element.getAttribute(b) ? b : null);
		}, null);
		if (attr)
			selector = element.tagName.toLowerCase() + '[' + attr + '="' + element.getAttribute(attr).replace(/\\/g, '\\\\').replace(/\'/g, '\\\'').replace(/\"/g, '\\"').replace(/\0/g, '\\0') + '"]';
		else if (element.className)
			selector = element.tagName.toLowerCase() + '.' + element.className.replace(/^\s+/,'').replace(/\s+$/,'').replace(/\s+/g, '.');
		else
			selector = element.tagName.toLowerCase();
		var nodes = element.parentNode.querySelectorAll(selector);
		if (nodes && nodes.length > 1)
			var elementIndex = Array.prototype.slice.call(nodes).indexOf(element);
		if (elementIndex > 0) {
			selector += ':nth-of-type(' + (elementIndex + 1).toString() + ')';
		}

		selector = selector.replace(/\s/g, ' ');
	}

	if (document.querySelectorAll(selector).length > 1 && element.parentNode)
		selector = cssSelectorOfElement(element.parentNode) + ' > ' + selector;

	return selector;
};

return cssSelectorOfElement(arguments[0]);