function xpathOfElement(element) {
	var elementTagName = element.tagName.toLowerCase();
	if (element.id != '') {
		return '//' + elementTagName + '[@id="' + element.id + '"]';
	} else if (element.name && document.getElementsByName(element.name).length === 1) {
		return '//' + elementTagName + '[@name="' + element.name + '"]';
	}
	if (element === document.body) {
		return '/html/' + elementTagName;
	}
	var sibling_count = 0;
	var siblings = element.parentNode.childNodes;
	siblings_length = siblings.length;
	for (cnt = 0; cnt < siblings_length; cnt++) {
		var sibling_element = siblings[cnt];
		if (sibling_element.nodeType !== 1) { // not ELEMENT_NODE
			continue;
		}
		if (sibling_element === element) {
			return sibling_count > 0 ? xpathOfElement(element.parentNode) + '/' + elementTagName + '[' + (sibling_count + 1) + ']' : xpathOfElement(element.parentNode) + '/' + elementTagName;
		}
		if (sibling_element.nodeType === 1 && sibling_element.tagName.toLowerCase() === elementTagName) {
			sibling_count++;
		}
	}
	return;
};
return xpathOfElement(arguments[0]);