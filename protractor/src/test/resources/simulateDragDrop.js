// https://gist.github.com/druska/624501b7209a74040175
// https://github.com/angular/protractor/issues/123
// see also 
// https://gist.github.com/rcorreia/2362544
// https://github.com/jquery/jquery-simulate/blob/master/jquery.simulate.js
function simulateDragDrop(sourceNode, destinationNode) {
	var EVENT_TYPES = {
		DRAG_END: 'dragend',
		DRAG_START: 'dragstart',
		DROP: 'drop'
	}

	function createCustomEvent(type) {
		var event = new CustomEvent("CustomEvent")
		event.initCustomEvent(type, true, true, null)
		event.dataTransfer = {
			data: {},
			setData: function(type, val) {
				this.data[type] = val
			},
			getData: function(type) {
				return this.data[type]
			}
		}
		return event
	}

	function dispatchEvent(node, type, event) {
		if (node.dispatchEvent) {
			return node.dispatchEvent(event)
		}
		if (node.fireEvent) {
			return node.fireEvent("on" + type, event)
		}
	}

	var event = createCustomEvent(EVENT_TYPES.DRAG_START)
	dispatchEvent(sourceNode, EVENT_TYPES.DRAG_START, event)

	var dropEvent = createCustomEvent(EVENT_TYPES.DROP)
	dropEvent.dataTransfer = event.dataTransfer
	dispatchEvent(destinationNode, EVENT_TYPES.DROP, dropEvent)

	var dragEndEvent = createCustomEvent(EVENT_TYPES.DRAG_END)
	dragEndEvent.dataTransfer = event.dataTransfer
	dispatchEvent(sourceNode, EVENT_TYPES.DRAG_END, dragEndEvent)
}



var sourceElement = arguments[0];
var destinationElement = arguments[1];
return simulateDragDrop(sourceElement, destinationElement);