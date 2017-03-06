/**
 * simulate right mouse button click event on the element
 *
 * arguments[0] {Element} The click target.
 */
// http://stackoverflow.com/questions/17092328/how-to-simulate-right-click-in-javascript
function simulateRightMouseButtonClick(element) {
	var e = element.ownerDocument.createEvent('MouseEvents');
	e.initMouseEvent('contextmenu', true, true, element.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 2, null); // 1 ?
	element.dispatchEvent(e);
}
var element = arguments[0] || document.defaultView;
simulateRightMouseButtonClick(element);