// origin: 
// http://stackoverflow.com/questions/7172955/selenium-ide-record-right-click
// http://stackoverflow.com/questions/433919/javascript-simulate-right-click-through-code
// http://stackoverflow.com/questions/433919/javascript-simulate-right-click-through-code
function contextMenuClick(element) {
    var evt = element.ownerDocument.createEvent('MouseEvents');

    var myEvt = document.createEvent('MouseEvents');
    var RIGHT_CLICK_BUTTON_CODE = 2; // the same for FF and IE
    myEvt.initMouseEvent(
        'click', // event type
        true, // can bubble?
        true, // cancelable?
        window, // the event's abstract view (should always be window)
        1, // mouse click count (or event "detail")
        100, // event's screen x coordinate
        200, // event's screen y coordinate
        100, // event's client x coordinate
        200, // event's client y coordinate
        false, // whether or not CTRL was pressed during event
        false, // whether or not ALT was pressed during event
        false, // whether or not SHIFT was pressed during event
        false, // whether or not the meta key was pressed during event
        RIGHT_CLICK_BUTTON_CODE, // indicates which button (if any) caused the mouse event (1 = primary button)
        null // relatedTarget (only applicable for mouseover/mouseout events)
    );
    // equivalent
    evt.initMouseEvent(
        'contextmenu',
        true,
        true,
        element.ownerDocument.defaultView,
        1,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        false,
        RIGHT_CLICK_BUTTON_CODE,
        null);

    if (document.createEventObject) {
        // dispatch for IE
        return element.fireEvent('onclick', evt)
    } else {
        // dispatch for firefox + others
        return !element.dispatchEvent(evt);
    }
}
