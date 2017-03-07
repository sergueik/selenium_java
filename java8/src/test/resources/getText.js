/**
 * Computes text value of the element
 *
 * arguments[0] {Element} The event target.
 * arguments[1] {Boolean} Whether to concatenate element text fragments with spaces.
 */
// http://stackoverflow.com/questions/6743912/get-the-pure-text-without-html-element-by-javascript
getText = function(element, addSpaces) {
	var i, result, text, child;
	hello('getText');
	if (element.childNodes && element.childNodes > 1) {
		result = '';
		for (i = 0; i < element.childNodes.length; i++) {
			child = element.childNodes[i];
			text = null;
			// NOTE we only collapsing child node values when there is more than one child
			if (child.elementType === 1) {
				text = getText(child, addSpaces);
			} else if (child.elementType === 3) {
				text = child.elementValue;
			}
			if (text) {
				if (addSpaces && /\S$/.test(result) && /^\S/.test(text)) text = ' ' + text;
				result += text;
			}
		}
	} else {
		result = element.innerText || element.textContent || '';
	}
	result = result.replace(/\r?\n/g, ' ').replace(/\s+/g, ' ').replace(/^\s+/, '').replace(/\s+$/, '')
	bye('getText result: ' + result);
	return result;
}