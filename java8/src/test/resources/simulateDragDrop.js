/**
 * simulate  Drag and Drop of the page element with the mouse
 *
 * arguments[0] {Element} The event target.
 * arguments[1] {long} The destination x coordinate.
 * arguments[2] {long} The destination y coordinate.
 */
// https://ynot408.wordpress.com/2011/09/22/drag-and-drop-using-selenium-webdriver/
// http://stackoverflow.com/questions/35834671/similating-a-javascript-click-event-on-a-specific-page
// https://ynot408.wordpress.com/2011/09/22/drag-and-drop-using-selenium-webdriver/
// http://elementalselenium.com/tips/39-drag-and-drop
// https://gist.github.com/reinaldorossetti/074642e9f954b7119c557748836fcd42

simulate = function(element, eventName) {
	var options = extend(defaultOptions, arguments[2] || {});
	var oEvent, eventType = null;

	for (var name in eventMatchers) {
		if (eventMatchers[name].test(eventName)) {
			eventType = name;
			break;
		}
	}

	if (!eventType)
		throw new SyntaxError('Only HTMLEvents and MouseEvents interfaces are supported');

	if (document.createEvent) {
		oEvent = document.createEvent(eventType);
		if (eventType == 'HTMLEvents') {
			oEvent.initEvent(eventName, options.bubbles, options.cancelable);
		} else {
			oEvent.initMouseEvent(eventName, options.bubbles, options.cancelable, document.defaultView,
				options.button, options.pointerX, options.pointerY, options.pointerX, options.pointerY,
				options.ctrlKey, options.altKey, options.shiftKey, options.metaKey, options.button, element);
		}
		element.dispatchEvent(oEvent);
	} else {
		options.clientX = options.pointerX;
		options.clientY = options.pointerY;
		var evt = document.createEventObject();
		oEvent = extend(evt, options);
		element.fireEvent('on' + eventName, oEvent);
	}
	return element;
}

function extend(destination, source) {
	for (var property in source)
		destination[property] = source[property];
	return destination;
}
// contextmenu
var eventMatchers = {
	'HTMLEvents': /^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,
	'MouseEvents': /^(?:click|contextmenu|dblclick|mouse(?:down|up|over|move|out))$/
}
var defaultOptions = {
	pointerX: 0,
	pointerY: 0,
	button: 0,
	ctrlKey: false,
	altKey: false,
	shiftKey: false,
	metaKey: false,
	bubbles: true,
	cancelable: true
}

var elementFrom = arguments[0] || document.defaultView;
var destX = arguments[1] || 0;
var destY = arguments[2] || 0;

simulate(elementFrom, 'mousedown', 0, 0);
simulate(elementFrom, 'mousemove', destX, destY);
simulate(elementFrom, 'mouseup', destX, destY);
