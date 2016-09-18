cssSelectorOfElementAlternative = function(element) {
	var query = '';

	if (element == document)
		query = 'body';
	else {
		var attr = ['ng-model', 'ng-href', 'name', 'aria-label'].reduce(function(a, b) {
			return a || (element.getAttribute(b) ? b : null);
		}, null);
		if (attr)
			query = element.tagName.toLowerCase() + '[' + attr + '="' + element.getAttribute(attr).replace(/\\/g, '\\\\').replace(/\'/g, '\\\'').replace(/\"/g, '\\"').replace(/\0/g, '\\0') + '"]';
		else
			query = element.tagName.toLowerCase();

		var nodes = element.parentNode.querySelectorAll(query);
		if (nodes && nodes.length > 1)
      var elementIndex = Array.prototype.slice.call(nodes).indexOf(element);
      if (elementIndex > 0 ) {
        query += ':nth-of-type(' + (elementIndex + 1).toString() + ')';
      }

		query = query.replace(/\s/g, ' ');
	}

	if (document.querySelectorAll(query).length > 1 && element.parentNode)
		query = cssSelectorOfElementAlternative(element.parentNode) + ' > ' + query;

	return query;
};

return cssSelectorOfElementAlternative(arguments[0]);