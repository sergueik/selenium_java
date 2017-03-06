/**
 * simulate mouse event on the element
 *
 * arguments[0] {Element} The event target.
 * arguments[1] {String} The name of the event (e.g. 'contextmenu').
 */

// http://stackoverflow.com/questions/6157929/how-to-simulate-a-mouse-click-using-javascript/6158050
// http://www.spookandpuff.com/examples/clickSimulation.html
// http://mouseevents.nerdyjs.com/search/MouseEvents
// https://raw.githubusercontent.com/sergeych/jsnippets/master/simulate_event.js
// http://stackoverflow.com/questions/35834671/similating-a-javascript-click-event-on-a-specific-page
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
		throw new SyntaxError('Unsupported event: ' + eventName);

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
// contextmenu is supposedly handled by simulateRightMouseButtonClick.js
var eventMatchers = {
	'HTMLEvents': /^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,
	'MouseEvents': /^(?:click|dblclick|mouse(?:down|up|over|move|out))$/
}
var defaultOptions = {
	pointerX: 0,
	pointerY: 0,
  // 0 for left button
  // 1  in Chrome has effect of 'Ctrl click' - link is opened in a new tab
	button: 0,
	ctrlKey: true,
  // modifiers have no effect in FF
	altKey: false,
	shiftKey: false,
	metaKey: false,
	bubbles: true,
	cancelable: true
}

// http://stackoverflow.com/questions/10596417/is-there-a-way-to-get-element-by-xpath-using-javascript-in-selenium-webdriver
var getElementByXpath = function getElementByXpath(xpath) {
  return document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

var getElementsByXpath = function getElementByXpath(xpath) {
  var result = [];
  var nodesSnapshot = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
  for (var i = 0; i < nodesSnapshot.snapshotLength; i++) {
    result.push(nodesSnapshot.snapshotItem(i));
  }
  return result;
}
var element = null;
var eventName = null;
if (typeof(arguments) == 'undefined') {
  // localor valid for a link in http://suvian.in/selenium/1.1link.html
  element = getElementByXpath('//div[1]/div/div/div/div/h3[2]/a');
  eventName = 'click';
} else {
  element = arguments[0] || document.defaultView;
  eventName = arguments[1] || 'click';
}

/*
// simplified:
var _event = new MouseEvent(eventName, {
  view: window,
  bubbles: true,
  cancelable: true,
  clientX: 20,
});
element.dispatchEvent(_event);
*/